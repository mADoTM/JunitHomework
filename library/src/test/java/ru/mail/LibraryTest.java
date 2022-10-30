package ru.mail;

import com.google.inject.Inject;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(GuiceExtension.class)
@IncludeModule(ApplicationModule.class)
class LibraryTest {
    @Inject
    BooksFactory booksFactory;

    @Inject
    LibraryFactory libraryFactory;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private @NotNull Library library;

    private @NotNull List<Book> books;

    @Test
    public void factoryThrowsExceptionIfLibraryHasLessSlotsThatBooks() {
        assertThrows(IllegalArgumentException.class, () -> libraryFactory.create(books.size() - 1));
    }

    @Test
    public void libraryHasSameOrderedBooksLikeFactoryBooksOthersCellShouldBeEmpty() {
        library = libraryFactory.create(books.size() + 1);

        for(int i = 0; i < books.size(); i++) {
            assertEquals(books.get(i), library.getBook(i));
        }

        for(int i = books.size(); i < library.getSize(); i++) {
            int index = i;
            assertThrows(IllegalArgumentException.class, () -> library.getBook(index));
        }
    }

    @Test
    public void whenBookTakingInfoAboutThatBookPrinting() {
        int index = 1;
        final var libraryBook = library.getBook(index);
        library.printBook(index);
        String cellNumber = outContent.toString().split("::")[0];
        String bookString = outContent.toString().split("::")[1];

        assertEquals(index, Integer.parseInt(cellNumber));
        assertEquals(libraryBook.toString(), bookString);
    }

    @Test
    public void whenBookTakingFromEmptyCellThrowException() {
        library = libraryFactory.create(books.size() + 1);
        assertThrows(IllegalArgumentException.class, () -> library.printBook(books.size()));
    }

    @Test
    public void whenBookTakenGivenThatBook() {
        int index = 1;
        final var libraryBook = library.getBook(index);

        library.printBook(index);
        String actualLibraryBookString = outContent.toString().split("::")[1];

        assertEquals(libraryBook.toString(), actualLibraryBookString);
    }

    @Test
    public void bookShouldBePuttedOnFirstEmptyCell() {
        final var book1 = new Book("test book0", new Author("test author 0"));
        final var book3 = new Book("test book1", new Author("test author 1"));

        library.printBook(1);
        library.printBook(3);

        library.putBook(book3);
        library.putBook(book1);

        outContent.reset();
        library.printBook(1);
        String bookString = outContent.toString().split("::")[1];
        assertEquals(book3.toString(), bookString);

        outContent.reset();
        library.printBook(3);
        bookString = outContent.toString().split("::")[1];
        assertEquals(book1.toString(), bookString);
    }

    @Test
    public void libraryThrowExceptionIfThereAreNoFreeSpace() {
        final var testBook = new Book("test book", new Author("test author"));

        assertThrows(IllegalArgumentException.class, () -> library.putBook(testBook));
    }

    @Test
    public void printBooksMethodPrintingInConsole() {
        library.printBooks();
        final var booksInString = outContent.toString().split("\n");
        for(int i = 0; i < library.getSize(); i++) {
            try {
                final var libraryBook = library.getBook(i);
                assertEquals(i + "::" + libraryBook, booksInString[i]);
            } catch(IllegalArgumentException e) {
                assertEquals(i + "::", booksInString[i]);
            }
        }
    }

    @BeforeEach
    public void setDefaultValues() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        books = booksFactory.books();
        library = libraryFactory.create(books.size());
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}