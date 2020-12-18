@smoke
Feature: Sample1 Feature

  This feature file just sleeps for a 4 seconds (one second each scenario)
  It is not meant to do anything, but sleep
  So we can prove that it runs in parallel

  Scenario Outline: Scenario 1 Sample 1
    When I click on <t1> number
    And I click on <t2> number
    Then It should finish
    Examples:
      | t1 | t2 |
      | 4  | 3  |
      | 1  | 2  |

  Scenario: Scenario 2 Sample 1
    Given I login
    When I drag & drop
    Then drag and drop should have worked