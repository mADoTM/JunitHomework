package ru.mail;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public final class Book {
    private final @NotNull String name;
    private final @NotNull Author author;

}
