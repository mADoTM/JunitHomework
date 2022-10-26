package ru.mail;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class FileBookFactory implements BooksFactory {
    @NotNull
    private static final Type listBooksType = new TypeToken<ArrayList<Book>>() {
    }.getType();

    @NotNull
    private final String fileName;

    @Inject
    public FileBookFactory(@NotNull @Named("file_name") String fileName) {
        this.fileName = fileName;
    }

    @NotNull
    @Override
    public List<Book> books() {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listBooksType);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}