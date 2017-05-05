package ch.cern.c2mon.benchmarks;

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkProperties;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import org.openjdk.jmh.annotations.*;

/**
 * @author Szymon Halastra
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = BenchmarkProperties.MEASUREMENT_ITERATIONS)
@Warmup(iterations = BenchmarkProperties.WARM_UP_ITERATIONS)
public class RedissonBenchmark implements BenchmarkedMethods {

  CachingProvider provider;
  Cache<Long, Entity> cache;


  @Setup
  public void setup() throws Exception {
    provider = Caching.getCachingProvider("org.redisson.jcache.JCachingProvider");
    CacheManager cacheManager = provider.getCacheManager();

    cache = cacheManager.createCache("entities", BenchmarkProperties.createMutableConfiguration());

    BenchmarkProperties.populateCache(cache);
  }

  @TearDown
  public void shutdown() {
    provider.close();
  }

  @Benchmark
  @Override
  public void putEntityBenchmark() {
    Entity entity = new Entity();
    cache.put(entity.getId(), entity);
  }


  @Benchmark
  @Override
  public void getEntityBenchmark() {
    cache.get(BenchmarkProperties.getRandomKey(cache));
  }
}