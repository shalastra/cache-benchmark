package ch.cern.c2mon.benchmarks;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
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
public class IgniteBenchmark implements BenchmarkedMethods {

  Cache<Long, Entity> cache;
  CachingProvider provider;

  @Setup
  public void setup() throws Exception {
    provider = Caching.getCachingProvider("org.apache.ignite.cache.CachingProvider");
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

  @Override
  public Map<Long, Entity> getAllEntities() {
    Map<Long, Entity> entities = cache.getAll(BenchmarkProperties.getKeys(cache));

    return entities;
  }

  @Override
  public void putAllEntities() {
    cache.putAll(BenchmarkProperties.createEntities());
  }

  @Override
  public void putIfAbsentEntity() {

  }

  @Override
  public void removeEntity() {

  }

  @Override
  public Entity getAndRemoveEntity() {

    return null;
  }

  @Override
  public void replaceEntity() {

  }

  @Override
  public void getAndReplaceEntity() {

  }

  @Override
  public void removeAllEntities() {

  }
}
