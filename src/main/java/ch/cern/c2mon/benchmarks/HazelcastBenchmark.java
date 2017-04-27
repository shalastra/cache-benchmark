package ch.cern.c2mon.benchmarks;

import java.util.concurrent.TimeUnit;

import ch.cern.c2mon.entities.Entity;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import com.hazelcast.cache.ICache;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICacheManager;
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

  ICache<Long, Entity> cache;

  @Setup
  public void setup() {
    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

    ICacheManager cacheManager = hazelcastInstance.getCacheManager();

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


  @Override
  public void getEntryBenchmark() {
  }
}
