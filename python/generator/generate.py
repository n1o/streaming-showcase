import asyncio
import random
from datetime import datetime
from confluent_kafka import avro
from confluent_kafka.avro import AvroProducer
import numpy as np
from scipy.stats import bernoulli, t, norm, multinomial

event_1_schema = avro.load('/Users/mbarak/projects/github/showcase/core/src/main/resources/Event.avsc')
key_schema = avro.load('/Users/mbarak/projects/github/showcase/core/src/main/resources/UserKey.avsc')
event_2_schema = avro.load('/Users/mbarak/projects/github/showcase/core/src/main/resources/Event2.avsc')


users = [i for i in range(100)]

event_1_producer = AvroProducer({'bootstrap.servers': 'localhost:9092', 'schema.registry.url': 'http://localhost:8081'}, default_value_schema=event_1_schema, default_key_schema=key_schema)
event_2_producer = AvroProducer({'bootstrap.servers': 'localhost:9092', 'schema.registry.url': 'http://localhost:8081'}, default_value_schema=event_2_schema, default_key_schema=key_schema)


async def generate_event_1(users, producer):
    n11 = norm(10, 2)
    n12 = norm(20,5)
    rv = multinomial(1, [0.3, 0.2, 0.5])

    def gen_event(user):
        return (
            "user_%s" % user,
                {
                    "userId": "user_%s" % user,
                    "userValue1": round(n11.rvs() if user % 4 == 0 else n12.rvs(), 2),
                    "userValue2": int(np.argmax(rv.rvs())),
                    "timestamp": int((datetime.utcnow() - datetime(1970, 1, 1)).total_seconds() * 1000)
                }
            )
    while True:
        size = random.randint(10,20)
        print("Event 1", size)
        for user in random.sample(users, size):
            user_id, user_event = gen_event(user)
            producer.produce(topic = "dev-v1-avro-event1",  key = {"user": user_id}, value=user_event)

        await asyncio.sleep(5)

async def generate_event_2(users, producer):
    m = {0: "good", 1: "neutral", 2: "bad"}

    n11 = norm(10, 2)
    n12 = norm(20,5)
    rv = multinomial(1, [0.3, 0.2, 0.5])

    def gen_event(user):
        return (
            "user_%s" % user,
            {
                "userId": "user_%s" % user,
                "userValue3": round(n11.rvs() if user % 4 == 0 else n12.rvs(), 2),
                "userValue4": m[int(np.argmax(rv.rvs()))],
                "timestamp": int((datetime.utcnow() - datetime(1970, 1, 1)).total_seconds() * 1000)
            }
        )

    while True:
        size = random.randint(10,20)
        print("Event 2", size)

        for user in random.sample(users, size):
            user_id, user_event = gen_event(user)
            producer.produce(topic = "dev-v1-avro-event2",  key = {"user": user_id}, value=user_event)

        await asyncio.sleep(5)


loop = asyncio.get_event_loop()
asyncio.ensure_future(generate_event_1(users, event_1_producer))
asyncio.ensure_future(generate_event_2(users, event_2_producer))

try:
    loop.run_forever()
finally:
    loop.close()