package ch.cern.c2mon.benchmarks;

import java.util.Map;
import java.util.Set;
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

@BenchmarkMode({Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class EhCacheBenchmark implements BenchmarkedMethods {

  Cache<Long, Entity> cache;
  CachingProvider provider;

  Map<Long, Entity> entitiesMap;
  long key;
  Set<Long> keys;

  @Setup
  public void setup() throws Exception {
    entitiesMap = BenchmarkProperties.createEntities();

    provider = Caching.getCachingProvider(BenchmarkProperties.EHCACHE_PROVIDER);
    CacheManager cacheManager = provider.getCacheManager();

    cache = cacheManager.createCache("entities", BenchmarkProperties.createMutableConfiguration());

    cache.putAll(entitiesMap);

    key = BenchmarkProperties.getRandomKey(cache);
    keys = BenchmarkProperties.getKeys(cache);
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
    Entity entity = cache.get(key);

    return entity;
  }

  @Benchmark
  @Override
  public Entity getAndPutEntity() {
    Entity entity = cache.getAndPut(key, new Entity());

    return entity;
  }

  @Benchmark
  @Override
  public Map<Long, Entity> getAllEntities() {
    Map<Long, Entity> entities = cache.getAll(keys);

    return entities;
  }

  @Benchmark
  @Override
  public void putAllEntities() {
    cache.putAll(entitiesMap);
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
    boolean isRemoved = cache.remove(key);

    return isRemoved;
  }

  @Benchmark
  @Override
  public Entity getAndRemoveEntity() {
    Entity entity = cache.getAndRemove(key);
    return entity;
  }

  @Benchmark
  @Override
  public boolean replaceEntity() {
    boolean isReplaced = cache.replace(key, new Entity());

    return isReplaced;
  }

  @Benchmark
  @Override
  public Entity getAndReplaceEntity() {
    Entity entity = cache.getAndReplace(key, new Entity());

    return entity;
  }

  @Benchmark
  @Override
  public void removeAllEntities() {
    cache.removeAll(keys);
  }
}