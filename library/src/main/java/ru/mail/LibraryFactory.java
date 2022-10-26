package ru.mail;

import org.jetbrains.annotations.NotNull;

public interface LibraryFactory {
    @NotNull Library create(int size);
}
