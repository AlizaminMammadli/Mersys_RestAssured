### **Mersys Rest Assured API Testing Project**

This project was developed using Rest Assured to test various API scenarios. It includes test scenarios based on user stories and their respective acceptance criteria.

---

#### **Table of Contents**
1. About the Project
2. Technologies Used
3. Setup and Execution
4. User Stories and Test Coverage
5. Contributors

---

### **1. About the Project**

This project is designed to execute comprehensive API tests. It includes scenarios for CRUD operations, authentication, and other API functionalities based on user stories and acceptance criteria.

---

### **2. Technologies Used**

- **Java**: Programming language.
- **Rest Assured**: API testing library.
- **Maven**: Dependency management tool.
- **JUnit/TestNG**: Test frameworks.

---

### **3. Setup and Execution**

#### Using IntelliJ IDEA:
1. Open IntelliJ IDEA and select "File > New > Project from Version Control".
2. Enter the project URL:
   ```bash
   git clone <https://github.com/AlizaminMammadli/Mersys_RestAssured.git>
   ```
3. To install Maven dependencies:
   ```bash
   mvn clean install
   ```
4. To execute tests, run:
   ```bash
   mvn test
   ```

---

### **4. User Stories and Test Coverage**

## US001
As a user, I want to log into the API test environment with an admin role, so I can securely perform admin-level operations.
Acceptance Criteria:

Invalid credentials should return an HTTP status code of 400.
Valid credentials should return an HTTP status code of 200 and an access token.

## US002
As a user, I want to add a country with states via the API, so I can verify that all required details are included in the response.
Acceptance Criteria:

The body should include "hasState": true.
Response code 201.
The response should contain accurate country and state details.

## US101
As a user, I want to perform CRUD operations for states via the API, so I can manage state information effectively.
Acceptance Criteria:

List all states within 1000 ms.
ADD: New states should return a 201 status code.
EDIT: Updating states should return a 200 status code.
DELETE: Deleting states should return a 200 or 204 status code.

## US102
As a user, I want to perform CRUD operations for cities via the API, so I can manage city information effectively.
Acceptance Criteria:

List all cities within 1000 ms.
Retrieve a specific city within 200 ms.
ADD: New cities should return a 201 status code.
EDIT: Updating cities should return a 200 status code.
DELETE: Deleting cities should return a 200 or 204 status code.

## US103
As a user, I want to manage exams for courses via the API (CRUD), so I can evaluate students effectively and provide feedback.
Acceptance Criteria:

ADD: Create an exam with mandatory fields (name, academic period, grade level); missing any fields should return 400.
Successfully created exams should return 201 with the exam ID in the response.
EDIT: Update an existing exam by providing its ID and modified details; invalid IDs should return a 404 status code.
DELETE: Remove an existing exam using its ID; invalid IDs should return a 404 status code.

## US105
As a user, I want to manage student groups via the API (CRUD), so I can organize students based on their interests.
Acceptance Criteria:

ADD: Create a group with valid school ID, name, and description; successful creation should return 201.
EDIT: Update group details using its ID; successful updates should return 200.
DELETE: Delete groups using their ID; invalid IDs should return 400.

## US106
As a user, I want to add students to a group via the API, so I can assign students to their preferred groups.
Acceptance Criteria:

Provide valid group and student IDs; successful additions should return 200.
Newly added students should be retrievable via the corresponding endpoint.

## US107
As a user, I want to remove students from a group via the API, so I can allow them to leave groups when requested.
Acceptance Criteria:

Provide valid group and student IDs; successful removals should return 200.
Removed students should no longer appear in the group listing.

## US108
As a user, I want to manage educational standards via the API (CRUD), so I can keep track of curriculum requirements.
Acceptance Criteria:

LIST: Retrieve all standards for a school; responses should include correct data.
ADD: Create a standard with a valid school ID; successful additions should return 201.
EDIT: Update a standard's details; successful updates should return 200.
DELETE: Remove standards using their ID; invalid IDs should return 400.

## US109
As a user, I want to manage grading schemes via the API, so the school can switch between grading systems easily.
Acceptance Criteria:

LIST: Retrieve all grading schemes for a school.
ADD: Create a scheme with valid data; successful creation should return 201.
EDIT: Update scheme details using its ID; successful updates should return 200.
DELETE: Remove schemes using their ID; invalid IDs should return 400.

## US110
As a user, I want to manage incident types via the API (CRUD), so I can track and prevent accidents at school.
Acceptance Criteria:

LIST: Retrieve all incident types for a school.
ADD: Create a type with valid data; successful creation should return 201.
EDIT: Update type details using its ID; successful updates should return 200.
DELETE: Remove types using their ID; invalid IDs should return 400.


---

### **5. Contributors**

- **AlizaminMammadli**  
- **alikilincwk**  
- **GWilsonpixie**
- **Gunay4**
- **ariftemur**
- **Sinem777**
- **umit20222**
- **kardelenssu**


