package entity;

import java.util.List;

public class Book {
    private int bookID;
    private String originalBookName;
    private int firstPublishedYear;
    private Language originalLanguage;
    private List<Author> authors;

    public Book (String originalBookName, int firstPublishedYear, Language originalLanguage, List<Author> authors){
        this.originalBookName = originalBookName;
        this.firstPublishedYear = firstPublishedYear;
        this.originalLanguage = originalLanguage;
        this.authors = authors;
    }

    public Book (int bookID, String originalBookName, int firstPublishedYear, Language originalLanguage, List<Author> authors){
        this.bookID = bookID;
        this.originalBookName = originalBookName;
        this.firstPublishedYear = firstPublishedYear;
        this.originalLanguage = originalLanguage;
        this.authors = authors;
    }

    public void printDetails(){
        System.out.print(bookID + ". " + originalBookName + " written by ");
        for (int i = 0; i < authors.size(); i++){
            if (i < authors.size() - 1){
                System.out.print(authors.get(i).getName() + " and ");
            } else {
                System.out.print(authors.get(i).getName());
            }
        }
        System.out.println();
    }

    public int getBookID() {
        return bookID;
    }

    public String getOriginalBookName() {
        return originalBookName;
    }

    public int getFirstPublishedYear() {
        return firstPublishedYear;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setOriginalBookName(String originalBookName) {
        this.originalBookName = originalBookName;
    }

    public void setFirstPublishedYear(int firstPublishedYear) {
        this.firstPublishedYear = firstPublishedYear;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
}
