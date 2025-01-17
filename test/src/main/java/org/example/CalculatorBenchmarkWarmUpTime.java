package org.example;

import org.example.calculator.CalculatorEnhancedForLoopImpl;
import org.example.calculator.CalculatorReduceImpl;
import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class CalculatorBenchmarkWarmUpTime {

    private static final int NUMBERS_SIZE = 100;

    private static final Random RANDOM = new Random();

    private CalculatorReduceImpl calculatorReduceImpl;

    private BigDecimal[] numbers;

    @Setup
    public void setupCalc() {
        calculatorReduceImpl = new CalculatorReduceImpl();

        numbers = DoubleStream.generate(() -> RANDOM.nextDouble(100)) // Generate random numbers between 0 and 99
                .limit(NUMBERS_SIZE)
                .mapToObj(number -> new BigDecimal(number, new MathContext(10, RoundingMode.HALF_UP)))
                .toArray(BigDecimal[]::new);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 3, time = 10)
    public BigDecimal benchmarkSingleWarmUp(CalculatorBenchmarkWarmUpTime calcState) {
        return calcState.calculatorReduceImpl.sum(calcState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    @Measurement(iterations = 3, time = 10)
    public BigDecimal benchmarkFiveWarmUps(CalculatorBenchmarkWarmUpTime calcState) {
        return calcState.calculatorReduceImpl.sum(calcState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 10)
    @Measurement(iterations = 3, time = 10)
    public BigDecimal benchmarkTenWarmUps(CalculatorBenchmarkWarmUpTime calcState) {
        return calcState.calculatorReduceImpl.sum(calcState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 20)
    @Measurement(iterations = 3, time = 10)
    public BigDecimal benchmarkTwentyWarmUps(CalculatorBenchmarkWarmUpTime calcState) {
        return calcState.calculatorReduceImpl.sum(calcState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 30)
    @Measurement(iterations = 3, time = 10)
    public BigDecimal benchmarkThirtyWarmUps(CalculatorBenchmarkWarmUpTime calcState) {
        return calcState.calculatorReduceImpl.sum(calcState.numbers);
    }
}
