package application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.PublishedBookDao;
import entity.PublishedBook;

public class Menu {
    private int choice;
    private PublishedBookDao publishedBookDao = new PublishedBookDao();
    private Scanner kb = new Scanner(System.in);
    private List<PublishedBook> allPBooks;

    public void start(){
        boolean end = false;
        AuthorMenu authorMenu = new AuthorMenu();
        AddPBookMenu addPBookMenu = new AddPBookMenu();
        PublishedBookMenu publishedBookMenu = new PublishedBookMenu();
        BookMenu bookMenu = new BookMenu();
        
        while (!end){
            showMainMenu();
            getInput();

            try {
                switch (choice) {
                    case 1:
                        getAllPBooks();
                        break;
                    case 2:
                        authorMenu.showAllAuthors();
                        break;
                    case 3:
                        bookMenu.bookVersions();;
                        break;
                    case 4:
                        addPBookMenu.addBook();
                        break;
                    case 5:
                        publishedBookMenu.finishBook();
                        break;
                    case 6:
                        publishedBookMenu.deleteBook();
                        break;
                    case 7:
                        kb.close();
                        end = true;
                        break;
                     default:
                        System.out.println("That was not a valid choice please try again.");
                }
            } catch (Exception e) {
                System.out.println("something went wrong");
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
                System.out.println(e.toString());
        }
        }

        kb.close();

    }//start

    private void showMainMenu(){
        System.out.println();
        System.out.println("Please select one of the following options and then hit enter: ");
        System.out.println("1. See all books in your library.");
        System.out.println("2. See all authors in your library.");
        System.out.println("3. See how many versions of a specific book you have.");
        System.out.println("4. Add a new book.");
        System.out.println("5. Set a book to finished.");
        System.out.println("6. Remove a book from your library.");
        System.out.println("7. Exit.");
        System.out.println();
    }//showMainMenu

    private void getAllPBooks() throws SQLException{
        allPBooks = publishedBookDao.getAllBooks();
        for (int i = 1; i <= allPBooks.size(); i++){
            System.out.print(i + ". ");
            allPBooks.get(i-1).printDetails();
            System.out.println();
        }
    }//getAllPBooks

    private int getInput(){
        try {
            choice = Integer.parseInt(kb.nextLine());
        } catch (Exception e) {
            choice = 0;
        }
        return choice;
    }
}
