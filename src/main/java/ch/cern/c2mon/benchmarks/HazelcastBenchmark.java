package ch.cern.c2mon.benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import com.hazelcast.cache.ICache;
import com.hazelcast.core.Hazelcast;
import org.openjdk.jmh.annotations.*;

/**
 * @author Szymon Halastra
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10)
@Warmup(iterations = 10)
public class HazelcastBenchmark implements BenchmarkedMethods {

  Cache<Long, Entity> cache;

  @Setup
  public void setup() {
    CachingProvider provider = Caching.getCachingProvider();

    CacheManager cacheManager = provider.getCacheManager();

    cache = cacheManager.getCache("entities");
  }

  @TearDown
  public void shutdown() {
    Hazelcast.shutdownAll();
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
}
