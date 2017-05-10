package ch.cern.c2mon.benchmarks;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.cern.c2mon.entities.Entity;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;

/**
 * @author Szymon Halastra
 */

@Slf4j
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public abstract class AbstractBenchmark {

  public abstract void putEntity();

  public abstract Entity getEntity();

  public abstract Entity getAndPutEntity();

  public abstract Map<Long, Entity> getAllEntities();

  public abstract void putAllEntities();

  public abstract boolean putIfAbsentEntity();

  public abstract boolean removeEntity();

  public abstract Entity getAndRemoveEntity();

  public abstract boolean replaceEntity();

  public abstract Entity getAndReplaceEntity();

  public abstract void removeAllEntities();
}
