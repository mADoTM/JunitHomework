package ru.mail;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface BooksFactory {
    @NotNull List<Book> books();
}
