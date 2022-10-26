package ru.mail;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.jetbrains.annotations.NotNull;

public class ApplicationModule extends AbstractModule {
    private final @NotNull String filePath;


    public ApplicationModule(@NotNull String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("file_name")).toInstance(filePath);
        bind(BooksFactory.class).to(FileBookFactory.class);
        bind(LibraryFactory.class).to(LibraryFactoryImpl.class);
    }
}
