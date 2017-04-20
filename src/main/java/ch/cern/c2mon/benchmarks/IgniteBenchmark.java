package ch.cern.c2mon.benchmarks;

import ch.cern.c2mon.annotations.C2monBenchmark;
import ch.cern.c2mon.utils.BechmarkedMethods;
import org.openjdk.jmh.annotations.Benchmark;

/**
 * @author Szymon Halastra
 */

@C2monBenchmark
public class IgniteBenchmark implements BechmarkedMethods {

  @Benchmark
  @Override
  public void putEntryBenchmark() {

  }

  @Benchmark
  @Override
  public void getEntryBenchmark() {

  }

  @Benchmark
  @Override
  public void setEntryBenchmark() {

  }
}
