import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an individual Note entity in the Notes Manager application.
 */
public class Note {
    private String id;
    private String timestamp;
    private String title;
    private String content;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Note(String title, String content) {
        this.timestamp = LocalDateTime.now().format(FORMATTER);
        this.title = title;
        this.content = content;
    }

    public Note(String timestamp, String title, String content) {
        this.timestamp = timestamp;
        this.title = title;
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    /**
     * Formats the note into a formatted string ready for file persistence.
     * Format: [Timestamp] Title | Content
     */
    public String toFileFormat() {
        return String.format("[%s] %s | %s", timestamp, title, content);
    }

    @Override
    public String toString() {
        return String.format("----------------------------------------\n" +
                             "Timestamp: %s\n" +
                             "Title    : %s\n" +
                             "Content  : %s\n" +
                             "----------------------------------------", timestamp, title, content);
    }
}
