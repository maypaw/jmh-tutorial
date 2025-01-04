/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

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
public class CalculatorBenchmarkThroughput {

    private static final Random RANDOM = new Random();

    private CalculatorReduceImpl calculatorReduceImpl;

    private CalculatorEnhancedForLoopImpl calculatorEnhancedForLoopImpl;

    private BigDecimal[] numbers;

    @Param({ "10", "100", "10000" })
    private int numberSize;

    @Setup
    public void setupCalc() {
        calculatorReduceImpl = new CalculatorReduceImpl();
        calculatorEnhancedForLoopImpl = new CalculatorEnhancedForLoopImpl();

        numbers = DoubleStream.generate(() -> RANDOM.nextDouble(100)) // Generate random numbers between 0 and 99
                .limit(numberSize)
                .mapToObj(number -> new BigDecimal(number, new MathContext(10, RoundingMode.HALF_UP)))
                .toArray(BigDecimal[]::new);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 1, time = 30)
    public void benchmarkBaseline() {
        // empty benchmark - sanity check
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 3, time = 30)
    public BigDecimal benchmarkSumWithReduce(CalculatorBenchmarkThroughput calcState) {
        return calcState.calculatorReduceImpl.sum(calcState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 3, time = 30)
    public BigDecimal benchmarkSumWithEnhancedForLoop(CalculatorBenchmarkThroughput calcState) {
        return calcState.calculatorEnhancedForLoopImpl.sum(calcState.numbers);
    }
}
