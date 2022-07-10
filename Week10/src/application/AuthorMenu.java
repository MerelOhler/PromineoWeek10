package application;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import dao.AuthorDao;
import dao.PublishedBookDao;
import entity.Author;
import entity.PublishedBook;

public class AuthorMenu {
    private AuthorDao authorDao = new AuthorDao();
    private PublishedBookDao publishedBookDao = new PublishedBookDao();
    private int choice;
    private Scanner kb = new Scanner(System.in);

    public void showAllAuthors() throws SQLException{
        boolean done = false;
        List<Author> authors = authorDao.getAllAuthors();
        for (Author author : authors){
            author.printDetails();
        }
        
        while (!done){
            System.out.println();
            System.out.println("Please choose one of the following options and hit enter:");
            System.out.println("1. To see all the books in your library by a specific author");
            System.out.println("2. Show all authors again");
            System.out.println("3. Go back");
            
            getInput();

            switch (choice){
                case 1:
                    showBooks4Author(authors);
                    break;
                case 2:
                    for (Author author : authors){
                        author.printDetails();
                    }
                    break;
                case 3:
                    done = true;
                    return;
                default:
                    System.out.println("That was not a valid choice please try again.");

            }
        }
    }//showAllAuthors

    private void showBooks4Author(List<Author> authors) throws SQLException{
        System.err.println();
        System.err.println("Please select the id of an author listed above and hit enter");

        
        getInput();

        if (choice > 0 && choice <= (authors.size() + 1)){
            List<PublishedBook> pBooks = publishedBookDao.getAllPBooks4Author(authors.get(choice-1));
            for (int i = 1; i <= pBooks.size() ; i++){
                System.out.print(i + ". ");
                pBooks.get(i-1).printDetails();
                System.out.println();
            }
        } else {
            System.out.println("This is not a valid choice please try again.");
        }
    }//showBooks4Author

    private int getInput(){
        try {
            choice = Integer.parseInt(kb.nextLine());
        } catch (Exception e) {
            choice = 0;
        }
        return choice;
    }
    
}
