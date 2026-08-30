Feature: Calculator operations
  As a user
  I want to use a calculator
  So I can perform basic arithmetic operations

  Scenario: Add two numbers
    Given I have a calculator
    When I add 2 and 3
    Then the result should be 5

  Scenario: Subtract two numbers
    Given I have a calculator
    When I subtract 3 from 5
    Then the result should be 2

  Scenario: Multiply two numbers
    Given I have a calculator
    When I multiply 2 by 3
    Then the result should be 6

  Scenario: Divide two numbers
    Given I have a calculator
    When I divide 6 by 2
    Then the result should be 3

  Scenario: Divide by zero throws exception
    Given I have a calculator
    When I divide 5 by 0
    Then an exception should be thrown