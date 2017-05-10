package ch.cern.c2mon.benchmarks.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

import ch.cern.c2mon.benchmarks.AbstractBenchmark;
import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkUtils;
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
public class RedissonBenchmark implements AbstractBenchmark {

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

    Configuration<Long, Entity> configuration = RedissonConfiguration.fromConfig(config, BenchmarkUtils.createMutableConfiguration());

    client = Redisson.create(config);

    cache = cacheManager.createCache("entities", BenchmarkUtils.createMutableConfiguration());

    cache.putAll(BenchmarkUtils.createEntities());
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
    Entity entity = cache.get(BenchmarkUtils.getRandomKey(cache));

    return entity;
  }

  @Benchmark
  @Override
  public Entity getAndPutEntity() {
    Entity entity = cache.getAndPut(BenchmarkUtils.getRandomKey(cache), new Entity());

    return entity;
  }

  @Benchmark
  @Override
  public Map<Long, Entity> getAllEntities() {
    Map<Long, Entity> entities = cache.getAll(BenchmarkUtils.getKeys(cache));

    return entities;
  }

  @Benchmark
  @Override
  public void putAllEntities() {
    cache.putAll(BenchmarkUtils.createEntities());
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
    boolean isRemoved = cache.remove(BenchmarkUtils.getRandomKey(cache));

    return isRemoved;
  }

  @Benchmark
  @Override
  public Entity getAndRemoveEntity() {
    Entity entity = cache.getAndRemove(BenchmarkUtils.getRandomKey(cache));
    return entity;
  }

  @Benchmark
  @Override
  public boolean replaceEntity() {
    boolean isReplaced = cache.replace(BenchmarkUtils.getRandomKey(cache), new Entity());

    return isReplaced;
  }

  @Benchmark
  @Override
  public Entity getAndReplaceEntity() {
    Entity entity = cache.getAndReplace(BenchmarkUtils.getRandomKey(cache), new Entity());

    return entity;
  }

  @Benchmark
  @Override
  public void removeAllEntities() {
    cache.removeAll(BenchmarkUtils.getKeys(cache));
  }
}