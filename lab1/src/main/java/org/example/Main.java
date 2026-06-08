package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String DATA_FILE = "books.json";

    private List<Book> books;

    public static void main(String[] args) {
        logger.info("Application started");
        Main app = new Main();
        app.run();
    }

    public Main() {
            books = new ArrayList<>(JsonUtils.loadBooksFromFile(DATA_FILE));
        logger.info("Loaded {} books from file", books.size());
    }

    public void run() {
        logger.debug("Entering run() method");
        System.out.println("=== Book Manager ===");

               Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);

            while (true) {
                printMenu();
                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    logger.debug("User selected option: {}", choice);

                    switch (choice) {
                        case 1 -> addBook(scanner);
                        case 2 -> listBooks();
                        case 3 -> saveToFile();
                        case 4 -> {
                            logger.info("Program terminated by user");
                            System.out.println("Program terminated");
                            return;
                        }
                        default -> {
                            logger.warn("User entered invalid choice: {}", choice);
                            System.out.println("Invalid choice!");
                        }
                    }
                } catch (NumberFormatException e) {
                    logger.error("Invalid input format", e);
                    System.out.println("Please enter a valid number!");
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
                logger.debug("Scanner closed");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n1. Add a book");
        System.out.println("2. Show all books");
        System.out.println("3. Save to file");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private void addBook(Scanner scanner) {
        logger.info("Adding a new book");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter publication year: ");
        try {
            int year = Integer.parseInt(scanner.nextLine());

            Book book = new Book(title, author, year);
            books.add(book);

            logger.info("Book added: {}", book);
            System.out.println("Book added!");
        } catch (NumberFormatException e) {
            logger.error("Invalid year format", e);
            System.out.println("Invalid year format!");
        }
    }

    private void listBooks() {
        logger.debug("Requesting book list");

        if (books.isEmpty()) {
            logger.info("Book list is empty");
            System.out.println("Book list is empty");
            return;
        }

        System.out.println("\nBook list:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }

        logger.debug("Displayed {} books", books.size());
    }

    private void saveToFile() {
        JsonUtils.saveBooksToFile(books, DATA_FILE);
        System.out.println("Books saved to file " + DATA_FILE);
    }
}