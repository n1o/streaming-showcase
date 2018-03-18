from confluent_kafka import Consumer, KafkaError
from confluent_kafka import avro
from confluent_kafka.avro import AvroProducer
import json


user_score_schema = avro.load('/Users/mbarak/projects/github/showcase/core/src/main/resources/UserScore.avsc')
key_schema = avro.load('/Users/mbarak/projects/github/showcase/core/src/main/resources/UserKey.avsc')

c = Consumer({'bootstrap.servers': 'localhost:9092', 'group.id': 'user-profile-scoring',
              'default.topic.config': {'auto.offset.reset': 'smallest'}})

scores_producer = AvroProducer({'bootstrap.servers': 'localhost:9092', 'schema.registry.url': 'http://localhost:8081'}, default_value_schema=user_score_schema, default_key_schema=key_schema)

c.subscribe(['dev-json-v1-user-features'])

running = True
while running:
    msg = c.poll()
    if not msg.error():
        features =  json.loads(msg.value().decode('utf-8'))
        print('Scoring User: %s' % features['userId'])
        dummy_result = {"userId": features['userId'], "score": 1, "timestamp": features['timestamp'] }
        scores_producer.produce(topic = "dev-v1-avro-user-scores",  key = {"user": features['userId']}, value=dummy_result)

    elif msg.error().code() != KafkaError._PARTITION_EOF:
        print(msg.error())
        running = False
c.close()

# c = AvroConsumer({'bootstrap.servers': 'localhost:9092', 'schema.registry.url': 'http://localhost:8081', 'group.id': 'user-profile-scoring'})
# c.subscribe(['dev-avro-v1-user-features'])
# running = True
# while running:
#     try:
#         msg = c.poll(10)
#         if msg:
#             if not msg.error():
#                 print(msg.value())
#             elif msg.error().code() != KafkaError._PARTITION_EOF:
#                 print(msg.error())
#                 running = False
#     except SerializerError as e:
#         print("Message deserialization failed for %s: %s" % (msg, e))
#         running = False
#
# c.close()