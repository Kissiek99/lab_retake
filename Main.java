import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static BookManager bookManager;

    public static void main(String[] args) {
        List<Book> initialBooks = new ArrayList<>();
        initialBooks.add(new Book("1984", "George Orwell", "123456789", 1949));
        initialBooks.add(new Book("To Kill a Mockingbird", "Harper Lee", "987654321", 1960));
        initialBooks.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "111111111", 1925));
        initialBooks.add(new Book("Moby Dick", "Herman Melville", "222222222", 1851));
        initialBooks.add(new Book("War and Peace", "Leo Tolstoy", "333333333", 1869));

        bookManager = new BookManager(initialBooks);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("[1] Add book");
            System.out.println("[2] Remove book");
            System.out.println("[3] Update book");
            System.out.println("[4] List books");
            System.out.println("[5] Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    removeBook(scanner);
                    break;
                case 3:
                    updateBook(scanner);
                    break;
                case 4:
                    listBooks();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addBook(Scanner scanner) {
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter author:");
        String author = scanner.nextLine();
        System.out.println("Enter ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Enter year:");
        int year = scanner.nextInt();
        scanner.nextLine();

        Book book = new Book(title, author, isbn, year);
        bookManager.addBook(book);
        System.out.println("Book added.");
    }

    private static void removeBook(Scanner scanner) {
        System.out.println("Enter ISBN of the book to remove:");
        String isbn = scanner.nextLine();

        Book bookToRemove = null;
        for (Book book : bookManager.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                bookToRemove = book;
                break;
            }
        }

        if (bookToRemove != null) {
            bookManager.removeBook(bookToRemove);
            System.out.println("Book removed.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void updateBook(Scanner scanner) {
        System.out.println("Enter ISBN of the book to update:");
        String isbn = scanner.nextLine();

        Book oldBook = null;
        for (Book book : bookManager.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                oldBook = book;
                break;
            }
        }

        if (oldBook != null) {
            System.out.println("Enter new title:");
            String title = scanner.nextLine();
            System.out.println("Enter new author:");
            String author = scanner.nextLine();
            System.out.println("Enter new year:");
            int year = scanner.nextInt();
            scanner.nextLine();

            Book newBook = new Book(title, author, isbn, year);
            bookManager.updateBook(oldBook, newBook);
            System.out.println("Book updated.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void listBooks() {
        for (Book book : bookManager.getBooks()) {
            System.out.println(book);
        }
    }
}
