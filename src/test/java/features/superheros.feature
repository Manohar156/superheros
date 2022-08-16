Feature: Super Heroes features

  Background: creating users
    Given User Details

  Scenario: Verify that the user is able to created
    When creating a User
    Then User must Created

  Scenario: Verify that throwing a message when super name is null
    When creating user with super name null
    Then message thrown

  Scenario: Verify that throwing a message when age is zero
    When creating a user with age is zero
    Then message thrown age is required

  Scenario: Verify that throwing a message when name is null
    When creating user with name null
    Then message thrown name is required

  Scenario: Verify that throwing a message when profession is null
    When creating user with profession null
    Then message thrown profession is required

  Scenario: Verify that the user is created when canFly status is blank
    When creating user with blank canFly
    Then User must Created

  Scenario: Verify that the profession is updated
    When creating a User
    And updating profession
    Then profession must be updated

  Scenario: Verify that the age is updated
    When creating a User
    And updating age
    Then age must be updated

  Scenario: Verify that the name is updated
    When creating a User
    And updating name
    Then name must be updated

  Scenario: Verify that the super name is updated
    When creating a User
    And updating super name
    Then super name must be updated

  Scenario: Verify that the canFly is updated
    When creating a User
    And updating canFly
    Then canFly must be updated

  Scenario: Verify that the user list is displayed
    When creating a User
    Then List of user must be displayed

  Scenario: Verify that the user ID has to displayed
    When creating a User
    Then user with particular ID must be displayed

  Scenario: Verify that user is deleted
    When creating a User
    Then Deleting user

  Scenario: Verify that deleting the user not in list
    When creating a User
    Then Deleting Unknown user




