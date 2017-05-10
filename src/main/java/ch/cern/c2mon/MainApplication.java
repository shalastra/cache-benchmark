package ch.cern.c2mon;

import ch.cern.c2mon.benchmarks.impl.EhCacheBenchmark;
import ch.cern.c2mon.benchmarks.impl.HazelcastBenchmark;
import ch.cern.c2mon.benchmarks.impl.IgniteBenchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author Szymon Halastra
 */
public class MainApplication {

  public static void main(String[] args) throws Exception {
    Options options = new OptionsBuilder()
            .include(IgniteBenchmark.class.getSimpleName())
            .include(HazelcastBenchmark.class.getSimpleName())
            .include(EhCacheBenchmark.class.getSimpleName())
//            .include(RedissonBenchmark.class.getSimpleName())
            .forks(BenchmarkProperties.FORKS_NUMBER)
            .measurementIterations(BenchmarkProperties.MEASUREMENT_ITERATIONS)
            .warmupIterations(BenchmarkProperties.WARM_UP_ITERATIONS)
            .build();

    new Runner(options).run();
  }
}