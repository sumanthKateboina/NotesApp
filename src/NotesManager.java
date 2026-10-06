import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles all File I/O operations for Notes Manager using Java FileReader, FileWriter, 
 * BufferedReader, and proper exception handling techniques (try-with-resources, IOException logging).
 */
public class NotesManager {
    private static final Logger LOGGER = Logger.getLogger(NotesManager.class.getName());
    private final String filePath;

    public NotesManager(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    /**
     * Ensures that the storage file exists on the disk.
     */
    private void ensureFileExists() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("[INFO] Storage file created successfully: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating storage file: " + e.getMessage(), e);
        }
    }

    /**
     * Writes a note to the file using FileWriter.
     * Demonstrates the difference between append mode and overwrite mode.
     *
     * @param note   The Note object to save.
     * @param append If true, appends content to the end of file; if false, overwrites the file.
     * @return true if write operation succeeded, false otherwise.
     */
    public boolean saveNote(Note note, boolean append) {
        // Try-with-resources block ensures automatic closing of FileWriter stream even if an IOException occurs
        try (FileWriter writer = new FileWriter(filePath, append)) {
            writer.write(note.toFileFormat() + System.lineSeparator());
            System.out.println("[SUCCESS] Note saved successfully (" + (append ? "Appended" : "Overwritten") + ").");
            return true;
        } catch (IOException e) {
            // Exception Logging & Stack Trace demonstration
            LOGGER.log(Level.SEVERE, "IOException occurred while writing to file: " + filePath, e);
            System.err.println("[ERROR] Could not write note to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reads all notes line-by-line using BufferedReader wrapping a FileReader.
     * BufferedReader provides efficient reading by buffering characters.
     *
     * @return List of formatted note strings read from file.
     */
    public List<String> readNotesWithBufferedReader() {
        List<String> notesList = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            System.out.println("[INFO] No notes found. The file is empty.");
            return notesList;
        }

        // Try-with-resources automatically closes both BufferedReader and FileReader streams
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    notesList.add(line);
                }
            }
            System.out.println("[INFO] Read " + notesList.size() + " note(s) using BufferedReader.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException occurred while reading file with BufferedReader: " + filePath, e);
            System.err.println("[ERROR] Failed to read notes: " + e.getMessage());
        }

        return notesList;
    }

    /**
     * Demonstrates reading file content using character-by-character raw FileReader.
     * This demonstrates how FileReader reads raw characters without line-buffering.
     */
    public String readRawContentWithFileReader() {
        StringBuilder contentBuilder = new StringBuilder();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            return "[INFO] File is empty or does not exist.";
        }

        // Using raw FileReader directly (reading character by character into a char buffer)
        try (FileReader reader = new FileReader(filePath)) {
            char[] buffer = new char[1024];
            int numCharsRead;
            while ((numCharsRead = reader.read(buffer)) != -1) {
                contentBuilder.append(buffer, 0, numCharsRead);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException in readRawContentWithFileReader: " + filePath, e);
            System.err.println("[ERROR] Raw FileReader operation failed: " + e.getMessage());
        }

        return contentBuilder.toString();
    }

    /**
     * Searches for notes containing a specified search keyword.
     *
     * @param keyword The term to search for (case-insensitive).
     * @return List of matching lines.
     */
    public List<String> searchNotes(String keyword) {
        List<String> searchResults = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return searchResults;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    searchResults.add(line);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException during note search operation: " + filePath, e);
            System.err.println("[ERROR] Error searching notes: " + e.getMessage());
        }

        return searchResults;
    }

    /**
     * Clears all content from the file by using FileWriter in overwrite mode with empty content.
     */
    public boolean clearAllNotes() {
        // Opening FileWriter with append=false truncates the file content
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write(""); // Empty overwrite
            System.out.println("[SUCCESS] All notes cleared successfully.");
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException while clearing notes file: " + filePath, e);
            System.err.println("[ERROR] Failed to clear notes file: " + e.getMessage());
            return false;
        }
    }

    public String getFilePath() {
        return filePath;
    }
}
