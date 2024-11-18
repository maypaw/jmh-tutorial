package org.example.calculator;

import java.math.BigDecimal;

public class CalculatorEnhancedForLoopImpl implements Calculator{


    @Override
    public BigDecimal sum(BigDecimal... numbers) {
        BigDecimal accumulator = BigDecimal.ZERO;

        for (BigDecimal number : numbers) {
            accumulator = accumulator.add(number);
        }

        return accumulator;
    }
}
