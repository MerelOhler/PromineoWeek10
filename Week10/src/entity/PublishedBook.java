package entity;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PublishedBook {
    private int publishedBookID;
    private Book book;
    private String publishedBookName;
    private int yearPublished;
    private Publisher publisher;
    private Language language;
    private String translator;
    private boolean finished = false;
    private Date dateFinished;
    
   
    public PublishedBook(int publishedBookID, Book book, String publishedBookName, int yearPublished,
            Publisher publisher, Language language, String translator, Date dateFinished) {
        this.publishedBookID = publishedBookID;
        this.book = book;
        this.publishedBookName = publishedBookName;
        this.yearPublished = yearPublished;
        this.publisher = publisher;
        this.language = language;
        this.translator = translator;
        this.dateFinished = dateFinished;
        if (dateFinished != null){this.finished = true;} 
    }

    public void printDetails(){
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        List<Author> authors = book.getAuthors();
        System.out.print(publishedBookName + " written by ");
        for (int i = 0; i < authors.size(); i++){
            if (i < authors.size() - 1){
                System.out.print(authors.get(i).getName() + " and ");
            } else {
                System.out.print(authors.get(i).getName());
            }
        }
        if (dateFinished != null){ System.out.print(" Finished book on: " + df.format(dateFinished));}
    }

    public int getPublishedBookID() {
        return publishedBookID;
    }

    public Book getBook() {
        return book;
    }

    public String getPublishedBookName() {
        return publishedBookName;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Language getLanguage() {
        return language;
    }

    public String getTranslator() {
        return translator;
    }
    
    public boolean isFinished() {
        return finished;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPublishedBookName(String publishedBookName) {
        this.publishedBookName = publishedBookName;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }  
    
}
