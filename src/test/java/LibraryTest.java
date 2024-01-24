import books.AbstractBook;
import books.Book;
import books.Comic;
import books.Novel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class LibraryTest {

    @Test
    public void abstractBookTest() {
        AbstractBook abstractBook = new AbstractBook("t", "a") {
            @Override
            public String toString() {
                return String.format("$ %s - %s $", title, author);
            }
        };

        assertEquals("t", abstractBook.getTitle());
        assertEquals("a", abstractBook.getAuthor());
        assertEquals("$ t - a $", abstractBook.toString());

        assertFalse(abstractBook.isBorrowed());
        abstractBook.borrowed();
        assertTrue(abstractBook.isBorrowed());
        abstractBook.returned();
        assertFalse(abstractBook.isBorrowed());

        Field[] fields = AbstractBook.class.getDeclaredFields();

        Arrays.stream(fields).forEach(field -> {
            assertTrue(Modifier.isProtected(field.getModifiers()));
        });
    }

    @Test
    public void comicTest() {
        Comic comic = new Comic("t", "a", "c");

        assertDoesNotThrow(() -> {
            Field companyField = Comic.class.getDeclaredField("company");
            assertTrue(Modifier.isPrivate(companyField.getModifiers()));
        });

        assertEquals("c", comic.getCompany());
        assertEquals("@= t (c) =@ writer: a", comic.toString());
    }

    @Test
    public void bookTest() {
        Book book = new Book("t", "a", "s");

        assertDoesNotThrow(() -> {
            Field subtitleField = Book.class.getDeclaredField("subtitle");
            assertTrue(Modifier.isPrivate(subtitleField.getModifiers()));
        });

        assertEquals("s", book.getSubtitle());
        assertEquals("*[ t - s ]* by: a", book.toString());
    }

    @Test
    public void novelTest() {
        Novel novel = new Novel("t", "a", "g1,g2,g3");

        assertDoesNotThrow(() -> {
            Field genresField = Novel.class.getDeclaredField("genres");
            assertTrue(Modifier.isPrivate(genresField.getModifiers()));
        });

        assertArrayEquals(new String[]{"g1", "g2", "g3"}, novel.getGenres());
        assertEquals("g1,g2,g3", novel.getAllGenres());
        assertEquals("<[ t /g1,g2,g3 ]> from: a", novel.toString());
    }

    @Test
    public void libraryFieldsTest() {
        assertDoesNotThrow(() -> {
            Field addressField = Library.class.getDeclaredField("address");
            assertTrue(Modifier.isPrivate(addressField.getModifiers()));

            Field openingHoursField = Library.class.getDeclaredField("openingHours");
            assertTrue(Modifier.isPrivate(openingHoursField.getModifiers()));

            Field closingTimeField = Library.class.getDeclaredField(
                    "closingTime");
            assertTrue(Modifier.isPrivate(closingTimeField.getModifiers()));

            Field MaxBooksField = Library.class.getDeclaredField("MaxBooks");
            assertTrue(Modifier.isPrivate(MaxBooksField.getModifiers()));
            assertTrue(Modifier.isStatic(MaxBooksField.getModifiers()));

            Field booksField = Library.class.getDeclaredField("books");
            assertTrue(Modifier.isPrivate(booksField.getModifiers()));

            Field booksCountField = Library.class.getDeclaredField("booksCount");
            assertTrue(Modifier.isPrivate(booksCountField.getModifiers()));
        });
    }

    @Test
    public void libraryTest() throws NoSuchFieldException, IllegalAccessException {
        Field maxBooksField = Library.class.getDeclaredField("MaxBooks");
        maxBooksField.setAccessible(true);
        assertEquals(100000, (int) maxBooksField.get(Library.class));

        Library.debugMode(true);

        assertEquals(5, (int) maxBooksField.get(Library.class));

        Library library = new Library("The Address. St 2", 8, 13);

        assertEquals("The Address. St 2", library.getAddress());
        assertEquals(8, library.getOpeningHours());
        assertEquals(13, library.getClosingTime());
        assertEquals("8 - 13", library.getHours());

        AbstractBook bookA = new Novel(
                "Wish You Were Here",
                "Renee Carlino",
                "Romance,Fiction,New Adult"
        );

        AbstractBook bookB = new Novel(
                "Before We Were Strangers",
                "Renee Carlino",
                "Romance,Contemporary,New Adult"
        );

        AbstractBook bookC = new Book(
                "Deep Work",
                "Cal Newport",
                "Rules for Focused Success in a Distracted World"
        );

        AbstractBook bookD = new Comic(
                "Doctor Strange",
                "Steve Ditko",
                "Marvel"
        );

        AbstractBook bookE = new Book(
                "Test book 1",
                "Test author 1",
                "Test subtitle 1"
        );

        AbstractBook bookF = new Book(
                "Test book 2",
                "Test author 2",
                "Test subtitle"
        );

        assertEquals(0, library.getBooksCount());

        assertDoesNotThrow(() -> {
            library.addBook(bookA);
            library.addBook(bookB);
            library.addBook(bookC);
            assertEquals(3, library.getBooksCount());
            library.addBook(bookD);
            library.addBook(bookE);
        });

        assertEquals(5, library.getBooksCount());

        assertThrows(LibraryFullException.class, () -> {
            library.addBook(bookF);
        });

        assertEquals(5, library.getBooksCount());

        assertEquals(
                bookA,
                library.findBook("Wish You Were Here")
        );

        assertNull(library.findBook("Wish You Were There"));

        assertArrayEquals(
                new AbstractBook[]{ bookA, bookB, bookC, bookD, bookE },
                library.getAvailableBooks()
        );


        library.borrowBook("Wish You Were Here");
        library.borrowBook("This book does not exists");



        assertArrayEquals(
                new AbstractBook[]{ bookB, bookC, bookD, bookE },
                library.getAvailableBooks()
        );

        assertTrue(library.isBorrowed("Wish You Were Here"));
        assertFalse(library.isBorrowed("Deep Work"));
        assertFalse(library.isBorrowed("Wrong book title"));

        library.returnBook("Wish You Were Here");
        library.returnBook("Wish You Were There");

        assertFalse(library.isBorrowed("Wish You Were Here"));

        assertNull(library.removeBook("Test book 2"));

        assertEquals(
                bookE,
                library.removeBook("Test book 1")
        );

        assertEquals(
                bookA,
                library.removeBook("Wish You Were Here")
        );

        library.debugMode(false);
    }

}











// Test haye Faze 2

//public class LibraryTest {
//
//    @Test
//    public void abstractBookTest() {
//        AbstractBook abstractBook = new AbstractBook("t", "a") {
//            @Override
//            public String toString() {
//                return String.format("$ %s - %s $", title, author);
//            }
//        };
//
//        assertEquals("t", abstractBook.getTitle());
//        assertEquals("a", abstractBook.getAuthor());
//        assertEquals("$ t - a $", abstractBook.toString());
//
//        assertFalse(abstractBook.isBorrowed());
//        abstractBook.borrowed();
//        assertTrue(abstractBook.isBorrowed());
//        abstractBook.returned();
//        assertFalse(abstractBook.isBorrowed());
//    }
//
//    @Test
//    public void comicTest() {
//        Comic comic = new Comic("t", "a", "c");
//
//        assertEquals("@= t (c) =@ writer: a", comic.toString());
//    }
//
//    @Test
//    public void bookTest() {
//        Book book = new Book("t", "a", "s");
//
//        assertEquals("*[ t - s ]* by: a", book.toString());
//    }
//
//    @Test
//    public void novelTest() {
//        Novel novel = new Novel("t", "a", "g1,g2,g3");
//
//        assertArrayEquals(new String[]{"g1", "g2", "g3"}, novel.getGenres());
//        assertEquals("g1,g2,g3", novel.getAllGenres());
//        assertEquals("<[ t /g1,g2,g3 ]> from: a", novel.toString());
//    }
//
//    @Test
//    public void libraryFieldsTest() {
//        assertDoesNotThrow(() -> {
//            // Spoiler for ghesmate tashvighi
//            Field MaxBooksField = Library.class.getDeclaredField("MaxBooks");
//            assertTrue(Modifier.isPrivate(MaxBooksField.getModifiers()));
//            assertTrue(Modifier.isStatic(MaxBooksField.getModifiers()));
//        });
//    }
//
//
//    @Test
//    public void libraryTest() throws NoSuchFieldException, IllegalAccessException {
//        // Check line 77 and 82
//        Field maxBooksField = Library.class.getDeclaredField("MaxBooks");
//        maxBooksField.setAccessible(true);
//        // Checks the initial value of MaxBooks
//        assertEquals(100000, (int) maxBooksField.get(Library.class));
//
//        Library.debugMode(true);
//
//        // Checks the debug value of MaxBooks
//        assertEquals(5, (int) maxBooksField.get(Library.class));
//
//        Library library = new Library("The Address. St 2", 8, 13);
//
//        assertEquals("The Address. St 2", library.getAddress());
//        assertEquals(8, library.getOpeningHours());
//        assertEquals(13, library.getClosingTime());
//        assertEquals("8 - 13", library.getHours());
//
//        AbstractBook bookA = new Novel(
//                "Wish You Were Here",
//                "Renee Carlino",
//                "Romance,Fiction,New Adult"
//        );
//
//        AbstractBook bookB = new Novel(
//                "Before We Were Strangers",
//                "Renee Carlino",
//                "Romance,Contemporary,New Adult"
//        );
//
//        AbstractBook bookC = new Book(
//                "Deep Work",
//                "Cal Newport",
//                "Rules for Focused Success in a Distracted World"
//        );
//
//        AbstractBook bookD = new Comic(
//                "Doctor Strange",
//                "Steve Ditko",
//                "Marvel"
//        );
//
//        AbstractBook bookE = new Book(
//                "Test book 1",
//                "Test author 1",
//                "Test subtitle 1"
//        );
//
//        AbstractBook bookF = new Book(
//                "Test book 2",
//                "Test author 2",
//                "Test subtitle"
//        );
//
//        assertEquals(0, library.getBooksCount());
//
//        assertDoesNotThrow(() -> {
//            library.addBook(bookA);
//            library.addBook(bookB);
//            library.addBook(bookC);
//            assertEquals(3, library.getBooksCount());
//            library.addBook(bookD);
//            library.addBook(bookE);
//        });
//
//        assertEquals(5, library.getBooksCount());
//
//        assertThrows(LibraryFullException.class, () -> {
//            library.addBook(bookF);
//        });
//
//        assertEquals(5, library.getBooksCount());
//    }
//}




  //test haye Faze 1
//public class LibraryTest {
//    @Test
//    public void bookTest() {
        // Test Book class here

//       Novel n1 = new Novel("The Witcher" , "Andrzej Sapkowski" , new String[] {"Action" , "Fantasy" , "Adventure"});
//       assertArrayEquals(new String[] {"Action"}, new  String[]{n1.getGenres(1)});
//       assertArrayEquals(new String[]{"Fantasy"}, new String[]{n1.getGenres(2)});
//       assertEquals("[Action, Fantasy, Adventure]", Arrays.toString(n1.getAllGenres()));
//
//
//        Book b1 = new Book("Java","Paul & Harvey Dietel","Object Oriented Programming Learning");
//        assertEquals("Object Oriented Programming Learning",b1.getSubtitle());
//        assertEquals("Subtitle : Object Oriented Programming Learning",b1.toString());
//        assertTrue(b1.borrowed());
//        assertEquals("Yes",b1.isBorrowed());
//        assertTrue(b1.returned());
//        assertEquals("No",b1.isBorrowed());
//
//        Comic c1 = new Comic("Java","Paul & Harvey Dietel" , "Nas");
//        assertEquals("Nas",c1.getCompany());
//        assertEquals("Publisher : Nas", c1.toString());
//        assertEquals("Java",c1.getTitle());
//        assertEquals("Paul & Harvey Dietel",c1.getAuthor());


 //   }

//    @Test
//    public void libraryTest() throws LibraryFullException {
        // Test library class here
//        Book a = new Book("Math" , "Stwart" , "University Math") ;
//        Book b = new Book("Fizik", "Haliday & Resnick & Walker" , "University Fizik");
//        Comic c = new Comic("Iron man" , "StanLee" , "Marvel");
//        Comic d = new Comic("The Batman" , "Warner Bros" , "DC");
//        Book b1 = new Book("Java","Paul & Harvey Dietel","Object Oriented Programming Learning");
//        Comic c1 = new Comic("Java","Paul & Harvey Dietel" , "Nas");
//        Library BagheKetab = new Library("ran Western side of the, National Library Blvd، Haghani Hwy, National Library",
//                "9" , "22" , new AbstractBook[]{a , b , c , d , b1 });
//        Library.debugMode(true);
//        assertEquals("[Subtitle : University Math, Subtitle : University Fizik, Publisher : Marvel, Publisher : DC, Subtitle : Object Oriented Programming Learning]" , Arrays.toString(BagheKetab.getAllBooks()));
//
//        assertEquals(100000,Library.debugMode(false));
//        assertEquals(5, Library.debugMode(true));
//        assertEquals("9", BagheKetab.getOpeningHours());
//        assertEquals("22" , BagheKetab.getClosingTime());
//        assertEquals("from 9 to 22" , BagheKetab.getHours());
//        assertEquals("ran Western side of the, National Library Blvd، Haghani Hwy, National Library", BagheKetab.getAddress());
//
//        //// Adding & Removing Books
//        //BagheKetab.removeBook(b);
//        BagheKetab.addBook(c1);
//        assertEquals("Java" , BagheKetab.findBook(c1));
//        //assertNull(BagheKetab.findBook(b));
//
//
//
//        //// Test For Borrowing Books
//        BagheKetab.borrowBook("Math");
//        assertTrue(BagheKetab.isBorrowed("Math"));
//        assertFalse(BagheKetab.isBorrowed("Fizik"));
//        //// Testing Books retuning
//        BagheKetab.returnBook("Math");
//        assertFalse(BagheKetab.isBorrowed("Math"));
//        assertEquals("[Math, Fizik, Iron man, The Batman, Java]",Arrays.toString(BagheKetab.getAvailableBooks()));




 //   }

