package ch.cern.c2mon;

import ch.cern.c2mon.benchmarks.EhCacheBenchmark;
import ch.cern.c2mon.benchmarks.HazelcastBenchmark;
import ch.cern.c2mon.benchmarks.IgniteBenchmark;
import ch.cern.c2mon.utils.BenchmarkProperties;
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
            .forks(BenchmarkProperties.FORKS_NUMBER)
            .build();

    new Runner(options).run();
  }
}

/** TODO:
 * create configuration files for all caches by providing implementations of CachingProvider/CacheManager
 * Write missing tests for clusters
 *
 */
