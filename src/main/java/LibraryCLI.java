import books.AbstractBook;
import books.Book;
import books.Novel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class LibraryCLI {
    public static void main(String[] args) throws LibraryFullException {
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("\n * Helloooo I'm Talrary!! I'm At Your Service! For Any Help Just Type /help ");
        boolean check = true;
        while(check) {
            String input1 = scanner1.nextLine();
            String[] command = input1.split(" ");

            if (Objects.equals(command[0], "/help")) {
                System.out.println("!$! create -adr <address> -opn <openingHour> -cls <closingHours> : To create a new Library !$!");
                System.out.println("!$! get adr : To access the address of library !$!");
                System.out.println("!$! get opn : To access the opening Hour !$!");
                System.out.println("!$! get cls : To access the closing hours !$!");
                System.out.println("!$! get hrs : To access the working hours of library !$!");
                System.out.println("!$! add <type> -t <title> -a <author> -s <subtitle> : To add a new book !$!");
                System.out.println("!$! remove <title> : To remove a book from library !$!");
                System.out.println("!$! find <title> : To find a book in library !$!");
                System.out.println("!$! borrow <title> : To borrow a book from library !$!");
                System.out.println("!$! return <title> : To return a book to library !$!");
                System.out.println("!$! available : To see the available books !$!");
                System.out.println("!$! count : To show the number of available books !$!");
                System.out.println(">>> To Start Any Process You Need To Create A Library First By The KeyWord create ");

            }
            else if(Objects.equals(command[0] , "create")) {
                System.out.print("> Please Enter the Address : ");
                String adr = scanner2.nextLine();
                System.out.print("> Please Enter The Opening Hour : ");
                int opn = scanner2.nextInt();
                System.out.print("> Please Enter The Closing Hours : ");
                int cls = scanner2.nextInt();
                Library nit = new Library(adr, opn, cls);
                boolean check1 = true;
                while (check1) {
                    System.out.println(">> What Can I Do For You ;)");
                    String input2 = scanner1.nextLine();
                    String[] commands = input2.split(" ");
                    if (Objects.equals(commands[0], "get")) {
                        if (Objects.equals(commands[1], "adr")) {
                            System.out.print(">>> Hope To See You At : ");
                            System.out.println(nit.getAddress());
                        }
                        if (Objects.equals(commands[1], "opn")) {
                            System.out.print(">>> The Library Stars Working At : ");
                            System.out.println(nit.getOpeningHours());
                        }
                        if (Objects.equals(commands[1], "cls")) {
                            System.out.print(">>> The Library Finishes Working At : ");
                            System.out.println(nit.getClosingTime());
                        }
                        if (Objects.equals(commands[1], "hrs")) {
                            System.out.print(">>> Our Working Time Is : ");
                            System.out.println(nit.getHours());
                        }
                    }
                    else if (Objects.equals(commands[0], "add")) {
                        boolean check2 = true;
                        while (check2) {
                            System.out.print("> Enter The Type of Book : ");
                            String type = scanner1.nextLine();
                            if ((Objects.equals(type, "Book")) || (Objects.equals(type, "Novel")) || (Objects.equals(type, "Comic"))) {
                                if(type.equals("Book")) {
                                    System.out.print(">> Enter Title Of The Book : ");
                                    String t = scanner1.nextLine();
                                    System.out.print(">> Enter The Author Of The Book : ");
                                    String a = scanner1.nextLine();
                                    System.out.print(">> Enter The Subtitles Of The Book : ");
                                    String s = scanner1.nextLine();
                                    AbstractBook sample = new Book(t, a, s);
                                    nit.addBook(sample);
                                    System.out.println(">>> Your Book Has Added To The Library ");
                                    check2 = false;
                                }

                            } else {
                                System.out.println("! Couldn't Find This Type Please Try Again ! ");
                            }
                        }

                    }
                    else if (Objects.equals(commands[0], "remove")) {
                        System.out.print("> Enter The Title That You Want To Remove :");
                        String t = scanner1.nextLine();
                        System.out.println(">> "+ t + " Successfully Removed From Library <<");
                        nit.removeBook(t);
                    }
                    else if (Objects.equals(commands[0], "Borrow")) {
                        System.out.print("> Enter The Title That You Want To Borrow : ");
                        String t = scanner1.nextLine();
                        System.out.println(">> "+ t + " Borrowed Successfully! Have A Brilliant Time With It <<");
                        nit.borrowBook(t);
                    }
                    else if (Objects.equals(commands[0], "return")) {
                        System.out.print("> Enter The Title That You Want To Return :");
                        String t = scanner1.nextLine();
                        System.out.println(">> "+ t + " Returned Successfully! Hope You Enjoy It <<");
                        nit.returnBook(t);
                    }
                    else if (Objects.equals(commands[0], "available")) {
                        System.out.println("Kolan To Return Type Array milangam");
                        System.out.print(Arrays.toString(nit.getAvailableBooks()));
                    }
                    else if (Objects.equals(commands[0], "count")) {
                        System.out.print("The Number Of Available Books Are : ");
                        System.out.println(nit.getBooksCount());
                    }
                    else {
                        System.out.println("!!! Not Supported Command Please Try Again : ");
                    }
                }
            }
        }
    }
}
 