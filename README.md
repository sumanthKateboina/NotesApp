# Task 4: Java File I/O – Notes Manager App

## 📌 Project Overview
The **Java Notes Manager** is a text-based console application designed to demonstrate data persistence in Java using **File I/O** streams (`FileWriter`, `FileReader`, and `BufferedReader`) along with robust **Exception Handling** (`try-with-resources`, `IOException` handling, and exception logging).

---

## 📁 Repository Structure
```
Task 4/
│── src/
│   ├── Note.java             # Data model representing an individual note with timestamp
│   ├── NotesManager.java     # Core File I/O logic (write, read, search, clear)
│   └── Main.java             # Command Line Interface (CLI) menu & entry point
│── bin/                      # Compiled byte-code (.class files)
│── notes.txt                 # Data storage file created dynamically at runtime
│── .gitignore                # Ignores compiled binaries and temporary files
└── README.md                 # Project documentation and Interview Q&A
```

---

## ✨ Features & Functionalities
1. **Add Notes (Append Mode)**: Writes new notes to `notes.txt` without overwriting existing data (`new FileWriter(filename, true)`).
2. **View All Notes (BufferedReader)**: Reads file efficiently line-by-line using `BufferedReader` and `readLine()`.
3. **View Raw Content (FileReader)**: Reads file character-by-character using raw `FileReader` for demonstration.
4. **Search Notes**: Allows keyword-based case-insensitive search across saved notes.
5. **Overwrite File**: Overwrites `notes.txt` with a fresh note (`new FileWriter(filename, false)`).
6. **Clear Notes**: Deletes all existing notes securely.
7. **Exception-Safe Operations**: Utilizes **try-with-resources** to ensure streams are automatically closed, preventing memory or file handle leaks.

---

## 🚀 How to Run the Application

### Prerequisites
- Java Development Kit (JDK 8 or higher, JDK 17 recommended)
- Terminal / VS Code / Any Java IDE

### Steps to Compile and Execute

1. **Open Terminal** in the project directory:
   ```bash
   cd "Task 4"
   ```

2. **Compile Java Files**:
   ```bash
   javac -d bin src/*.java
   ```

3. **Run Interactive Application**:
   ```bash
   java -cp bin Main
   ```

4. **Run Automated Test (Verification)**:
   ```bash
   java -cp bin Main --auto-test
   ```

---

## 📊 Sample Output (Automated Test Run)

```text
[INFO] Storage file created successfully: C:\Users\Asus\OneDrive\Documents\Desktop\Task 4\notes.txt
[AUTOMATED TEST RUN] Executing File I/O operations test...
[SUCCESS] All notes cleared successfully.
[SUCCESS] Note saved successfully (Appended).
[SUCCESS] Note saved successfully (Appended).
[SUCCESS] Note saved successfully (Appended).

[TEST] Reading notes with BufferedReader:
[INFO] Read 3 note(s) using BufferedReader.
[2026-10-06 14:16:31] Java File I/O | Learned how to use FileWriter and FileReader in Java.
[2026-10-06 14:16:31] Try-with-Resources | AutoCloseable resources prevent memory and file handle leaks.
[2026-10-06 14:16:31] Interview Prep | BufferedReader wraps FileReader for efficient line reading.

[TEST] Searching for 'FileReader':
Match: [2026-10-06 14:16:31] Java File I/O | Learned how to use FileWriter and FileReader in Java.
Match: [2026-10-06 14:16:31] Interview Prep | BufferedReader wraps FileReader for efficient line reading.

[AUTOMATED TEST RUN] All tests completed successfully!
```

---

## 🧠 Interview Questions & Comprehensive Answers

### 1. Difference between `FileReader` and `BufferedReader`?
- **`FileReader`**: A character stream class used to read raw character data directly from a file disk byte-by-byte or block-by-block. It performs disk I/O for every read request, making it slower for reading line-by-line.
- **`BufferedReader`**: A wrapper class that buffers character inputs in an internal memory buffer (default size 8KB). It reduces disk access frequency and provides high-level convenience methods such as `readLine()`.
- **Comparison Table**:
  | Feature | `FileReader` | `BufferedReader` |
  | :--- | :--- | :--- |
  | **Buffering** | Unbuffered | Buffered (in memory) |
  | **Performance** | Slower (direct disk access) | Faster (reads chunks into buffer) |
  | **Methods** | Reads single `char` or `char[]` | Supports `readLine()` for entire lines |

---

### 2. What is `try-with-resources`?
- Introduced in Java 7, **try-with-resources** is a statement that automatically closes resources (such as streams, files, or database connections) when the block exits.
- Any class implementing `java.lang.AutoCloseable` or `java.io.Closeable` can be declared in a try-with-resources header.
- **Syntax Example**:
  ```java
  try (BufferedReader br = new BufferedReader(new FileReader("notes.txt"))) {
      String line = br.readLine();
  } catch (IOException e) {
      e.printStackTrace();
  }
  // BufferedReader and FileReader are automatically closed here without requiring a explicitly written finally block!
  ```

---

### 3. How to handle `IOException`?
- `IOException` is a checked exception thrown when an input/output operation fails (e.g., file not found, permission denied, disk full).
- It can be handled in two ways:
  1. **Catching with `try-catch`**: Catch `IOException` locally, log the message/stack trace, and provide a fallback or user alert.
     ```java
     try {
         FileWriter writer = new FileWriter("file.txt");
         writer.write("Hello");
     } catch (IOException e) {
         System.err.println("Failed to write to file: " + e.getMessage());
     }
     ```
  2. **Declaring with `throws`**: Propagate `IOException` up the call stack to the caller method using `throws IOException`.

---

### 4. What are checked and unchecked exceptions?
- **Checked Exceptions**:
  - Exceptions checked at compile time by the Java compiler.
  - The programmer **must** handle them using `try-catch` or declare them using `throws`.
  - Examples: `IOException`, `FileNotFoundException`, `SQLException`.
- **Unchecked Exceptions**:
  - Exceptions that occur at runtime (subclasses of `RuntimeException`).
  - Compiler does not force explicit handling. Usually indicate logic bugs or illegal arguments.
  - Examples: `NullPointerException`, `ArrayIndexOutOfBoundsException`, `IllegalArgumentException`, `ArithmeticException`.

---

### 5. How does file writing work in Java?
- File writing converts character data into byte streams and writes them to a file target on disk using native OS file system APIs.
- Flow of characters during buffered file writing:
  `Character Data` ➔ `FileWriter` (Character Stream) ➔ `FileOutputStream` (Byte Stream) ➔ `Disk Storage`.
- When writing using streams/writers, data is often stored in memory buffers first. Calling `flush()` or `close()` ensures buffered bytes are physically flushed out to the disk file.

---

### 6. What is the difference between append and overwrite mode?
- **Overwrite Mode** (`new FileWriter("file.txt", false)` or `new FileWriter("file.txt")`):
  - Clears/truncates the existing file content to 0 bytes before writing new data.
- **Append Mode** (`new FileWriter("file.txt", true)`):
  - Preserves existing file content and positions the write cursor at the end of the file, appending new data.

---

### 7. What is exception propagation?
- Exception propagation is the process by which an unhandled exception travels up the call stack from the method where it occurred to caller methods until it is caught by a `try-catch` block.
- If an exception reaches the top of the call stack (`main` method) without being caught, the thread terminates and prints the stack trace to `System.err`.

---

### 8. How to log exceptions?
- Logging exceptions involves recording error details (timestamps, error severity, stack traces) using logging frameworks (e.g., `java.util.logging.Logger`, `SLF4J`, `Log4j2`).
- **Example using `java.util.logging.Logger`**:
  ```java
  private static final Logger LOGGER = Logger.getLogger(NotesManager.class.getName());

  try {
      // File I/O
  } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "An I/O error occurred while writing notes", e);
  }
  ```

---

### 9. What is a stack trace?
- A **stack trace** is a report showing the execution call stack of threads at the exact instant an exception occurred.
- It details:
  1. The exception type and error message.
  2. The exact chain of method calls leading to the failure.
  3. The exact file name and line number where the exception originated.
- Printed via `e.printStackTrace()` or logged via loggers.

---

### 10. When to use `finally` block?
- A `finally` block is executed **always**, whether an exception occurs or not (even if a `return` statement is encountered inside `try` or `catch`).
- **Use Cases**:
  - Closing traditional resources (files, database connections, sockets) when not using `try-with-resources`.
  - Performing cleanup operations (releasing locks, resetting states).
- Note: `finally` will not execute only in extreme circumstances like `System.exit(0)` or JVM crash/power failure.
