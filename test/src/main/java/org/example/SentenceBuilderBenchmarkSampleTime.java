package org.example;

import org.example.sentence.SentenceBuilderStringAdditionImpl;
import org.example.sentence.SentenceBuilderStringBuilderImpl;
import org.example.sentence.SentenceBuilderStringConcatImpl;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SentenceBuilderBenchmarkSampleTime {

    private SentenceBuilderStringBuilderImpl sentenceBuilderStringBuilder;

    private SentenceBuilderStringConcatImpl sentenceBuilderStringConcat;

    private SentenceBuilderStringAdditionImpl sentenceBuilderStringAddition;

    private static final Random RANDOM = new Random();

    private String[] words;

    @Param({"10", "100", "10000"})
    private int numWords;

    @Setup
    public void setUpBuilders() {
        sentenceBuilderStringBuilder = new SentenceBuilderStringBuilderImpl();
        sentenceBuilderStringConcat = new SentenceBuilderStringConcatImpl();
        sentenceBuilderStringAddition = new SentenceBuilderStringAdditionImpl();

        words = IntStream.range(0, numWords)
                .mapToObj(i -> generateRandomWord())
                .toArray(String[]::new);
    }

    private String generateRandomWord() {
        int length = RANDOM.nextInt(5) + 5; // Random word length between 5 and 9

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + RANDOM.nextInt(26)));
        }
        return sb.toString();
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @Measurement(iterations = 1)
    public void benchmarkBaseline() {
        // empty benchmark - sanity check
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void sentenceBuilderStringBuilderBenchmark(Blackhole bh, SentenceBuilderBenchmarkSampleTime sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringBuilder.build(sentenceBuilderState.words));
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public void sentenceBuilderStringConcatBenchmark(Blackhole bh, SentenceBuilderBenchmarkSampleTime sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringConcat.build(sentenceBuilderState.words));
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public void sentenceBuilderStringAdditionBenchmark(Blackhole bh, SentenceBuilderBenchmarkSampleTime sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringAddition.build(sentenceBuilderState.words));
    }
}
