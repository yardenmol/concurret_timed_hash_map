package com.perf.isan_yarden;

import com.perf.isan_yarden.map1.NaiveMap;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class NaiveMapTest {

    @Test
    public void shouldInsertValue(){
        NaiveMap<String, Integer> map = new NaiveMap<>(1);
        assertEquals(0,map.size());
        map.put("yarden",5,4,TimeUnit.SECONDS);
        assertEquals(1,map.size());
        map.terminate();
    }

    @Test
    public void shouldGcRemove() throws InterruptedException {
        NaiveMap<String, Integer> map = new NaiveMap<>(1);
        map.put("yarden",5,1,TimeUnit.SECONDS);
        Thread.sleep(2000);
        assertFalse(map.get("yarden").isPresent());
        assertEquals(0,map.size());
        map.terminate();
    }

    @Test
    public void shouldInsertParallel() throws InterruptedException {
        for (int j=0;j<1;j++){
            NaiveMap<Integer, Integer> map = new NaiveMap<>(1);

            Thread t1 = new Thread(()->{
                for(int i=0; i<100;i++)
                    map.put(i,i,2,TimeUnit.SECONDS);
            });

            Thread t2 = new Thread(()->{
                for(int i=100; i<200;i++)
                    map.put(i,i,2,TimeUnit.SECONDS);
            });
            t1.start();
            t2.start();

            t1.join();
            t2.join();

            assertEquals(200,map.size());
            Thread.sleep(2000);
            assertEquals(0,map.size());
            map.terminate();
        }

    }

    @Test
    public void shouldReturnLastValueOfKey() throws InterruptedException {
        NaiveMap<String, Integer> map = new NaiveMap<>(1);
        map.put("isan",5,10,TimeUnit.SECONDS);
        map.put("isan",6,10,TimeUnit.SECONDS);
        map.get("isan").ifPresent(val->{
            assertEquals(6,val);
        });
        map.terminate();
    }
    @Test
    public void shouldDropHalfInputs() throws InterruptedException {
        NaiveMap<Integer, Integer> map = new NaiveMap<>(1);
        int size = 10_000;
        Thread t1 = new Thread(()->{
            for(int i=0;i<size;++i){
                map.put(i,i,10,TimeUnit.MILLISECONDS);
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<size;++i){
                map.put(i+size,i,50,TimeUnit.SECONDS);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Thread.sleep(2000);
        assertEquals(size,map.size());
        map.terminate();
    }
}
