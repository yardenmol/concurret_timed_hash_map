package com.perf.isan_yarden.map1;

import java.util.concurrent.TimeUnit;

public class TimedValue<V> {

    private final V value;
    private final long time;

    public TimedValue(V _val, long duration, TimeUnit unit){

        this.value = _val;
        long mills_timestamp = unit.toMillis(duration);
        this.time = System.currentTimeMillis() + mills_timestamp;

    }

    public long getTime() {
        return time;
    }

    public V getValue() {
        return value;
    }
}
