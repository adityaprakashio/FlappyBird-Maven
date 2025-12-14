# 🐦 Java Flappy Bird (DevOps Edition)

A classic Flappy Bird clone built using **Java Swing** and managed with **Apache Maven**. 
This project demonstrates the implementation of a standard DevOps build lifecycle, version control, and automated dependency management.

## 📋 Project Overview
* **Language:** Java (JDK 17)
* **Build Tool:** Apache Maven
* **Testing:** JUnit 4
* **Version Control:** Git & GitHub

## 🚀 How to Run the Project
You can build and run this project using a single command line.

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* Apache Maven 3.x

### Build & Run Commands
Open your terminal in the project folder and run:

1.  **Clean and Compile:**
    ```bash
    mvn clean compile
    ```

2.  **Run Unit Tests:**
    ```bash
    mvn test
    ```
    *(This verifies the game logic and board dimensions)*

3.  **Start the Game:**
    ```bash
    mvn exec:java
    ```

## 🎮 Controls
* **Spacebar:** Jump / Start Game / Restart

## 📂 Directory Structure
This project follows the **Maven Standard Directory Layout**:

* `src/main/java`: Core game logic (`App.java`, `FlappyBird.java`)
* `src/main/resources`: Game assets (Images)
* `src/test/java`: Automated JUnit tests (`AppTest.java`)
* `pom.xml`: Project Object Model (Dependency & Build Configuration)