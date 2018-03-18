package io.mbarak.showcase.streams;

import io.mbarak.showcase.Event;
import io.mbarak.showcase.Event2;
import io.mbarak.showcase.UserScore;

public class Events {
    private Event event;
    private Event2 event2;

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

    public Events(Event event, Event2 event2) {
        this.event = event;
        this.event2 = event2;
    }
}

