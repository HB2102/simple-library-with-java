import books.AbstractBook;
import books.Novel;

import java.util.Objects;


public class Library {

    private String address;
    private int  openingHours;
    private int  closingTime;
    private static int MaxBooks=300000;
    private int booksCount = 0 ;
    public boolean[] borrow = new boolean[MaxBooks] ;
    private AbstractBook[] books = new AbstractBook[MaxBooks];

    public Library(String address , int openingHours , int closingTime ){
        this.address = address;
        this.openingHours = openingHours;
        this.closingTime = closingTime;
    }

    public static int debugMode(boolean mode){
        if (mode){
            MaxBooks = 5;
            return 5;
        }
        else {
            return 100000;
        }
    }

    public String  getAddress(){
        return address;
    }
    public int  getOpeningHours(){
        return openingHours;
    }
    public int  getClosingTime(){
        return closingTime;
    }
    public String  getHours(){
        return  openingHours + " - " + closingTime ;
    }
    public int getBooksCount(){return booksCount;}

    public void addBook(AbstractBook book) throws LibraryFullException {
        if (booksCount == MaxBooks) throw new LibraryFullException();
        this.books[booksCount++] = book;
    }


    public AbstractBook removeBook (String title){
        int i = 0 ; int j; int check = 0 ;
        AbstractBook temp; AbstractBook a = null;

        for(i = 0;i<MaxBooks;i++) {
            if (Objects.equals(books[i].getTitle(), title)) {
                a = books[i];
                check = 1;
                break;
            }
        }
        if(check==1) {
            for (; i < booksCount-1; i++) {
                j = i + 1;
                temp = books[i];
                books[i] = books[j];
                books[j] = temp;
            }
            booksCount--;
            return a;
        }
        else{
            return null;
        }
    }
    public Novel findBook(String title){
        int check = 1;
        int i = 0;
        for (;i<booksCount;i++) {
            if (Objects.equals(books[i].getTitle(), title)) {
                check = 0;
                break;
            }
        }
        if(check==1){
            return null;
        }
        else {

            return (Novel) books[i];
        }
    }

    public void borrowBook(String title){
        for(int i = 0 ; i<booksCount;i++){
            if(Objects.equals(books[i].getTitle(), title)){
                borrow[i] = true;
                booksCount--;
                break;
            }
        }
    }
    public boolean isBorrowed(String title){
        int check = 0;
        int i = 0;
        for (; i<booksCount;i++){
            if (Objects.equals(books[i].getTitle(), title)){
                check = 1;
                break;
            }}
        if(check==1){
            return borrow[i];}
        else {return false;}}

    public void returnBook(String title){
        int i = 0;
        for(;i<booksCount;i++){
            if (Objects.equals(books[i].getTitle(), title)){
                if(borrow[i]){
                    borrow[i]=false;
                    break;
                }
            }
        }
    }
    public AbstractBook[] getAvailableBooks(){
        AbstractBook[] a = new AbstractBook[booksCount];
        int i = 0; int j = 0;
        for(;i<5;i++){
            if (!borrow[i]){
                a[j] =  books[i];
                j++;
            }
        }
        return a ;
    }
}