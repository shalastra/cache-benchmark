package ch.cern.c2mon.benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;

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

////  @Setup
//  public void setup() {
//    CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
//    MutableConfiguration<Long, Entity> config = new MutableConfiguration<>();
//
//    cache = cacheManager.createCache("entities", config);
//  }
//
////  @TearDown
//  public void shutdown() {
//    Caching.getCachingProvider().close();
//  }

//  @Benchmark
  @Override
  public void putEntityBenchmark() {
    Entity entity = new Entity();

    cache.put(entity.getId(), entity);
  }

//  @Benchmark
  @Override
  public void getEntityBenchmark() {
    cache.get(ThreadLocalRandom.current().nextLong(100000, 999999));
  }
}
