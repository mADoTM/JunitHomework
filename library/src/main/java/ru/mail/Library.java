package ru.mail;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Library {
    private final Book[] books;

    private final int size;

    public Library(int size,
                   @NotNull List<Book> books) {
        this.size = size;
        this.books = new Book[size];

        if(size < books.size()) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < books.size(); i++) {
            this.books[i] = books.get(i);
        }
    }

    public int getSize() {
        return size;
    }

    public void printBooks() {
        for(int i = 0; i < books.length; i++) {
            try {
                getBook(i);
            }
            catch(IllegalArgumentException e) {
                System.out.print(i + "::");
            }
            System.out.println();
        }
    }

    public void getBook(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Book book = books[index];
        if(book == null) {
            throw new IllegalArgumentException("There isn't book with such index");
        }
        books[index] = null;

        System.out.print(index + "::" + book);
    }

    public void putBook(@NotNull Book book) {
        int index = getFirstFreeIndex();
        books[index] = book;
    }

    private int getFirstFreeIndex() {
        for(int i = 0; i < books.length; i++) {
            if(books[i] == null) {
                return i;
            }
        }
        throw new IllegalArgumentException("There is not free place for book");
    }
}
