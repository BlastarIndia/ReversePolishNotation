package com.trevore.rpn;

public class ValueToken implements Token {

    private double value;

    public ValueToken(String input) {
        try {
            value = Double.parseDouble(input);
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException("Illegal number (" + input + ") as input.");
        }
    }

    public ValueToken(double input) {
        value = input;
    }

    public double getValue() {
        return value;
    }
}
