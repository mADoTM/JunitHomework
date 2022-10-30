package ru.mail;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public final class Author {
    private final @NotNull String name;
}
