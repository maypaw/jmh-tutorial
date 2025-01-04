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
public class SentenceBuilderBenchmarkThroughput {

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
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 1, time = 30)
    public void benchmarkBaseline() {
        // empty benchmark - sanity check
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 3, time = 30)
    public void sentenceBuilderStringBuilderBenchmark(Blackhole bh, SentenceBuilderBenchmarkThroughput sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringBuilder.build(sentenceBuilderState.words));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 3, time = 30)
    public void sentenceBuilderStringConcatBenchmark(Blackhole bh, SentenceBuilderBenchmarkThroughput sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringConcat.build(sentenceBuilderState.words));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 3, time = 30)
    public void sentenceBuilderStringAdditionBenchmark(Blackhole bh, SentenceBuilderBenchmarkThroughput sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringAddition.build(sentenceBuilderState.words));
    }
}
