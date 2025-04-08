# NetApp

**NetApp** is a simple Java desktop application that simulates a mini professional social network. It allows users to log in, view and edit their personal details, connect with other users, and browse educational and work experience information.

This project was developed as part of the **Databases** at the **Technical University of Crete**.

---


## Project Structure

- `LoginScreen.java`: Handles user authentication via PostgreSQL database.
- `MainScreen.java`: Displays the main dashboard with profile editing and network list.
- `MemberDetailScreen.java`: Shows detailed information for a selected member including messages, education, and experience.
- `Member.java`: Basic data class to represent a user.
- `postgresql-42.7.3.jar`: JDBC driver required for PostgreSQL connection.

---

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/netapp.git
cd netapp
```

### 2. Set Up the Database

- Create a database named: `MyDataBase_PLH303`
- Import your table schemas (`member`, `msg`, `education`, `experience`, `connects`)
- Ensure the correct credentials in `LoginScreen.java`:
```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/MyDataBase_PLH303";
private static final String DB_USER = "postgres";
private static final String DB_PASSWORD = "your_password";
```

### 3. Add PostgreSQL Driver

Make sure `postgresql-42.7.3.jar` is added to your classpath.

### 4. Compile and Run

```bash
javac -cp .:postgresql-42.7.3.jar netapp/*.java
java -cp .:postgresql-42.7.3.jar netapp.LoginScreen
```
---


## Acknowledgments

- **Professor A. Deligiannakis**, for his teaching.

---

