package ru.mail;

import org.jetbrains.annotations.NotNull;

public record Book(@NotNull String name, @NotNull Author author) {
}
