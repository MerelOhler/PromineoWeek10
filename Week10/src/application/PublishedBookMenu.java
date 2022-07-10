package application;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.PublishedBookDao;
import entity.PublishedBook;

public class PublishedBookMenu {

    private List<PublishedBook> allPBooks;
    private PublishedBookDao publishedBookDao = new PublishedBookDao();
    private Scanner kb = new Scanner(System.in);
    private int choice;

    public void finishBook() throws SQLException{
        int choice;
        System.out.println("Please select which book you would like to set to finished");
        System.out.println("0. go back to the main menu");
        getAllPBooks();
        choice = getPreciseInput(0, allPBooks.size());
        while (choice == -1){
            choice = getPreciseInput(0, allPBooks.size());
        }
        PublishedBook selectedBook = allPBooks.get(choice-1);
        if(selectedBook.isFinished()){
            System.out.println("you already finished this book");
            System.out.println("we are currently unable to add multiple finishdates to one book");
            System.out.println("do you want to update the old date to the new date?");
            System.out.println("1. yes, please update to the new date");
            System.out.println("2. no, please take me back to the main menu");
            choice = getPreciseInput(1, 2);
            while (choice == -1){
                choice = getPreciseInput(1, 2);
            }
            if (choice == 1){
                finishBook2DB(selectedBook, true);
            } else if (choice == 2){
                return;
            }            
        } else {
            finishBook2DB(selectedBook, false);
        }

    }//finishbook

    public void deleteBook() throws SQLException{
        System.out.println("Please select which book you would like to delete and hit enter");
        System.out.println("0. I don't want to delete any books, take me back to the main menu");
        getAllPBooks();

        int choice = getPreciseInput(0, allPBooks.size());
        while (choice == -1){
            System.out.println("That was not a valid choice please try again.");
            choice = getPreciseInput(0, allPBooks.size());
        }
        if (choice == 0){
            return;
        } else {
            PublishedBook selectedBook = allPBooks.get(choice - 1);

            System.out.println("are you sure you want to delete this book?");
            selectedBook.printDetails();
            System.out.println();
            System.out.println("1. yes, delete it already!");
            System.out.println("2. no, let's go back to safety!");
            choice = getPreciseInput(1, 2);
            while (choice == -1){
                choice = getPreciseInput(1, 2);
            }
            if (choice == 1){
                publishedBookDao.deletePBook(selectedBook.getPublishedBookID());
                getAllPBooks();
                return;
            } else {
                return;
            }
        }
    }//deletebook

    private void finishBook2DB(PublishedBook selectedBook, boolean updatefinish) throws SQLException{
        System.out.println("Please input the date that you finished the book in YYYY-MM-DD format");
        String dateString = kb.nextLine();
        Date date = null;
        while (date == null){
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(dateString);
            } catch (Exception e) {
                System.out.println("Please input the date that you finished the book in YYYY-MM-DD format");
                dateString = kb.nextLine();
            }
        }
        publishedBookDao.addFinishedDate(selectedBook.getPublishedBookID(), date, updatefinish);
        getAllPBooks();
    }

    private void getAllPBooks() throws SQLException{
        allPBooks = publishedBookDao.getAllBooks();
        for (int i = 1; i <= allPBooks.size(); i++){
            System.out.print(i + ". ");
            allPBooks.get(i-1).printDetails();
            System.out.println();
        }
    }//getAllPBooks


    private int getPreciseInput(int min, int max){
        try {
            choice = Integer.parseInt(kb.nextLine());
            if (choice < min || choice > max){ choice = -1;}
        } catch (Exception e) {
            choice = -1;
        }
        return choice;
    }
    
}
