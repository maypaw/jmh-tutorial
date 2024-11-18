package org.example.calculator;

import java.math.BigDecimal;
import java.util.Arrays;

public class CalculatorReduceImpl implements Calculator {

    public BigDecimal sum(BigDecimal... numbers) {
        return Arrays.stream(numbers)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
