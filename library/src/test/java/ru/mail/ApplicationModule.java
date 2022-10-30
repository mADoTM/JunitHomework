package ru.mail;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.mockito.Mockito;

import java.util.List;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LibraryFactory.class).to(LibraryFactoryImpl.class);
    }

    @Provides
    static BooksFactory provideBooksFactory() {
        BooksFactory fileBookFactory = Mockito.mock(FileBookFactory.class);

        final var preparedBooks = List.of(new Book("book1", new Author("author1")),
                new Book("book2", new Author("author2")),
                new Book("book3", new Author("author3")),
                new Book("book4", new Author("author4")));

        Mockito.when(fileBookFactory.books()).thenReturn(preparedBooks);

        return fileBookFactory;
    }
}
