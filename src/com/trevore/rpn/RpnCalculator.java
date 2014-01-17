package com.trevore.rpn;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class RpnCalculator {

    private Deque<Token> mInputQueue = new ArrayDeque<Token>();
    private int mCurrentToken = 0;
    private boolean mPrintLogging = false;

    public RpnCalculator() {

    }
    
    public RpnCalculator(boolean log) {
        mPrintLogging = log;
    }
    
    public void setLogging(boolean enabled) {
        mPrintLogging = enabled;
    }

    public double calculate(String input) {
        if(mInputQueue.size() != 0)
            mInputQueue = new ArrayDeque<Token>(input.length());

        StringTokenizer tokenizer = new StringTokenizer(input, " ");
        while(tokenizer.hasMoreTokens()) {
            processToken(tokenizer.nextToken());
            mCurrentToken++;
        }

        if(mInputQueue.size() != 1)
            throw new IllegalArgumentException("Invalid result size of " + mInputQueue.size());

        return mInputQueue.pop().getValue();
    }

    private void processToken(String input) {
        //Single char, check its type for correct parsing
        if(input.length() == 1) {
            char temp = input.charAt(0);
            //Digit
            if(Character.isDigit(temp)) {
                mInputQueue.push(new ValueToken(input));
                if(mPrintLogging) System.out.println("Pushing input " + input);
            }
            //Math symbol such as + - * /
            else if(isMathSymbol(temp)) {
                processOperator(input);
            }
            //Illegal character
            else {
                throw new IllegalArgumentException("Invalid input character " + input + " at position " + mCurrentToken + ".");
            }
        }
        //Has to be a digit if length > 1
        else {
            mInputQueue.add(new ValueToken(input));
            if(mPrintLogging) System.out.println("Pushing input " + input);
        }
    }

    private void processOperator(String input) {
        if(mInputQueue.size() < 2)
            throw new IllegalArgumentException("Does not have two operands for operation " + input + " at position " + mCurrentToken + ".");

        char operator = input.charAt(0); //this is safe becauase only called if length is 1
        double first = mInputQueue.pop().getValue();
        double second = mInputQueue.pop().getValue();
        ValueToken result = null;

        if(mPrintLogging) System.out.println("Popping " + first + " and " + second);

        switch(operator) {
            case '+':
                result = new ValueToken(second + first);
                mInputQueue.push(result);
                break;
            case '-':
                result = new ValueToken(second - first);
                mInputQueue.push(result);
                break;
            case '/':
                result = new ValueToken(second / first);
                mInputQueue.push(result);
                break;
            case '*':
                result = new ValueToken(second * first);
                mInputQueue.push(result);
                break;
        }

        if(result != null && mPrintLogging) System.out.println("Pushing result " + result.getValue());
    }

    private boolean isMathSymbol(char input) {
        switch(input) {
            case '+':
            case '-':
            case '/':
            case '*':
                return true;
            default: return false;
        }
    }

}
