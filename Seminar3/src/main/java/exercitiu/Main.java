package exercitiu;

public class Main {

    public static void main(String[] args) {

        Book cleanCode = new Book("Clean Code", "Robert C. Martin");
        Book effectiveJava = new Book("Effective Java", "Joshua Bloch");

        Member andrei = new Member("Andrei");

        // Andrei takes Clean Code if it's available
        if (cleanCode.isAvailable()) {
            cleanCode.borrow();
            andrei.borrowBook(cleanCode);
            System.out.println(andrei.getName() + " borrowed " + cleanCode.getTitle());
        }

        // Just to see what happens if we try again (should fail)
        try {
            cleanCode.borrow();
        } catch (IllegalStateException e) {
            System.out.println("Couldn't borrow again: " + e.getMessage());
        }

        // Return the book
        cleanCode.returnBook();
        andrei.returnBook(cleanCode);
        System.out.println(andrei.getName() + " returned " + cleanCode.getTitle());

        // Take another book
        if (effectiveJava.isAvailable()) {
            effectiveJava.borrow();
            andrei.borrowBook(effectiveJava);
            System.out.println(andrei.getName() + " borrowed " + effectiveJava.getTitle());
        }

        // Quick check of what Andrei currently has
        System.out.println("\nRight now " + andrei.getName() + " has these books:");

        for (Book book : andrei.getBorrowedBooks()) {
            System.out.println(" - " + book.getTitle());
        }
    }
}