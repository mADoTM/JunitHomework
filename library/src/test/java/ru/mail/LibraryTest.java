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
    public void whenSizeLessBooksAmount() {
        assertThrows(IllegalArgumentException.class, () -> libraryFactory.create(books.size() - 1));
    }

    @Test
    public void libraryHasSameOrderedBooks() {
        library = libraryFactory.create(books.size() + 1);

        for(int i = 0; i < library.getSize(); i++) {
            outContent.reset();
            try {
                library.getBook(i);
            }
            catch (IllegalArgumentException e) {
                System.out.print(i + "::");
            }

            String bookString = outContent.toString();
            String expectedStr = i + "::";

            if(i < books.size()) {
                expectedStr += books.get(i).toString();
            }
            assertEquals(expectedStr, bookString);
        }
    }

    @Test
    public void whenBookTakenGivenThatBook() {
        int index = 1;
        library.getBook(index);

        String bookString = outContent.toString().split("::")[1];
        assertEquals(books.get(index).toString(), bookString);
    }

    @Test
    public void whenBookTakenInfoIsPrinting() {
        library.getBook(1);
        String cellNumber = outContent.toString().split("::")[0];
        String bookString = outContent.toString().split("::")[1];

        assertEquals(1, Integer.parseInt(cellNumber));
        assertNotEquals("", bookString);
    }

    @Test
    public void throwExceptionForEmptyCell() {
        library = libraryFactory.create(books.size() + 1);
        assertThrows(IllegalArgumentException.class, () -> library.getBook(books.size()));
    }

    @Test
    public void bookShouldPutOnFirstEmptyCell() {
        final var book0 = new Book("test book0", new Author("test author 0"));
        final var book1 = new Book("test book1", new Author("test author 1"));

        library.getBook(0);
        library.getBook(1);

        library.putBook(book1);
        library.putBook(book0);

        outContent.reset();
        library.getBook(0);
        String bookString = outContent.toString().split("::")[1];
        assertEquals(book1.toString(), bookString);

        outContent.reset();
        library.getBook(1);
        bookString = outContent.toString().split("::")[1];
        assertEquals(book0.toString(), bookString);
    }

    @Test
    public void throwExceptionWhenNoSpaceInLibrary() {
        final var testBook = new Book("test book", new Author("test author"));

        assertThrows(IllegalArgumentException.class, () -> library.putBook(testBook));
    }

    @Test
    public void printBooksPrintingInConsole() {
        library.printBooks();
        assertNotEquals(outContent.toString(), "");
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