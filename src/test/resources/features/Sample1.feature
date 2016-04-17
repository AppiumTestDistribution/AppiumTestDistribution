@smoke
Feature: Sample1 Feature

  This feature file just sleeps for a 4 seconds (one second each scenario)
  It is not meant to do anything, but sleep
  So we can prove that it runs in parallel

  Scenario: Example 2 that sleep 1 seconds
    Given I accept the tip screen
    When I click on 4 number
    And I click on 3 number
    Then It should finnish

  Scenario: Example 3 that sleep 1 seconds
    Given I accept the tip screen
    When I click on 51 number
    And I click on 7 number
    Then It should finnish