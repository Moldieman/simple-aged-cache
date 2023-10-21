package io.collective;

import java.time.Clock;

public class SimpleAgedCache {

    private Clock clock;
    private ExpirableEntry head;
    private ExpirableEntry tail;

    public SimpleAgedCache(Clock clock) {
        this.head = null;
        this.tail = this.head;
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this.head = null;
        this.tail = this.head;
        this.clock = Clock.systemUTC();
    }

    public void put(Object key, Object value, int retentionInMillis) {
        ExpirableEntry input = new ExpirableEntry(key, value, this.clock, retentionInMillis);
        if (this.head == null) {
            this.head = input;
            this.tail = input;
            return;
        }
        this.tail.setNext(input);
        this.tail = this.tail.getNext();
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public int size() {
        int count = 0;
        if (this.head == null) {
            return 0;
        }
        var current = this.head;
        if (!this.head.isExpired()) {
            count++;
        }
        while (current.getNext() != null) {
            current = current.getNext();
            if (current.isExpired()) {
                continue;
            }
            count++;
        }
        return count;
    }

    public Object get(Object key) {
        if (this.head == null) {
            return null;
        }
        var current = this.head;
        while (current.getKey() != key) {
            current = current.getNext();
            if (current == null) {
                return null;
            }
            if (current.isExpired()) {
                return null;
            }
        }
        return current.getValue();
    }
}
