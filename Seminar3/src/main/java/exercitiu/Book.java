package exercitiu;

public class Book {

    private String title;
    private String author;
    private boolean available = true;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrow() {
        if (!available) {
            throw new IllegalStateException("This book is already taken");
        }
        available = false;
    }

    public void returnBook() {
        available = true;
    }
}