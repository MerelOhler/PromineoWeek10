package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.AuthorDao;
import dao.BookDao;
import dao.LanguageDao;
import dao.PublishedBookDao;
import entity.Author;
import entity.Book;
import entity.Language;
import entity.Publisher;

public class AddPBookMenu {
    private BookDao bookDao = new BookDao();
    private PublishedBookDao publishedBookDao = new PublishedBookDao();
    private LanguageDao languageDao = new LanguageDao();
    private AuthorDao authorDao = new AuthorDao();
    private Scanner kb = new Scanner(System.in);
    private int choice;
    private List<Book> allBooks;

    private String originalTitle;
    private int originalYear;
    private int originalLanguageID;
    private List<Integer> authors = new ArrayList<>();
    private String title;
    private int bookID;
    private int yearPublished;
    private int publisherID;
    private int languageID;
    private String translator;

    public void addBook() throws SQLException{
        boolean done = false;
        
        while (!done){
            System.out.println("Please make a selection from the following menu and hit enter:");
            System.out.println("1. I want to add a book");
            System.out.println("2. I want to go back to the main menu");

            getInput();
            switch (choice){
                case 1:
                    addNewBook();
                    break;
                case 2:
                    done = true;
                    return;
                default:
                    System.out.println("That was not a valid choice please try again.");
            }
        }
    }

    private void addNewBook() throws SQLException{
        System.out.println("We're going to start with information about the original edition of this book");
        System.out.println("This information is usually foind on the first couple of pages of a book");
        System.out.println("Please select 0 if your book is not one of these books or select the book's ID to add a new version");
        System.out.println("0. This is a completely new book");
        getAllBooks();
        choice = getPreciseInput(0, allBooks.size());
        while (choice == -1){
            System.out.println("That was not a valid choice please try again.");
            choice = getPreciseInput(0, allBooks.size());
        }
        if (choice == 0){
            createBook();
        } else {
            createPublishedBook(choice);
        }
    }

    private void createBook() throws SQLException{
        //original title
        System.out.println("Please input the original title of the book when it was first published");
        System.out.println("the original title might be in a different language if the book has been translated");
        System.err.println("please hit enter when you are done");
        originalTitle = kb.nextLine();

        //original year
        System.out.println("Please enter the year when the book was first published");
        getPreciseInput(1000, 2023);
        while (choice == -1){
            System.out.println("That was not a valid choice please try again.");
            getPreciseInput(1000, 2023);
        }
        originalYear = choice;

        //original language
        System.out.println("What language was the first edition printed in? Please make a selection and hit enter");
        System.out.println("0. My language isn't on the list");
        List<Language> languages = languageDao.getAllLanguages();
        for (Language language: languages){
            language.printDetails();
        }
        getPreciseInput(0, languages.size());

        while (choice == -1){
            System.out.println("That was not a valid choice please try again.");
            getPreciseInput(0, languages.size());
        }
        if (choice == 0){
            originalLanguageID = createLanguage();
        } else {
            originalLanguageID = choice;
        }

        createAuthors();
        if (originalLanguageID != 0 && authors.size() != 0){
            bookID = bookDao.createBook(originalTitle, originalYear, originalLanguageID, authors);
            System.out.println();
            createPublishedBook(bookID);
        } else {
            System.out.println("something went wrong, please try again");
            return;
        }

    }

    private void createAuthors() throws SQLException{
        boolean done = false;


        while (!done){
            System.out.println("Please input who wrote your book");
            System.out.println("If your book has multiple authors please input one at a time");
    
            System.out.println("0. My author is not on the list");
            List<Author> existingAuthors = authorDao.getAllAuthors();
            for(Author author:existingAuthors){
                author.printDetails();
            }
            System.err.println((existingAuthors.size() + 1) + ". I am done adding authors");
            getPreciseInput(0, (existingAuthors.size()+1));
            while (choice == -1){
                System.out.println("That was not a valid choice please try again.");
                getPreciseInput(0, (existingAuthors.size()+1));
            }
            if (choice == 0){
                authors.add(addAuthor2DB());
                System.out.println(authors);
            } else if (choice == (existingAuthors.size()+1) && authors.size() > 0){
                done = true;
            } else if (choice == (existingAuthors.size()+1) && authors.size() == 0){
                System.out.println("you need to add at least one author");
            } else {
                authors.add(choice);
            }
        }
    }

    private int addAuthor2DB() throws SQLException {
        System.out.println("please input the author's first name and hit enter");
        String firstName = kb.nextLine();
        while (firstName.equals("")){
            System.out.println("the first name cannot be blank");
            firstName = kb.nextLine();
        }

        System.out.println("please input the author's middle name, if they have none just hit enter");
        String middleName = kb.nextLine();

        System.out.println("please input the author's last name, if they have none just hit enter");
        String lastName = kb.nextLine();

        System.out.println("Some authors use a nom de plume if the author also goes by a different name please put that in");
        System.out.println("if the author does not have another name that they go by please just hit enter");
        String aka = kb.nextLine();

        return authorDao.addAuthor(firstName, middleName, lastName, aka);
    }


   
    private void createPublishedBook (int bookID) throws SQLException{
        System.out.println("Now we need information about this specific copy:");
        
        //title
        System.out.println("Please input the title of this book and hit enter");
        title = kb.nextLine();

        //year published
        System.out.println("Please input the year this copy was published and hit enter");
        getPreciseInput(1000, 2023);
        while (choice == -1){
            System.out.println("Please input a valid year");
            getPreciseInput(1000,2023);
        }
        yearPublished = choice;

        //Publisher
        System.out.println("Please make a selection of the book's publisher and hit enter");
        System.out.println("0. My publisher is not on this list");
        List<Publisher> publishers = publishedBookDao.getAllPublishers();
        for (Publisher publisher: publishers){
            publisher.printDetails();
        }
        
        getPreciseInput(0, publishers.size());
        
        while (choice == -1){
            System.out.println("That was not a valid choice please try again.");
            getPreciseInput(0, publishers.size());
        }
        if (choice == 0){
            publisherID = createPublisher();
        } else {
            publisherID = choice;
        }

        //Language
        System.out.println("What language is your book in? Please make a selection and hit enter");
        System.out.println("0. My language isn't on the list");
        List<Language> languages = languageDao.getAllLanguages();
        for (Language language: languages){
            language.printDetails();
        }
        getPreciseInput(0, languages.size());
        
        while (choice == -1){
            System.out.println("That was not a valid choice please try again.");
            getPreciseInput(0, languages.size());
        }
        if (choice == 0){
            languageID = createLanguage();
        } else {
            languageID = choice;
        }

        //translator
        System.out.println("Was this book translated? Please make input your choice and hit enter");
        System.out.println("1. Yes, this book was originally written in another language.");
        System.out.println("2. No, this book is in it's original language");
        getPreciseInput(1, 2);
        while (choice == -1){
            System.out.println("That was not a valid choice please try again.");
            getPreciseInput(1, 2);
        }
        if (choice == 1){
            System.err.println("Please input the translator's name in and hit enter.");
            translator = kb.nextLine();
        } else {
            translator = null;
        }
        if(bookID != 0 && publisherID != 0 && languageID != 0){
            int newpbookID = publishedBookDao.addPBook(bookID, title, yearPublished, publisherID, languageID, translator);
            if (newpbookID != 0){
                System.out.println("SUCCESS your book has been added");
            }
        } else {
            System.out.println("something went wrong please try again");
            return;
        }
    }//Add New Published book

    private int createLanguage() throws SQLException{
        System.out.println("Please input the language you'd like to input and hit enter");
        String newLanguage = kb.nextLine();
        int newLanguageID = languageDao.addLanguage(newLanguage);
        return newLanguageID;
    }

    private int createPublisher() throws SQLException{
        System.out.println("Please input the publisher you'd like to input and hit enter");
        String newPublisher = kb.nextLine();
        int newPublisherID = publishedBookDao.addPublisher(newPublisher);
        return newPublisherID;
    }

    private void getAllBooks() throws SQLException{
        allBooks = bookDao.getAllBooks();
        for (Book book: allBooks){
            book.printDetails();
        }
    }//getAllBooks

    private void getInput(){
        try {
            choice = Integer.parseInt(kb.nextLine());
        } catch (Exception e) {
            choice = -1;
        }
    }

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
