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

    /**
     * Turns on or off logging.  If enabled it will print each PUSH and POP to the Stack.
     * @param enabled
     */
    public void setLogging(boolean enabled) {
        mPrintLogging = enabled;
    }

    /**
     * Calculate the result of the RPN String.  Returns the value as a double.
     * @param input
     * @return
     */
    public double calculate(String input) {
        if(mInputQueue.size() != 0) {
            mInputQueue = new ArrayDeque<Token>(input.length());
            mCurrentToken = 0;
        }

        StringTokenizer tokenizer = new StringTokenizer(input, " ");
        while(tokenizer.hasMoreTokens()) {
            processToken(tokenizer.nextToken());
            mCurrentToken++;
        }

        if(mInputQueue.size() != 1)
            throw new IllegalArgumentException("Invalid result size of " + mInputQueue.size());

        return mInputQueue.pop().getValue();
    }

    /**
     * Process an individual token parsed from the RPN String
     * @param token The token being processed from the RPN String
     */
    private void processToken(String token) {
        //Single char, check its type for correct parsing
        if(token.length() == 1) {
            char temp = token.charAt(0);
            //Digit
            if(Character.isDigit(temp)) {
                mInputQueue.push(new ValueToken(token));
                if(mPrintLogging) System.out.println("Pushing token " + token);
            }
            //Math symbol such as + - * /
            else if(isMathSymbol(temp)) {
                processOperator(token);
            }
            //Illegal character
            else {
                throw new IllegalArgumentException("Invalid token character " + token + " at position " + mCurrentToken + ".");
            }
        }
        //Has to be a digit if length > 1
        else {
            mInputQueue.add(new ValueToken(token));
            if(mPrintLogging) System.out.println("Pushing token " + token);
        }
    }

    /**
     * An operator was detected as the current token and needs to be processed
     * @param operand The operand detected
     */
    private void processOperator(String operand) {
        if(mInputQueue.size() < 2)
            throw new IllegalArgumentException("Does not have two operands for operation " + operand + " at position " + mCurrentToken + ".");

        char operator = operand.charAt(0); //this is safe becauase only called if length is 1
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

    /**
     * Determines if a token is a math symbol (+,-,/,*) or not
     * @param token The token being examined
     * @return True if a math symbol (+,-,/,*), false otherwise
     */
    private boolean isMathSymbol(char token) {
        switch(token) {
            case '+':
            case '-':
            case '/':
            case '*':
                return true;
            default: return false;
        }
    }

}
