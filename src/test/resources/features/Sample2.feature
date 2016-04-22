@smoke
Feature: Sample2 Feature

  This feature file just sleeps for a 4 seconds (one second each scenario)
  It is not meant to do anything, but sleep
  So we can prove that it runs in parallel

  Scenario: Example 1 to add 4
    Given I accept the tip screen
    When I click on 1 number
    And I click on 4 number
    Then It should finnish

  Scenario: Example 8 to add 8
    Given I accept the tip screen
    When I click on 8 number
    And I click on 8 number
    Then It should finnish