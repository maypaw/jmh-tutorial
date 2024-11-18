package org.example.sentence;

import java.util.Arrays;

public class SentenceBuilderStringAdditionImpl implements SentenceBuilder {

    public String build(String... words) {
        return Arrays.stream(words).reduce("",
                (accumulator,word) -> accumulator + word + " "
        ) + ".";
    }
}
