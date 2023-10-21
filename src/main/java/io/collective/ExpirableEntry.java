package io.collective;

import java.time.Clock;
import java.time.Instant;

public class ExpirableEntry {
    private Object key;
    private Object value;
    private ExpirableEntry next;

    private Clock clock;
    private Instant expiry;

    public ExpirableEntry(Object key, Object value, Clock clock, int retentionInMillis) {
        this.key = key;
        this.value = value;
        this.clock = clock;
        this.expiry = clock.instant().plusMillis(retentionInMillis);
        this.next = null;
    }

    public ExpirableEntry getNext() {
        return next;
    }

    public void setNext(ExpirableEntry next) {
        this.next = next;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public boolean isExpired() {
        return clock.instant().isAfter(expiry);
    }
}
