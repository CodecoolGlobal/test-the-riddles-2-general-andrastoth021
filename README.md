# Test the Riddles - Test Automation Project

## Project Description

This team work project is a test automation suite for the "Test the Riddles" application.
The purpose of the suite is to automate functional and regression tests for the system under test (SUT).
The test cases are written using Java, Selenium, and JUnit, ensuring comprehensive coverage of the application's core
functionalities.

## System Under Test (SUT)

[![SUT][SUT-Screenshot]][sut-screenshot]

The SUT for this project is the **Reptile Riddles** application. It is a riddle game where players solve quizzes based
on questions. You can find the SUT repository here:
[Reptile Riddles SUT Repository](https://github.com/CodecoolGlobal/reptile-riddles-2-general-reveszter).

## Features Tested

- **User registration and login:** Automated tests for creating new users and verifying login functionalities.
- **Riddle solving:** Tests to verify that users can solve riddles based on the provided questions.
- **Role-based actions:** Tests that ensure different user roles (e.g., quizmaster) perform specific tasks correctly.
- **Score tracking:** Verifies that the system tracks and updates user scores based on their answers.


## Technologies Used

- [![Java][java]](https://www.java.com/en/) The primary programming language for writing the test automation scripts.
- [![Selenium][selenium]](https://www.selenium.dev/) Web browser automation tool used to interact with the application's front-end.
- [![JUnit][junit]](https://junit.org/junit5/) Testing framework used for structuring and executing test cases.
- [![Maven][maven]](https://maven.apache.org/) Build automation tool used to manage dependencies and run test cases.


## Installation Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/CodecoolGlobal/test-the-riddles-2-general-andrastoth021.git

2. **Navigate to the project folder:**

   cd test-the-riddles-2-general-andrastoth021

3. **Set up the project dependencies:**

   Ensure you have Maven installed on your system, then run the following command to download all necessary dependencies:
   mvn clean install 

4. **Set up the SUT (System Under Test):**
   To run the tests, the SUT must be running. Clone the SUT repository from Reptile Riddles SUT Repository,
   follow the setup instructions in that repository, and ensure the application is running before running the tests.

5. **Set up the environment variables:**

   Environment Variables
    You need to set a few environment variables for the automated tests.

    - Create a .env file in the root folder (where .gitignore and pom.xml are).
    - Copy the code below into the newly created .env file.
    - Provide variables for your .env file.

    REPTILE_USER_USERNAME=
    REPTILE_USER_EMAIL=
    REPTILE_USER_PASSWORD=
    
    REPTILE_QUIZMASTER_USERNAME=
    REPTILE_QUIZMASTER_EMAIL=
    REPTILE_QUIZMASTER_PASSWORD=

6. **Run the tests:**

    Once the SUT is running and the environment variables are set, you can run the test suite using Maven:

    mvn test

    ### Test Structure*

    The tests are organized into the following categories:

    - LoginTests: Verifies the login functionality for both users and quizmasters.
    - RiddleTests: Tests related to solving riddles and verifying correct answers.
    - RoleBasedTests: Ensures that different roles (users and quizmasters) can perform their respective tasks.
   
    ### Test Cases

    1. Preconditions:
    - The user must already have a valid account in the web application.
    - The user must be logged in to the web application.
    - At least one riddle must be available in the system for the user to solve.
    - The browser (e.g., Chrome) must be open and the web application's homepage must be loaded.

    2. Test Steps:
    - Step 1: Navigate to the riddle page.
    - Step 2: Input the answer to the current riddle.
    - Step 3: Submit the answer.
    - Step 4: Verify that the riddle is marked as solved.

    3. Test Data:
    - Username, password, and sample answers for riddles.

    4. Expected Result:
    - The riddle is marked as solved, and the user's score is updated accordingly.

    5. Postconditions:
    - Ensure the user is logged out after the test.
    - Clean up any test data or reset states that were changed during the test.


[![TEST][TEST-Result]][test-result]


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[sut-screenshot]: src/test/resources/images/logo_big.png
[test-result]: src/test/resources/images/test.png
[java]: https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logoColor=%23ED8B00&labelColor=white&color=%23ED8B00&link=https%3A%2F%2Fwww.java.com%2Fen%2F
[junit]: https://img.shields.io/badge/junit_5-%2325A162?style=for-the-badge&logo=junit5&logoColor=%2325A162&labelColor=white&color=%2325A162&link=https%3A%2F%2Fjunit.org%2Fjunit5%2F
[selenium]: https://img.shields.io/badge/selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=%2343B02A&labelColor=white&color=%2343B02A&link=https%3A%2F%2Fwww.selenium.dev%2F
[maven]: https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white&link=https%3A%2F%2Fmaven.apache.org%2F

