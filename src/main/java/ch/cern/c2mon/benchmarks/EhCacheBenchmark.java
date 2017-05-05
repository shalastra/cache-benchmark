package ch.cern.c2mon.benchmarks;

import ch.cern.c2mon.utils.BenchmarkProperties;
import ch.cern.c2mon.utils.BenchmarkedMethods;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

/**
 * @author Szymon Halastra
 */


@Measurement(iterations = BenchmarkProperties.MEASUREMENT_ITERATIONS)
@Warmup(iterations = BenchmarkProperties.WARM_UP_ITERATIONS)
public class EhCacheBenchmark implements BenchmarkedMethods {

  @Override
  public void putEntityBenchmark() {

  }

  @Override
  public void getEntityBenchmark() {

  }
}
