package org.example;

import org.example.sentence.SentenceBuilderStringAdditionImpl;
import org.example.sentence.SentenceBuilderStringBuilderImpl;
import org.example.sentence.SentenceBuilderStringConcatImpl;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SentenceBuilderBenchmark {

    private SentenceBuilderStringBuilderImpl sentenceBuilderStringBuilder;

    private SentenceBuilderStringConcatImpl sentenceBuilderStringConcat;

    private SentenceBuilderStringAdditionImpl sentenceBuilderStringAddition;

    @Setup
    public void setUpBuilders() {
        sentenceBuilderStringBuilder = new SentenceBuilderStringBuilderImpl();
        sentenceBuilderStringConcat = new SentenceBuilderStringConcatImpl();
        sentenceBuilderStringAddition = new SentenceBuilderStringAdditionImpl();
    }

    @State(Scope.Benchmark)
    public static class ShortSentenceState {
        String[] sentence = { "The", "quick", "brown", "fox", "jumps", "over", "a", "lazy", "sleeping", "dog" };
    }

    @State(Scope.Benchmark)
    public static class LongSentenceState {
        String[] sentence = {
                "The", "bright", "morning", "sun", "illuminated", "the", "peaceful", "valley", "where", "ancient",
                "trees", "swayed", "gently", "in", "the", "warm", "breeze", "while", "colorful", "birds",
                "soared", "gracefully", "through", "clear", "blue", "skies", "and", "butterflies", "danced", "among",
                "fragrant", "wildflowers", "that", "carpeted", "the", "rolling", "meadows", "beneath", "majestic", "mountains",
                "whose", "snow-capped", "peaks", "touched", "wispy", "clouds", "drifting", "lazily", "across", "the",
                "horizon", "as", "deer", "grazed", "contentedly", "near", "a", "crystal", "clear", "stream",
                "that", "meandered", "through", "the", "lush", "landscape", "creating", "melodic", "sounds", "which",
                "blended", "harmoniously", "with", "rustling", "leaves", "and", "chirping", "songbirds", "in", "nature's",
                "grand", "symphony", "of", "life", "flourishing", "within", "this", "untouched", "wilderness", "sanctuary",
                "where", "time", "seemed", "to", "stand", "still", "in", "perfect", "natural", "balance"
        };
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void benchmarkBaseline() {
        // empty benchmark - sanity check
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public String sentenceBuilderStringBuilderBenchmarkShort(SentenceBuilderBenchmark sentenceBuilderState, ShortSentenceState sentenceState) {
        return sentenceBuilderState.sentenceBuilderStringBuilder.build(sentenceState.sentence);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public String sentenceBuilderStringConcatBenchmarkShort(SentenceBuilderBenchmark sentenceBuilderState, ShortSentenceState sentenceState) {
        return sentenceBuilderState.sentenceBuilderStringConcat.build(sentenceState.sentence);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public String sentenceBuilderStringAdditionBenchmarkShort(SentenceBuilderBenchmark sentenceBuilderState, ShortSentenceState sentenceState) {
        return sentenceBuilderState.sentenceBuilderStringAddition.build(sentenceState.sentence);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public String sentenceBuilderStringBuilderBenchmarkLong(SentenceBuilderBenchmark sentenceBuilderState, LongSentenceState sentenceState) {
        return sentenceBuilderState.sentenceBuilderStringBuilder.build(sentenceState.sentence);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public String sentenceBuilderStringConcatBenchmarkLong(SentenceBuilderBenchmark sentenceBuilderState, LongSentenceState sentenceState) {
        return sentenceBuilderState.sentenceBuilderStringConcat.build(sentenceState.sentence);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public String sentenceBuilderStringAdditionBenchmarkLong(SentenceBuilderBenchmark sentenceBuilderState, LongSentenceState sentenceState) {
        return sentenceBuilderState.sentenceBuilderStringAddition.build(sentenceState.sentence);
    }
}
