from confluent_kafka import Consumer, KafkaError
from confluent_kafka import avro
from confluent_kafka.avro import AvroProducer
import json
import torch
from torch.autograd import Variable
import numpy as np
from log_reg import LogisticRegression

user_score_schema = avro.load('/Users/mbarak/projects/github/showcase/core/src/main/resources/UserScore.avsc')
key_schema = avro.load('/Users/mbarak/projects/github/showcase/core/src/main/resources/UserKey.avsc')

c = Consumer({'bootstrap.servers': 'localhost:9092', 'group.id': 'user-profile-scoring',
              'default.topic.config': {'auto.offset.reset': 'smallest'}})

scores_producer = AvroProducer({'bootstrap.servers': 'localhost:9092', 'schema.registry.url': 'http://localhost:8081'}, default_value_schema=user_score_schema, default_key_schema=key_schema)

c.subscribe(['dev-json-v1-user-features'])

model = LogisticRegression(num_features=5)

model.state_dict = torch.load("model.pth")

while True:
    msg = c.poll()
    if not msg.error():
        features =  json.loads(msg.value().decode('utf-8'))

        print('Scoring User: %s' % features['userId'])

        X = Variable(
            torch.Tensor(
                np.array([features['feature1'], features['feature3'], features['good'], features['neutral'] ,features['bad']])
            ), requires_grad=False
        )

        res = model(X)

        score = 1 if res.data[0] > 0.5 else 0

        dummy_result = {"userId": features['userId'], "score": 1, "timestamp": features['timestamp'] }

        scores_producer.produce(topic = "dev-v1-avro-user-scores",  key = {"user": features['userId']}, value=dummy_result)

    elif msg.error().code() != KafkaError._PARTITION_EOFw:
        print(msg.error())
        running = False

c.close()

