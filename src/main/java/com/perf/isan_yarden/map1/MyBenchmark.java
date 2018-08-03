package com.perf.isan_yarden.map1;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;

public class MyBenchmark {


    @Benchmark
    public void wellHelloThereFast() {

    }
    @Benchmark
    public void wellHelloThereSlow() {
        Random r = new Random();
        r.nextInt(50);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();
        new Runner(opt).run();
    }

}