package ch.cern.c2mon.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

/**
 * @author Szymon Halastra
 */


@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface C2monBenchmark {
}
