package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    private Calculator calculator = new Calculator();

    @Nested
    @DisplayName("add tests")
    class AddTests {
        @Test
        @DisplayName("adds two positive numbers")
        void addTwoPositives() {
            assertEquals(5, calculator.add(2, 3));
        }

        @Test
        @DisplayName("adds two negative numbers")
        void addTwoNegatives() {
            assertEquals(-5, calculator.add(-2, -3));
        }

        @Test
        @DisplayName("adds positive and negative number")
        void addPositiveAndNegative() {
            assertEquals(1, calculator.add(5, -4));
        }

        @Test
        @DisplayName("adds zero")
        void addWithZero() {
            assertEquals(5, calculator.add(5, 0));
        }
    }

    @Nested
    @DisplayName("subtract tests")
    class SubtractTests {
        @Test
        @DisplayName("subtracts smaller from larger")
        void subtractLargerFromLarger() {
            assertEquals(1, calculator.subtract(5, 4));
        }

        @Test
        @DisplayName("subtracts larger from smaller gives negative")
        void subtractLargerFromSmaller() {
            assertEquals(-1, calculator.subtract(4, 5));
        }
    }

    @Nested
    @DisplayName("multiply tests")
    class MultiplyTests {
        @Test
        @DisplayName("multiplies two numbers")
        void multiplyTwoNumbers() {
            assertEquals(6, calculator.multiply(2, 3));
        }

        @Test
        @DisplayName("multiplies by zero")
        void multiplyByZero() {
            assertEquals(0, calculator.multiply(5, 0));
        }
    }

    @Nested
    @DisplayName("divide tests")
    class DivideTests {
        @Test
        @DisplayName("divides without remainder")
        void divideWithoutRemainder() {
            assertEquals(3, calculator.divide(9, 3));
        }

        @Test
        @DisplayName("throws on division by zero")
        void divideByZero() {
            assertThrows(IllegalArgumentException.class, () -> calculator.divide(5, 0));
        }
    }

    @Nested
    @DisplayName("sum varargs tests")
    class SumTests {
        @Test
        @DisplayName("sums multiple numbers")
        void sumMultipleNumbers() {
            assertEquals(15, calculator.sum(1, 2, 3, 4, 5));
        }

        @Test
        @DisplayName("sums single number")
        void sumSingleNumber() {
            assertEquals(7, calculator.sum(7));
        }

        @Test
        @DisplayName("sums empty array returns zero")
        void sumEmpty() {
            assertEquals(0, calculator.sum());
        }
    }
}