package org.example.concurrency;

import org.example.calculator.Calculator;
import org.example.sentence.SentenceBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class TaskManagerPlatformThreadsImpl<T> implements TaskManager<T> {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    private SentenceBuilder sentenceBuilder;

    private Calculator calculator;

    public TaskManagerPlatformThreadsImpl(SentenceBuilder sentenceBuilder, Calculator calculator) {
        this.sentenceBuilder = sentenceBuilder;
        this.calculator = calculator;
    }

    @Override
    public List<T> resolveTasks(T[][] inputs) {
        List<? extends CompletableFuture<? extends Serializable>> completableFutures = Arrays.stream(inputs)
                .map(input -> {
                    if (input instanceof String[] strings) {
                        return CompletableFuture.supplyAsync(() -> sentenceBuilder.build(strings), executorService);
                    }

                    if (input instanceof BigDecimal[] numbers) {
                        return CompletableFuture.supplyAsync(() -> calculator.sum(numbers), executorService);

                    }

                    return null;
                }).toList();
        CompletableFuture<Void> allOf = CompletableFuture.allOf((CompletableFuture<?>) completableFutures);
        return allOf.thenApply(v -> completableFutures.stream()
                .map(CompletableFuture::join)
                        .map(object -> (T) object)
                .toList())
                .join();
    }
}
