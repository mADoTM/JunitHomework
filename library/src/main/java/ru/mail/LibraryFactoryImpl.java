package ru.mail;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class LibraryFactoryImpl implements LibraryFactory {
    @NotNull
    private final BooksFactory booksFactory;

    @Inject
    public LibraryFactoryImpl(@NotNull BooksFactory booksFactory) {
        this.booksFactory = booksFactory;
    }

    @Override
    public @NotNull Library create(int size) {
        return new Library(size, booksFactory.books());
    }
}
