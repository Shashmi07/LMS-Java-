import java.util.*;
import java.time.*;

class Book {
    private String title;
    private String author;
    private String bID;
    private String status;
    private LocalDateTime borrowedDate;
    private LocalDateTime returnedDate;


    public Book(String title, String author, String bID) {
        this.title = title;
        this.author = author;
        this.bID = bID;
        this.status = "available";
        this.borrowedDate = null;
        this.returnedDate = null;
    }

    public String getTitle() {
        return title;
    }
    
    
    public String getAuthor() {
        return author;
    }

   
    public String getbID() {
        return bID;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void borrowBook() {
        if ("available".equals(status)) {
            status = "borrowed";
            borrowedDate = LocalDateTime.now();
            System.out.println("Book borrowed successfully");
        } else {
            System.out.println("Sorry, this book is not available");
        }
    }

    public void returnBook() {
        if ("borrowed".equals(status)) {
            status = "available";
            returnedDate = LocalDateTime.now();
            System.out.println("The book '" + title + "' has been returned");
        } else {
            System.out.println("The book '" + title + "' is not currently borrowed");
        }
    }

}
    
   

class Library {
    private List<Book> books;
    private Map<String, String> borrowedBooks;

    public Library() {
        books = new ArrayList<>();
        borrowedBooks = new HashMap<>();
    }

    public void viewBooks() {
        System.out.println("------- All Books  -------");
        for (Book book : books) {
            System.out.printf("Title: %s, Author: %s, bID: %s, Status: %s, Borrowed Time: %s, Returned Time: %s\n",
                    book.getTitle(), book.getAuthor(), book.getbID(), book.getStatus(),
                    book.getBorrowedDate(), book.getReturnedDate());
        }
    }

    public void availableBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if ("available".equals(book.getStatus())) {
                System.out.printf("Title: %s, Author: %s, bID: %s\n", book.getTitle(), book.getAuthor(), book.getbID());
            }
        }
    }

    public void borrowBook(String bID, String user) {
        for (Book book : books) {
            if (book.getbID().equals(bID)) {
                if ("available".equals(book.getStatus())) {
                    book.borrowBook();
                    borrowedBooks.put(bID, user);
                } else {
                    System.out.printf("Sorry, the book '%s' is not available\n", book.getTitle());
                }
                return;
            }
        }
        System.out.printf("Book with ISBN %s not found\n", bID);
    }

    public void addBook(String title, String author, String bID) {
        books.add(new Book(title, author, bID));
        System.out.printf("The book '%s' has been added to the library.\n", title);
    }

    public void returnBook(String bID) {
        for (Book book : books) {
            if (book.getbID().equals(bID)) {
                if ("borrowed".equals(book.getStatus())) {
                    book.returnBook();
                    String user = borrowedBooks.remove(bID);
                    if (user != null) {
                        System.out.printf("The book '%s' has been returned by %s\n", book.getTitle(), user);
                    }
                } else {
                    System.out.printf("The book '%s' is not currently borrowed\n", book.getTitle());
                }
                return;
            }
        }
        System.out.printf("Book with ISBN %s not found\n", bID);
    }

    public void viewBorrowedBooks() {
        System.out.println("Borrowed Books:");
        for (Map.Entry<String, String> entry : borrowedBooks.entrySet()) {
            String bID = entry.getKey();
            String user = entry.getValue();
            for (Book book : books) {
                if (book.getbID().equals(bID)) {
                    System.out.printf("Title: %s, Borrowed by: %s, Borrowed on: %s\n",
                            book.getTitle(), user, book.getBorrowedDate());
                }
            }
        }
    }
}



public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        System.out.println("-------Welcome to Library Management System-----------");

        while (true) {
            System.out.println("1. View all books");
            System.out.println("2. Available books");
            System.out.println("3. Borrow a book");
            System.out.println("4. Add a book");
            System.out.println("5. Return a book");
            System.out.println("6. View borrowed books");
            System.out.print("Enter your choice: ");

            int userchoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (userchoice) {
                case 1:
                    library.viewBooks();
                    break;
                case 2:
                    library.availableBooks();
                    break;
                case 3:
                    System.out.print("Enter the book ISBN to borrow: ");
                    String borrowbID= scanner.nextLine();
                    System.out.print("Enter your name: ");
                    String userName = scanner.nextLine();
                    library.borrowBook(borrowbID, userName);
                    break;
                case 4:
                    System.out.print("Enter the book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter the book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter the book ISBN: ");
                    String addbID = scanner.nextLine();
                    library.addBook(title, author, addbID);
                    break;
                case 5:
                    System.out.print("Enter the book ISBN to return: ");
                    String returnbID = scanner.nextLine();
                    library.returnBook(returnbID);
                    break;
                case 6:
                    library.viewBorrowedBooks();
                    break;   
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
        }
    

        System.out.print("Press E to exit and C to continue: ");
            String choice = scanner.nextLine().toUpperCase();
            if ("E".equals(choice)) {
                break;
            } else if (!"C".equals(choice)) {
                System.out.println("Not a valid option.");
                break;
            }
            System.out.println("==".repeat(20));
        }
        scanner.close();
    }
}
