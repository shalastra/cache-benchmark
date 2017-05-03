package ch.cern.c2mon.benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;

import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import org.openjdk.jmh.annotations.*;

/**
 * @author Szymon Halastra
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10)
@Warmup(iterations = 10)
public class RedissonBenchmark implements BenchmarkedMethods {

  Cache<Long, Entity> cache;


  @Setup
  public void init() {
    MutableConfiguration<Long, Entity> configuration = new MutableConfiguration<>();

    configuration.setStatisticsEnabled(true);

    CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
    cache = cacheManager.createCache("entities", configuration);
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

  @TearDown
  public void shutdown() {
    Caching.getCachingProvider().close();
  }
}
