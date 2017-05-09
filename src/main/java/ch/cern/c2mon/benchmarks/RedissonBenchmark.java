package ch.cern.c2mon.benchmarks;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkProperties;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import org.openjdk.jmh.annotations.*;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import redis.embedded.RedisServer;

/**
 * @author Szymon Halastra
 */

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Measurement(iterations = BenchmarkProperties.MEASUREMENT_ITERATIONS)
@Warmup(iterations = BenchmarkProperties.WARM_UP_ITERATIONS)
public class RedissonBenchmark implements BenchmarkedMethods {

  CachingProvider provider;
  Cache<Long, Entity> cache;

  RedissonClient client;
  RedisServer server;

  @Setup
  public void setup() throws Exception {
    server = new RedisServer(7002);
    server.start();
    // Provided implementation fully passes TCK tests.
    Config config = new Config();
    config.useSingleServer().setAddress("127.0.0.1:7002");
    config.setThreads(8);

    provider = Caching.getCachingProvider("org.redisson.jcache.JCachingProvider");
    CacheManager cacheManager = provider.getCacheManager();

    Configuration<Long, Entity> configuration = RedissonConfiguration.fromConfig(config, BenchmarkProperties.createMutableConfiguration());

    client = Redisson.create(config);

    cache = cacheManager.createCache("entities", BenchmarkProperties.createMutableConfiguration());

    cache.putAll(BenchmarkProperties.createEntities());
  }

  @TearDown
  public void shutdown() {
    provider.close();
    client.shutdown();
    server.stop();
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