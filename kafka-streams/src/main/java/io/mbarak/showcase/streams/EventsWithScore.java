package io.mbarak.showcase.streams;

import io.mbarak.showcase.Event;
import io.mbarak.showcase.Event2;
import io.mbarak.showcase.UserScore;

public class EventsWithScore {
    public EventsWithScore(Event event, Event2 event2, UserScore userScore) {
        this.event = event;
        this.event2 = event2;
        this.userScore = userScore;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event2 getEvent2() {
        return event2;
    }

    public void setEvent2(Event2 event2) {
        this.event2 = event2;
    }

    public UserScore getUserScore() {
        return userScore;
    }

    public void setUserScore(UserScore userScore) {
        this.userScore = userScore;
    }

    private Event event;
    private Event2 event2;
    private UserScore userScore;

}
