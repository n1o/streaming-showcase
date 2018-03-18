package io.mbarak.showcase.streams;

import io.mbarak.showcase.Event;
import io.mbarak.showcase.Event2;
import io.mbarak.showcase.UserProfile;
import org.apache.kafka.streams.kstream.Aggregator;

public class UserProfileAggregator implements Aggregator<String, EventsWithScore, UserProfile> {

    @Override
    public UserProfile apply(String key, EventsWithScore value, UserProfile aggregate) {

        if(aggregate.getUserId() == null || "".equals(aggregate.getUserId())) {
            aggregate.setUserId(key);
        }

        Event e = value.getEvent();
        Event2 e2 = value.getEvent2();

        if(e != null) {
            aggregate.setUserValue1(e.getUserValue1());
            aggregate.setUserValue2(e.getUserValue2());
            aggregate.setTimestamp(e.getTimestamp());
        }

        if(e2 != null) {
            aggregate.setUserValue3(e2.getUserValue3());
            aggregate.setUserValue4(e2.getUserValue4());
            aggregate.setTimestamp(e2.getTimestamp());
        }

        if(e != null && e2 != null) {
            aggregate.setTimestamp( e.getTimestamp() > e2.getTimestamp() ? e.getTimestamp() : e2.getTimestamp() );
        }

        return aggregate;
    }
}
