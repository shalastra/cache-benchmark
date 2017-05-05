package ch.cern.c2mon.benchmarks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkProperties;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;

/**
 * @author Szymon Halastra
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10)
@Warmup(iterations = 10)
@Slf4j
public class HazelcastBenchmark implements BenchmarkedMethods {

  CachingProvider provider;
  Cache<Long, Entity> cache;

  @Setup
  public void setup() throws Exception {

    provider = Caching.getCachingProvider("com.hazelcast.cache.HazelcastCachingProvider");
    CacheManager cacheManager = provider.getCacheManager();

    cache = cacheManager.createCache("entities", BenchmarkProperties.createMutableConfiguration());

    for(int i = 0; i < BenchmarkProperties.CACHE_SIZE; i++) {
      Entity entity = new Entity();
      cache.put(entity.getId(), entity);
    }
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
    List<Long> keys = new ArrayList<>();
    Iterator<Cache.Entry<Long, Entity>> entries = cache.iterator();
    while(entries.hasNext()) {
      Cache.Entry<Long, Entity> entry = entries.next();
      keys.add(entry.getKey());
    }

    Collections.shuffle(keys);

    Entity entity = cache.get(keys.get(0));
  }
}
