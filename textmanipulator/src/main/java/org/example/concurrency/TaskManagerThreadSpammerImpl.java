package org.example.concurrency;

import org.example.calculator.Calculator;
import org.example.sentence.SentenceBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class TaskManagerThreadSpammerImpl {

    private SentenceBuilder sentenceBuilder;

    private Calculator calculator;

    public TaskManagerThreadSpammerImpl(SentenceBuilder sentenceBuilder, Calculator calculator) {
        this.sentenceBuilder = sentenceBuilder;
        this.calculator = calculator;
    }

    public Object resolveTasks(Object[][] input) {
        Arrays.stream(input)
                .map(inputPart ->
                    switch (inputPart) {
                        case null -> null;
                        case String[] inputStrings -> CompletableFuture.supplyAsync(() -> sentenceBuilder.build(inputStrings));
                        case BigDecimal[] inputNumbers -> CompletableFuture.supplyAsync(() -> calculator.sum(inputNumbers));
                        default -> null;
                })
                .map()
    }
}
