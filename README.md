// Author: Adithya Mohanan
# Employee Performance Appraisal System

## Overview

The **Employee Performance Appraisal System** calculates and visualizes employee ratings, compares them against predefined standards, and suggests revisions based on performance deviations. The system provides a bell curve representation of the performance appraisal, along with detailed insights on individual employee ratings.

### Features:
- **Employee Details**: Stores employee ID, name, and rating.
- **Rating Categories**: Provides a standard performance distribution for categories (Outstanding, Very Good, Good, Need to Improve, Low Performers).
- **Deviation Calculation**: Calculates the deviation between actual performance and standard performance percentages.
- **Performance Insights**: Suggests employees who need a revision based on the calculated deviation.
- **Bell Curve**: Visualizes performance distribution using a line chart.

## Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: React (for visualizing the performance graphs and employee data)
- **Database**: H2 (for development purposes, can be replaced with any RDBMS like MySQL, PostgreSQL)
- **Charting Library**: Chart.js (for rendering Pie and Line charts)
- **Unit Testing**: JUnit, Mockito
- **Caching**: Redis (optional, for caching performance data)
- **Messaging**: (Optional) RabbitMQ or Kafka for async operations

## Setup Instructions

### Backend Setup (Spring Boot)

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/adithyamohanan/employee-performance-appraisal-system.git
   cd employee-performance-appraisal-backend
   ```

2. **Install Dependencies**:
   - Make sure **Java 17** (or above) is installed.
   - Install **Maven** for managing dependencies.
   - Run the following command to install the required dependencies:
     ```bash
     mvn clean install
     ```

3. **Database Setup**:
   - For development, an **H2 database** is used by default. If using a different DB (e.g., MySQL), modify the `application.properties` file in the `src/main/resources` folder.
   
4. **Run the Spring Boot Application**:
   ```bash
   mvn spring-boot:run
   ```

   The backend will be available at `http://localhost:8080`.

### Frontend Setup (React)

1. **Install Node.js**: Ensure that **Node.js** (version 16 or above) and **npm** are installed.

2. **Clone the Repository** (if separate repository):
   ```bash
   git clone https://github.com/adithyamohanan/employee-performance-appraisal-system.git
   cd employee-performance-frontend
   ```

3. **Install Dependencies**:
   ```bash
   npm install
   ```

4. **Run the React Application**:
   ```bash
   npm start
   ```

   The frontend will be available at `http://localhost:3000`.

### How to Run the Full Application

- The backend (Spring Boot) must be running on port `8080`, and the frontend (React) should be running on port `3000`.
- The frontend will communicate with the backend using HTTP requests to display performance data and generate the bell curve chart.

### Database Schema

- **Employee Table**:
  ```sql
  CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(50),
    employee_name VARCHAR(255),
    rating VARCHAR(1)
  );
  ```

- **Rating Categories Table**:
  ```sql
  CREATE TABLE rating_categories (
    category VARCHAR(50),
    standard_percentage INT
  );
  ```

### API Endpoints

- **GET** `/api/performance-appraisal/calculate`:
  - Fetches the performance data for the employees, compares it with the standard percentages, and calculates the deviation.
  - **Response**: A JSON object containing the `actualPercentage` and other relevant details.
  
  Example:
  ```json
  {
    "A": { "actualPercentage": 15 },
    "B": { "actualPercentage": 25 },
    "C": { "actualPercentage": 35 },
    "D": { "actualPercentage": 15 },
    "E": { "actualPercentage": 10 },
    "Employees to Revise": [
      { "Employee ID": "5004", "Name": "Harry", "Current Rating": "D" }
    ]
  }
  ```

### Unit Tests

Unit tests are implemented for the core logic, especially for the **deviation calculation** and **revision suggestion** based on the rating categories.

- To run the tests:
  ```bash
  mvn test
  ```

## Design & Implementation

### 1. **Performance Appraisal Calculation**:
   - Each employee's performance category (A, B, C, D, E) is mapped to a percentage.
   - The system compares the employee’s actual performance percentage with the standard category percentages.

### 2. **Deviation Calculation**:
   - The difference between the actual percentage and the standard percentage is calculated for each employee’s performance category.
   
### 3. **Revision Suggestions**:
   - Employees whose performance deviates significantly (either above or below) from the standard percentages are flagged for a revision of their rating.

### 4. **Bell Curve**:
   - A **Line Chart** is generated comparing the standard and actual performance percentages using **Chart.js**.
   
### 5. **Employee Revision Table**:
   - A table is displayed to show the list of employees who need their rating revised.

## How the Bell Curve is Created

- **Standard Distribution**: This is a predefined distribution of performance categories (e.g., 10% for A, 20% for B, etc.).
- **Actual Distribution**: This is calculated dynamically based on the employee ratings.
- A line graph (bell curve) is plotted to compare the actual performance with the standard.


## Extensibility

The system is designed to be easily extensible:
- New categories can be added without changing the core logic.
- Additional features like automated email notifications for revision suggestions or a more advanced analytics dashboard can be integrated.



## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
