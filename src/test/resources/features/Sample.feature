@smoke
Feature: Sample Feature

  This feature file just sleeps for a 4 seconds (one second each scenario)
  It is not meant to do anything, but sleep
  So we can prove that it runs in parallel

  Scenario: Example 1 that sleep 1 seconds
    Given I accept the tip screen
    When I click on 2 number
    And I click on 3 number
    Then It should finnish

