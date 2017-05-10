package ch.cern.c2mon.benchmarks.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import ch.cern.c2mon.BenchmarkProperties;
import ch.cern.c2mon.benchmarks.AbstractBenchmark;
import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;

/**
 * @author Szymon Halastra
 */
public class IgniteBenchmark  extends AbstractBenchmark {

  Cache<Long, Entity> cache;
  CachingProvider provider;

  public Map<Long, Entity> entityMap;
  public long randomKey;
  public Set<Long> keys;

  @Setup
  public void setup() throws Exception {
    entityMap = new HashMap<>(BenchmarkUtils.createEntities());
    keys = entityMap.keySet();
    randomKey = BenchmarkUtils.randomKey(entityMap, keys);

    provider = Caching.getCachingProvider(BenchmarkProperties.IGNITE_PROVIDER);
    CacheManager cacheManager = provider.getCacheManager();

    cache = cacheManager.createCache("entities", BenchmarkUtils.createMutableConfiguration());

    cache.putAll(entityMap);
  }

  @TearDown
  public void shutdown() {
    provider.close();
  }

  @Benchmark
  public void putEntity() {
    Entity entity = new Entity();
    cache.put(entity.getId(), entity);
  }


  @Benchmark
  public Entity getEntity() {
    Entity entity = cache.get(randomKey);

    return entity;
  }

  @Benchmark
  public Entity getAndPutEntity() {
    Entity entity = cache.getAndPut(randomKey, new Entity());

    return entity;
  }

  @Benchmark
  public Map<Long, Entity> getAllEntities() {
    Map<Long, Entity> entities = cache.getAll(keys);

    return entities;
  }

  @Benchmark
  public void putAllEntities() {
    cache.putAll(entityMap);
  }

  @Benchmark
  public boolean putIfAbsentEntity() {
    Entity entity = new Entity();
    boolean isAbsent = cache.putIfAbsent(entity.getId(), entity);

    return isAbsent;
  }

  @Benchmark
  public boolean removeEntity() {
    boolean isRemoved = cache.remove(randomKey);

    return isRemoved;
  }

  @Benchmark
  public Entity getAndRemoveEntity() {
    Entity entity = cache.getAndRemove(randomKey);
    return entity;
  }

  @Benchmark
  public boolean replaceEntity() {
    boolean isReplaced = cache.replace(randomKey, new Entity());

    return isReplaced;
  }

  @Benchmark
  public Entity getAndReplaceEntity() {
    Entity entity = cache.getAndReplace(randomKey, new Entity());

    return entity;
  }

  @Benchmark
  public void removeAllEntities() {
    cache.removeAll(keys);
  }
}
