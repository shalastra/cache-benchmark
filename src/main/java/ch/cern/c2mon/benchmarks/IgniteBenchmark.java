package ch.cern.c2mon.benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.openjdk.jmh.annotations.*;

/**
 * @author Szymon Halastra
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10)
@Warmup(iterations = 10)
public class IgniteBenchmark implements BenchmarkedMethods {

  protected Ignite node;

  protected IgniteCache cache;

  @Setup
  public void setup() {
    System.out.println("IGNITE BENCHMARK INFO: ");
    System.out.println("\tClient name");
    node = Ignition.start(createIgniteConfiguration("node0"));

    cache = node.cache(null);
  }

  @TearDown
  public void shutdown() {
    Ignition.stopAll(true);
  }

  @Benchmark
  @Override
  public void putEntryBenchmark() {
    Entity entity = new Entity();

    cache.put(entity.getId(), entity);
  }

  @Benchmark
  @Override
  public void getEntryBenchmark() {
    cache.get(ThreadLocalRandom.current().nextLong(100000, 999999));
  }


  private IgniteConfiguration createIgniteConfiguration(String nodeName) {
    IgniteConfiguration igniteConfiguration = new IgniteConfiguration();

    igniteConfiguration.setCacheConfiguration(createCacheConfiguration());

    return igniteConfiguration;
  }

  private CacheConfiguration createCacheConfiguration() {
    CacheConfiguration cacheConfiguration = new CacheConfiguration();

    cacheConfiguration.setName(null);
    cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);

    return cacheConfiguration;
  }
}
