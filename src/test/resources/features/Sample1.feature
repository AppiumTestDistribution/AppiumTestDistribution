@smoke
Feature: Sample1 Feature

  This feature file just sleeps for a 4 seconds (one second each scenario)
  It is not meant to do anything, but sleep
  So we can prove that it runs in parallel

  Scenario Outline: Example 2 that sleep 1 seconds
#    Given I accept the tip screen
    When I click on <t1> number
    And I click on <t2> number
    Then It should finnish
    Examples:
      | t1 | t2 |
      | 4  | 3  |
      | 1  | 2  |

  Scenario: Example 3 that sleep 1 seconds
#    Given I accept the tip screen
    When I click on 5 number
    And I click on 2 number
    Then It should finnish