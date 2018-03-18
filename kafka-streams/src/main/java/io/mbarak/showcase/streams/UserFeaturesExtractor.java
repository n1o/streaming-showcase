package io.mbarak.showcase.streams;

import io.mbarak.showcase.ExtracedFeatures;
import io.mbarak.showcase.UserProfile;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.ArrayList;
import java.util.Arrays;

public class UserFeaturesExtractor implements ValueMapper<UserProfile, Iterable<ExtracedFeatures>> {

    @Override
    public Iterable<ExtracedFeatures> apply(UserProfile value) {

        if(value.getUserId() != null && value.getUserValue1() != null && value.getUserValue3() != null && value.getUserValue4() != null) {

            int good = 0;
            int neutral = 0;
            int bad = 0;

            switch (value.getUserValue4()) {
                case "good": good =1; break;
                case "netural" : neutral = 1; break;
                case "bad": bad = 1; break;
            }

            return Arrays.asList(
                    new ExtracedFeatures(value.getUserId(), value.getUserValue1(), value.getUserValue3(), value.getTimestamp(), good, neutral, bad)
            );
        }
        return new ArrayList<ExtracedFeatures>();
    }
}
