package org.example.sentence;

import java.util.Arrays;

public class SentenceBuilderStringBuilderImpl implements SentenceBuilder{

    public String build(String... words) {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(words).forEach(word -> {
            builder.append(word)
                    .append(" ");
        });

        builder.append(".");
        return builder.toString();
    }

}
