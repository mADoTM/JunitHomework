package ru.mail;

import com.google.inject.Guice;

public class Main {
    public static void main(String[] args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }

        final String fileName = args[0];
        final int librarySize = Integer.parseInt(args[1]);

        final var injector = Guice.createInjector(new ApplicationModule(fileName));
        final var library = injector.getInstance(LibraryFactory.class).create(librarySize);
        library.printBooks();
    }
}