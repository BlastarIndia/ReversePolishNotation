package com.trevore.rpn.tests;

import com.trevore.rpn.RpnCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RpnCalculatorTest {

    @Test
    public void testCalculate() throws Exception {
        RpnCalculator calculator = new RpnCalculator();

        assertEquals(14.0, calculator.calculate("5 1 2 + 4 * + 3 -"), 0);
        assertEquals(2, calculator.calculate("1 1 +"), 0);
        assertEquals(0, calculator.calculate("1 1 -"), 0);
        assertEquals(-1, calculator.calculate("0 1 -"), 0);
        assertEquals(0, calculator.calculate("-1 1 +"), 0);
        assertEquals(2, calculator.calculate("6 3 /"), 0);
        assertEquals(6, calculator.calculate("6"), 0);
        assertEquals(7.5, calculator.calculate("15 2 /"), 0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoOperator() {
        RpnCalculator calculator = new RpnCalculator();
        calculator.calculate("2 2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooManyOperators() {
        RpnCalculator calculator = new RpnCalculator();
        calculator.calculate("2 2 + +");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOnlyOperators() {
        RpnCalculator calculator = new RpnCalculator();
        calculator.calculate("+ +");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCharacters() {
        RpnCalculator calculator = new RpnCalculator();
        calculator.calculate("2 a +");
    }
}
