package application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.BookDao;
import dao.PublishedBookDao;
import entity.Book;
import entity.PublishedBook;

public class BookMenu {

    private List<Book> allBooks;
    private List<PublishedBook> pBooks;
    private BookDao bookDao = new BookDao();
    private PublishedBookDao publishedBookDao = new PublishedBookDao();
    private int choice;
    private Scanner kb = new Scanner(System.in);


    public void bookVersions() throws SQLException{
        System.out.println("Which book do you want to see all the versions of?");
        System.out.println("0. go back to the main menu");
        getAllBooks();
        choice = getPreciseInput(0, allBooks.size());
        while (choice == -1){
            choice = getPreciseInput(0, allBooks.size());
        }
        if (choice == 0){
            return;
        } else {
            int bookID = choice;
            pBooks = publishedBookDao.getAllPBooks4BookID(bookID);
            if (pBooks.size() == 0){
                System.err.println("you don't have any versions of this book in your library");
            } else {
                for(int i = 1; i <= pBooks.size(); i++){
                    System.out.print(i + ". ");
                    pBooks.get(i-1).printDetails();
                    System.out.println();
                }
            }
        }


        
    }//bookversions

    private void getAllBooks() throws SQLException {
        allBooks = bookDao.getAllBooks();
        for (Book book: allBooks){
            book.printDetails();
        }
    }//getAllBooks

    private int getPreciseInput(int min, int max){
        try {
            choice = Integer.parseInt(kb.nextLine());
            if (choice < min || choice > max){ choice = -1;}
        } catch (Exception e) {
            choice = -1;
        }
        return choice;
    }//get precise input

    
}
