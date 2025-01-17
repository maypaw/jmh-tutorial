package org.example;

import org.example.sentence.SentenceBuilderStringAdditionImpl;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SentenceBuilderBenchmarkWarmUpTime {

    private static final int WORDS_SIZE = 100;
    
    private SentenceBuilderStringAdditionImpl sentenceBuilderStringAddition;

    private static final Random RANDOM = new Random();

    private String[] words;

    @Setup
    public void setUpBuilders() {
        sentenceBuilderStringAddition = new SentenceBuilderStringAdditionImpl();

        words = IntStream.range(0, WORDS_SIZE)
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
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(iterations = 5, time = 10)
    @Warmup(iterations = 5)
    public void sentenceBuilderFiveWarmUps(Blackhole bh, SentenceBuilderBenchmarkWarmUpTime sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringAddition.build(sentenceBuilderState.words));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(iterations = 5, time = 10)
    @Warmup(iterations = 10)
    public void sentenceBuilderTenWarmUps(Blackhole bh, SentenceBuilderBenchmarkWarmUpTime sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringAddition.build(sentenceBuilderState.words));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(iterations = 5, time = 10)
    @Warmup(iterations = 30)
    public void sentenceBuilderTwentyWarmUps(Blackhole bh, SentenceBuilderBenchmarkWarmUpTime sentenceBuilderState) {
        bh.consume(sentenceBuilderState.sentenceBuilderStringAddition.build(sentenceBuilderState.words));
    }

}
