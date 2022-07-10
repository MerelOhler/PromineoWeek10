package dao;

import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import entity.Author;
import entity.Book;
import entity.Language;

public class BookDao {
    private Connection connection;
    private List<Book> books;
    private LanguageDao languageDao = new LanguageDao();
    private AuthorDao authorDao = new AuthorDao();
    private final String GET_ALL_BOOKS = "SELECT * FROM book";
    private final String GET_BOOK_BY_ID = "SELECT * FROM book WHERE bookID = ?";
    private final String GET_BOOKS_BY_AUTHOR = "SELECT * FROM book AS B INNER JOIN bookauthor AS BA ON BA.bookID = B.bookID WHERE BA.authorID = ?";
    private final String INSERT_NEW_BOOK = "INSERT INTO mybooks.book (OriginalBookName, FirstEditionYear,OriginalLanguageID) VALUES (?, ?, ?)";
    private final String INSERT_NEW_BOOKAUTHOR = "INSERT INTO mybooks.bookauthor (BookID, AuthorID) VALUES (?, ?)";
    private final String GET_NEW_BOOKID = "SELECT bookID FROM book ORDER BY bookID DESC LIMIT 1";


    public BookDao() {
        this.connection = DBConnection.getConnection();
    }

    public List<Book> getAllBooks() throws SQLException{
        ResultSet rs = connection.prepareStatement(GET_ALL_BOOKS).executeQuery();
              
        books = new ArrayList<>();
        while (rs.next()){
            books.add(populateBook(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
        }
        return books;
    }

    public Book getBookByID(int bookID) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_ID);
        ps.setInt(1, bookID);
        ResultSet rs = ps.executeQuery();

        Book currentBook = null;
        while(rs.next()){
            currentBook = populateBook(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
        }
        return currentBook;
    }

    public List<Book> getBooksByAuthor(Author author) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(GET_BOOKS_BY_AUTHOR);
        ps.setInt(1, author.getAuthorID());
        ResultSet rs = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while (rs.next()){
            books.add(populateBook(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
        }
        return books;
    }

    public int createBook(String originalBookName, int yearPublished, int originalLanguageID, List<Integer> authors) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(INSERT_NEW_BOOK);
        ps.setString(1, originalBookName);
        ps.setInt(2, yearPublished);
        ps.setInt(3, originalLanguageID);
        ps.executeUpdate();

        ResultSet rs = connection.prepareStatement(GET_NEW_BOOKID).executeQuery();
        if (rs.next()){
            createBookAuthor(rs.getInt(1), authors);
            return rs.getInt(1);
        }
        return 0;
    }

    public void createBookAuthor(int bookID, List<Integer> authors) throws SQLException{
        for (int i : authors){
            PreparedStatement ps = connection.prepareStatement(INSERT_NEW_BOOKAUTHOR);
            ps.setInt(1, bookID);
            ps.setInt(2, i);
            ps.executeUpdate();
        }
        
    }
    
    private Book populateBook (int id, String originalBookName, int yearPublished, int languageID) throws SQLException{
        Language language = languageDao.getLanguageByID(languageID);
        List<Author> authors = authorDao.getAllAuthors4Book(id);

        return new Book(id, removeDiacritics(originalBookName), yearPublished, language, authors);
    }

    private String removeDiacritics(String diacritics){
        if(diacritics == null){
            return null;
        }
        return Normalizer.normalize(diacritics, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
}
