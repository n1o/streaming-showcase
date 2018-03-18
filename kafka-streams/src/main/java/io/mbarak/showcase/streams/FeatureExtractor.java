package io.mbarak.showcase.streams;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.mbarak.showcase.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.concurrent.TimeUnit;

public class FeatureExtractor {

    public static Topology getFeatureExtractionStream() {

        StreamsBuilder builder = new StreamsBuilder();

        SpecificAvroSerde<UserKey> userKeySerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<Event> eventsSerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<Event2> events2Serde = new SpecificAvroSerde<>();
        SpecificAvroSerde<UserScore> userScoreSerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<UserProfile> userProfileSerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<ExtracedFeatures> extracedFeaturesSerde = new SpecificAvroSerde<>();

        KStream<UserKey, Event> eventStream =  builder
                .stream("dev-1-avro-event1", Consumed.with(userKeySerde, eventsSerde));

        KStream<UserKey, Event2> events2Stream = builder
                .stream("dev-1-avro-event2", Consumed.with(userKeySerde, events2Serde));

        KTable<UserKey, UserScore> userScores = builder
                .table("dev-avro-user-scores", Consumed.with(userKeySerde, userScoreSerde));

        Materialized<String, UserProfile, KeyValueStore<Bytes, byte[]>> materialized =
                Materialized.<String, UserProfile, KeyValueStore<Bytes, byte[]>>as("user-profile-store")
                    .withKeySerde(Serdes.String())
                    .withValueSerde(userProfileSerde);

        KTable<String, UserProfile> userProfile = eventStream
                .outerJoin(events2Stream, (k,v) -> new Events(k,v), JoinWindows.of(TimeUnit.MINUTES.toMillis(5)))
                .leftJoin(userScores, (k, v) -> new EventsWithScore(k.getEvent(), k.getEvent2(), v) )
                .map((k,v) -> KeyValue.pair(k.getUser(), v))
                .groupByKey()
                .aggregate(
                        () -> new UserProfile(),
                        new UserProfileAggregator(),
                        materialized

                );


        userProfile
                .toStream()
                .flatMapValues(new UserFeaturesExtractor())
                .to("user-features", Produced.with(Serdes.String(), extracedFeaturesSerde));

        return builder.build();
    }
}
