package ru.mail;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.TreeSet;

public class Library {
    private final Book[] books;

    private final @NotNull TreeSet<Integer> freeIndexes;

    public Library(int size,
                   @NotNull List<Book> books) {
        this.books = new Book[size];

        if(size < books.size()) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < books.size(); i++) {
            this.books[i] = books.get(i);
        }

        freeIndexes = new TreeSet<>();

        for(int i = books.size(); i < size; i++) {
            freeIndexes.add(i);
        }
    }

    public int getSize() {
        return books.length;
    }

    public void printBooks() {
        for(int i = 0; i < books.length; i++) {
            try {
                printBook(i);
            }
            catch(IllegalArgumentException e) {
                System.out.print(i + "::");
            }
            System.out.println();
        }
    }

    public @NotNull Book getBook(int index) {
        Book book = books[index];
        if(book == null) {
            throw new IllegalArgumentException("There isn't book with such index");
        }
        return book;
    }

    public void printBook(int index) {
        final var book = getBook(index);

        books[index] = null;
        freeIndexes.add(index);

        System.out.print(index + "::" + book);
    }

    public void putBook(@NotNull Book book) {
        int index = getFirstFreeIndex();
        books[index] = book;
    }

    private int getFirstFreeIndex() {
        if(freeIndexes.isEmpty()) {
            throw new IllegalArgumentException("There is not free place for book");
        }
        int index = freeIndexes.first();
        freeIndexes.remove(index);
        return index;
    }
}
