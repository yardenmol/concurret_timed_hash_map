package com.perf.isan_yarden;

import com.perf.isan_yarden.map1.MapInterface;
import com.perf.isan_yarden.map1.NaiveMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TimedMapBenchmark {

    private final NaiveMap<Integer, Integer> naive = new NaiveMap<Integer, Integer>(5, TimeUnit.MILLISECONDS);
//    private final MapInterface.TimedSizableMap<String, Integer> actor = new NaiveMap<String, Integer>(2);

    private static final long WARMUP_ITER = (long) 1E2;
    private static final long BENCHMARK_ITER = (long) 1E3;

    public static void main(String... args) {
        new TimedMapBenchmark().benchmark();
    }

    public void benchmark() {
        warmup();
        System.gc();
        measure();
    }

    private void measure() {
        long naiveStart = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITER; i++) {
            naive.put(i,i,10,TimeUnit.MILLISECONDS);
            System.out.println(naive.size());
        }
        long naiveEnd = System.nanoTime();
        naive.terminate();
//        System.gc();
//        long actorStart = System.nanoTime();
//        for (int i = 0; i < BENCHMARK_ITER; i++) {
//            actor.put(i,i,3,TimeUnit.SECONDS);
//        }
//        long actorEnd = System.nanoTime();

        System.out.println("naive: " + (naiveEnd - naiveStart)/1E6 + "ms");
//        System.out.println("actor: " + (actorStart - actorEnd)/1E6 + "ms");
    }

    private void warmup() {
        long start = System.nanoTime();
        for (int i = 0; i < WARMUP_ITER; i++) {
            naive.put(i,i,10,TimeUnit.MILLISECONDS);
            naive.size();
//            actor.put(i,i,3,TimeUnit.SECONDS);
        }
        long end = System.nanoTime();
        System.out.println("Warmup time: " + (end-start)/1E6 + "ms");
    }

}
