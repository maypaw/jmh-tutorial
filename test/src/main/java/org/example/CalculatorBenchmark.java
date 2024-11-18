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
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class CalculatorBenchmark {

    private CalculatorReduceImpl calculatorReduceImpl;

    private CalculatorEnhancedForLoopImpl calculatorEnhancedForLoopImpl;

    @State(Scope.Benchmark)
    public static class NumbersState {
        public BigDecimal[] numbers = new BigDecimal[]{
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(11),
                BigDecimal.valueOf(13),
                BigDecimal.valueOf(6),
                BigDecimal.valueOf(7),
        };
    }

    @State(Scope.Benchmark)
    public static class VastNumbersState {
        public BigDecimal[] numbers = new BigDecimal[]{
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(11),
                BigDecimal.valueOf(13),
                BigDecimal.valueOf(6),
                BigDecimal.valueOf(7),
        };

        @Setup
        public void setNumbers() {
            BigDecimal[] bigDecimals = new BigDecimal[10000];
            for (int i = 0; i < 10000; i++) {
                bigDecimals[i] = BigDecimal.valueOf(i);
            }

            numbers = bigDecimals;
        }
    }

    @Setup
    public void setupCalc() {
        calculatorReduceImpl = new CalculatorReduceImpl();
        calculatorEnhancedForLoopImpl = new CalculatorEnhancedForLoopImpl();
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void benchmarkBaseline() {
        // empty benchmark - sanity check
    }


    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public BigDecimal benchmarkSumWithReduceSmallArray(CalculatorBenchmark calcState, NumbersState numbersState) {
        return calcState.calculatorReduceImpl.sum(numbersState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public BigDecimal benchmarkSumWithReduceVastArray(CalculatorBenchmark calcState, VastNumbersState numbersState) {
        return calcState.calculatorReduceImpl.sum(numbersState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public BigDecimal benchmarkSumWithEnhancedForLoopSmallArray(CalculatorBenchmark calcState, NumbersState numbersState) {
        return calcState.calculatorEnhancedForLoopImpl.sum(numbersState.numbers);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public BigDecimal benchmarkSumWithEnhancedForLoopVastArray(CalculatorBenchmark calcState, VastNumbersState numbersState) {
        return calcState.calculatorEnhancedForLoopImpl.sum(numbersState.numbers);
    }
}
