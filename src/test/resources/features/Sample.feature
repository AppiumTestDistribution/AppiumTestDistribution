@smoke
Feature: Sample Feature

  This feature file just sleeps for a 4 seconds (one second each scenario)
  It is not meant to do anything, but sleep
  So we can prove that it runs in parallel

  Scenario: Scenario 1 Sample
    Given I login
    When I drag & drop
    Then drag and drop should have worked

  Scenario: Scenario 2 Sample
    Given I login
    When I drag & drop
    Then drag and drop should have worked