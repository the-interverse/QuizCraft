# QuizCraft
Kirill Utyashev - KirillUtyashev  
Egor Dolgov - egordolgov  
Yasser Noori - the_interverse  
Katherine Lambert - Katherine-lambert  
# QuizCraft
Collaborative Quiz Creation Tool

## Authors
Team QuizCraft 
1. Kirill Utyashev - KirillUtyashev
2. Egor Dolgov - egordolgov  
3. Yasser Noori - the_interverse  
4. Katherine Lambert - Katherine-lambert

## Project Summary

QuizCraft is an innovative quiz management tool designed for educators, students, and content creators. 
It allows users to create, manage, and share quizzes seamlessly.
With its robust user authentication system, integration with AI tools for automatic question generation, and real-time quiz management,
QuizCraft simplifies the process of quiz creation and management.

## Why QuizCraft?

Simplifies quiz creation by parsing course materials (PDFs and text files).
Generates quizzes automatically using AI-powered engines.
Provides secure user authentication and role-based access for better collaboration.
Supports user-friendly dashboard features for managing and taking quizzes.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage Guide](#usage-guide)
- [License](#license)
- [Feedback](#feedback)
- [Contributions](#contributions)

## Features
* Sign up, log in, and log out functionalities with secure password management.
* View a dashboard of created quizzes, retake them, or create new ones.
* Upload course materials, define quiz specifications (number of questions, topics, difficulty), and the program will generate a corresponding quiz.
* After completing the quiz, receive feedback with correct answers and explanations for incorrect ones.
* Delete the quiz if it is no longer needed.
* Intuitive and clean interface built using Java Swing.
## Installation

- **Operating System**: Windows, macOS, or Linux
- **Java Version**: 17+
- **Gradle Version**: 7.5+
- **Dependencies**:
    - OkHttp
    - JSON Parsing Library
    - Cohere API Integration
    - REST API Integration
### Steps to Install
1. Clone the repository:
```git clone https://github.com/the-interverse/QuizCraft.git ```
2. Navigate to the project directory:
```cd QuizCraft```
3. Install dependencies:
```./gradlew build```
4. Run the application:
```./gradlew run```
5. Run tests:
```./gradlew test```
## Usage Guide
* Creating a Quiz
1. Log in to your account or sign up if you're new.
2. Navigate to the Create Quiz page via the Dashboard.
3. Upload a course material file (PDF or plain text).
4. Customize the quiz settings (number of questions, difficulty).
5. Save the quiz to view it on the dashboard.
* Taking a Quiz 
1. From the dashboard, click on a quiz to take it.
2. Answer all questions and submit your answers.
3. View your score immediately or access it later via the dashboard.
* Managing Your Account 
1. Log out securely via the dashboard button.
## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Feedback
We value your feedback! Please submit your suggestions through:
* [GitHub Issues](https://github.com/the-interverse/QuizCraft/issues)

## Contributions
We welcome contributions! Here’s how you can help:
1. Fork the repository: 
```git fork https://github.com/the-interverse/QuizCraft.git```
2. Make your changes in a feature branch:
```git checkout -b feature-name```
3. Commit your changes:
```git commit -m "Added feature-name"```
4. Push to your fork:
```git push origin feature-name```
5. Open a pull request.
### Guidelines for contributions
* Ensure your changes pass all existing tests. 
* Add tests for new functionality.
* Follow Clean Architecture and SOLID Principles in your design patterns.
* If your request satisfies the abovementioned prerequisities, we will evaluate your contribution and 
merge the feature into the program. 


