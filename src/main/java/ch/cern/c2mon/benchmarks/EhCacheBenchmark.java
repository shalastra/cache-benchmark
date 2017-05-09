package ch.cern.c2mon.benchmarks;

import java.util.Map;
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

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Measurement(iterations = BenchmarkProperties.MEASUREMENT_ITERATIONS)
@Warmup(iterations = BenchmarkProperties.WARM_UP_ITERATIONS)
public class EhCacheBenchmark implements BenchmarkedMethods {

  Cache<Long, Entity> cache;
  CachingProvider provider;

  @Setup
  public void setup() throws Exception {
    provider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
    CacheManager cacheManager = provider.getCacheManager();

    cache = cacheManager.createCache("entities", BenchmarkProperties.createMutableConfiguration());

    cache.putAll(BenchmarkProperties.createEntities());
  }

  @TearDown
  public void shutdown() {
    provider.close();
  }

  @Benchmark
  @Override
  public void putEntity() {
    Entity entity = new Entity();
    cache.put(entity.getId(), entity);
  }


  @Benchmark
  @Override
  public Entity getEntity() {
    Entity entity = cache.get(BenchmarkProperties.getRandomKey(cache));

    return entity;
  }

  @Benchmark
  @Override
  public Entity getAndPutEntity() {
    Entity entity = cache.getAndPut(BenchmarkProperties.getRandomKey(cache), new Entity());

    return entity;
  }

  @Benchmark
  @Override
  public Map<Long, Entity> getAllEntities() {
    Map<Long, Entity> entities = cache.getAll(BenchmarkProperties.getKeys(cache));

    return entities;
  }

  @Benchmark
  @Override
  public void putAllEntities() {
    cache.putAll(BenchmarkProperties.createEntities());
  }

  @Benchmark
  @Override
  public boolean putIfAbsentEntity() {
    Entity entity = new Entity();
    boolean isAbsent = cache.putIfAbsent(entity.getId(), entity);

    return isAbsent;
  }

  @Benchmark
  @Override
  public boolean removeEntity() {
    boolean isRemoved = cache.remove(BenchmarkProperties.getRandomKey(cache));

    return isRemoved;
  }

  @Benchmark
  @Override
  public Entity getAndRemoveEntity() {
    Entity entity = cache.getAndRemove(BenchmarkProperties.getRandomKey(cache));
    return entity;
  }

  @Benchmark
  @Override
  public boolean replaceEntity() {
    boolean isReplaced = cache.replace(BenchmarkProperties.getRandomKey(cache), new Entity());

    return isReplaced;
  }

  @Benchmark
  @Override
  public Entity getAndReplaceEntity() {
    Entity entity = cache.getAndReplace(BenchmarkProperties.getRandomKey(cache), new Entity());

    return entity;
  }

  @Benchmark
  @Override
  public void removeAllEntities() {
    cache.removeAll(BenchmarkProperties.getKeys(cache));
  }
}