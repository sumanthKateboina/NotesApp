import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for the Notes Manager Application.
 * Provides interactive options for creating, reading, searching, and managing notes.
 */
public class Main {
    private static final String DEFAULT_FILE_PATH = "notes.txt";

    public static void main(String[] args) {
        NotesManager manager = new NotesManager(DEFAULT_FILE_PATH);
        Scanner scanner = new Scanner(System.in);

        // Check if command line arguments provided for direct execution/testing
        if (args.length > 0 && args[0].equalsIgnoreCase("--auto-test")) {
            runAutomatedTest(manager);
            return;
        }

        System.out.println("==================================================");
        System.out.println("         WELCOME TO JAVA NOTES MANAGER            ");
        System.out.println("==================================================");

        boolean exit = false;
        while (!exit) {
            printMenu();
            System.out.print("Enter your choice (1-7): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addNoteInteractive(manager, scanner, true); // Append mode
                    break;
                case "2":
                    viewAllNotes(manager); // BufferedReader
                    break;
                case "3":
                    viewRawNotes(manager); // FileReader
                    break;
                case "4":
                    searchNotesInteractive(manager, scanner);
                    break;
                case "5":
                    addNoteInteractive(manager, scanner, false); // Overwrite mode
                    break;
                case "6":
                    clearNotesInteractive(manager, scanner);
                    break;
                case "7":
                    exit = true;
                    System.out.println("\nThank you for using Java Notes Manager. Goodbye!");
                    break;
                default:
                    System.out.println("\n[INVALID] Invalid option selected. Please choose between 1 and 7.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("                   MAIN MENU                      ");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Add Note (Append Mode - FileWriter)");
        System.out.println("2. View All Notes (BufferedReader)");
        System.out.println("3. View Raw Notes (FileReader)");
        System.out.println("4. Search Notes by Keyword");
        System.out.println("5. Overwrite File with New Note (Overwrite Mode)");
        System.out.println("6. Clear All Notes");
        System.out.println("7. Exit");
        System.out.println("--------------------------------------------------");
    }

    private static void addNoteInteractive(NotesManager manager, Scanner scanner, boolean append) {
        System.out.println("\n--- " + (append ? "ADD NOTE (Append Mode)" : "OVERWRITE NOTES FILE") + " ---");
        
        if (!append) {
            System.out.print("WARNING: This will erase all existing notes! Continue? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Operation cancelled.");
                return;
            }
        }

        System.out.print("Enter Note Title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("[ERROR] Title cannot be empty.");
            return;
        }

        System.out.print("Enter Note Content: ");
        String content = scanner.nextLine().trim();
        if (content.isEmpty()) {
            System.out.println("[ERROR] Content cannot be empty.");
            return;
        }

        Note note = new Note(title, content);
        manager.saveNote(note, append);
    }

    private static void viewAllNotes(NotesManager manager) {
        System.out.println("\n--- VIEWING ALL NOTES (BufferedReader) ---");
        List<String> notes = manager.readNotesWithBufferedReader();
        if (notes.isEmpty()) {
            System.out.println("No notes stored yet.");
            return;
        }

        int count = 1;
        for (String noteStr : notes) {
            System.out.println("[" + count++ + "] " + noteStr);
        }
    }

    private static void viewRawNotes(NotesManager manager) {
        System.out.println("\n--- VIEWING RAW CONTENT (FileReader) ---");
        String rawContent = manager.readRawContentWithFileReader();
        System.out.println(rawContent);
    }

    private static void searchNotesInteractive(NotesManager manager, Scanner scanner) {
        System.out.println("\n--- SEARCH NOTES ---");
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("[ERROR] Search keyword cannot be empty.");
            return;
        }

        List<String> results = manager.searchNotes(keyword);
        if (results.isEmpty()) {
            System.out.println("No notes found matching keyword: \"" + keyword + "\"");
        } else {
            System.out.println("Found " + results.size() + " matching note(s):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

    private static void clearNotesInteractive(NotesManager manager, Scanner scanner) {
        System.out.println("\n--- CLEAR ALL NOTES ---");
        System.out.print("Are you sure you want to delete all notes? (y/n): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("y")) {
            manager.clearAllNotes();
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    /**
     * Automated test mode for verification without user interaction.
     */
    private static void runAutomatedTest(NotesManager manager) {
        System.out.println("[AUTOMATED TEST RUN] Executing File I/O operations test...");
        
        // Step 1: Clear existing notes
        manager.clearAllNotes();

        // Step 2: Write sample notes using append mode
        Note n1 = new Note("Java File I/O", "Learned how to use FileWriter and FileReader in Java.");
        Note n2 = new Note("Try-with-Resources", "AutoCloseable resources prevent memory and file handle leaks.");
        Note n3 = new Note("Interview Prep", "BufferedReader wraps FileReader for efficient line reading.");

        manager.saveNote(n1, true);
        manager.saveNote(n2, true);
        manager.saveNote(n3, true);

        // Step 3: Read notes using BufferedReader
        System.out.println("\n[TEST] Reading notes with BufferedReader:");
        List<String> notes = manager.readNotesWithBufferedReader();
        notes.forEach(System.out::println);

        // Step 4: Read raw content with FileReader
        System.out.println("\n[TEST] Reading raw content with FileReader:");
        System.out.println(manager.readRawContentWithFileReader());

        // Step 5: Search for keyword
        System.out.println("\n[TEST] Searching for 'FileReader':");
        List<String> searchResults = manager.searchNotes("FileReader");
        searchResults.forEach(res -> System.out.println("Match: " + res));

        System.out.println("\n[AUTOMATED TEST RUN] All tests completed successfully!");
    }
}
