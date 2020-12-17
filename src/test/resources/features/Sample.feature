@smoke
Feature: Sample Feature

  This feature file just sleeps for a 4 seconds (one second each scenario)
  It is not meant to do anything, but sleep
  So we can prove that it runs in parallel

  Scenario: As a user I want to login
    Given I login
    When I drag & drop
    Then drag and drop should have worked

  Scenario: As a user I should be able to edit myprofile page
    Given I login
    When I drag & drop
    Then drag and drop should have worked