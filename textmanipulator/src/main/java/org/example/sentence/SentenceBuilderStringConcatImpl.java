package org.example.sentence;

import java.util.Arrays;

public class SentenceBuilderStringConcatImpl implements SentenceBuilder{

    public String build(String... words) {
        return Arrays.stream(words).reduce("",
                (accumulator,word) -> accumulator.concat(word).concat(" ")
        ).concat(".");
    }
}
