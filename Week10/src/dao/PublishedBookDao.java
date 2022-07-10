package dao;

import java.sql.*;
import java.text.Normalizer;
import java.util.*;
import java.util.Date;

import entity.Author;
import entity.Book;
import entity.Language;
import entity.PublishedBook;
import entity.Publisher;

public class PublishedBookDao {
    private Connection connection;
    private BookDao bookDao = new BookDao();
    private LanguageDao languageDao = new LanguageDao();


    private final String GET_ALL_P_BOOKS_QUERY = "SELECT * FROM publishedbook";
    private final String GET_P_BOOKS_4_BOOK_QUERY = "SELECT * FROM publishedbook WHERE bookID = ?";
    private final String GET_PUBLISHER_BY_ID = "SELECT * FROM publisher WHERE publisherID = ?";
    private final String GET_FINISHED_DATE = "SELECT DateFinished FROM finishedpublishedbook Where PublishedBookID = ?";
    private final String GET_ALL_PUBLISHERS = "SELECT * FROM publisher";
    private final String INSERT_PBOOK = "INSERT INTO mybooks.publishedbook (BookID, PublishedBookName, YearPublished, PublisherID, LanguageID, Translator)"+
                                        " VALUES (?,?,?,?,?,?)";
    private final String GET_NEW_PBOOKID = "SELECT publishedbookID FROM publishedbook ORDER BY publishedBookID DESC LIMIT 1";
    private final String INSERT_PUBLISHER = "INSERT INTO mybooks.publisher (publisherName) VALUES (?)";
    private final String GET_NEW_PUBLISHERID = "SELECT publisherID FROM publisher ORDER BY publisherID DESC LIMIT 1";
    private final String ADD_FINISHDATE = "INSERT INTO  mybooks.finishedpublishedbook (publishedbookID, dateFinished) VALUES (?, ?)";
    private final String UPDATE_FINISHDATE = "UPDATE mybooks.finishedpublishedbook SET datefinished = ? WHERE publishedbookID = ?";
    private final String DELETE_FINISHEDDATE = "DELETE FROM mybooks.finishedpublishedbook WHERE publishedbookid = ?";
    private final String DELETE_PBOOK = "DELETE FROM mybooks.publishedbook WHERE publishedbookid = ?";
    private final String GET_PBOOKS_BY_BOOKID = "SELECT * FROM publishedbook WHERE bookid = ?";

    public PublishedBookDao() {
        this.connection = DBConnection.getConnection();
    }
    
    public List<PublishedBook> getAllBooks() throws SQLException{
        ResultSet rs = connection.prepareStatement(GET_ALL_P_BOOKS_QUERY).executeQuery();
        List<PublishedBook> allPBooks = new ArrayList<>();
        while (rs.next()){
            allPBooks.add(populatePBook(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),
                rs.getInt(5), rs.getInt(6), rs.getString(7)));
        }
        return allPBooks;
    }

    public List<PublishedBook> getAllPBooks4Author(Author author) throws SQLException{
        List<Book> books = bookDao.getBooksByAuthor(author);
        List<PublishedBook> allPBooks = new ArrayList<>();
        for (Book book: books){
            
            PreparedStatement ps = connection.prepareStatement(GET_P_BOOKS_4_BOOK_QUERY);
            ps.setInt(1, book.getBookID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                allPBooks.add(populatePBook(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),
                    rs.getInt(5), rs.getInt(6), rs.getString(7)));
            }
        }
        return allPBooks;
    }

    public int addPBook(int bookID, String title, int year, int pulisherID, int languageID, String translator) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(INSERT_PBOOK);
        ps.setInt(1, bookID);
        ps.setString(2, title);
        ps.setInt(3, year);
        ps.setInt(4, pulisherID);
        ps.setInt(5, languageID);
        ps.setString(6, translator);
        ps.executeUpdate();

        ResultSet rs = connection.prepareStatement(GET_NEW_PBOOKID).executeQuery();
        if (rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    public int addPublisher(String publisherName) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(INSERT_PUBLISHER);
        ps.setString(1, publisherName);
        ps.executeUpdate();

        ResultSet rs = connection.prepareStatement(GET_NEW_PUBLISHERID).executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    public void addFinishedDate (int pBookID, Date finishedDate, boolean update) throws SQLException{
        java.sql.Date sqlDate = new java.sql.Date(finishedDate.getTime());
        if (!update){
            PreparedStatement ps = connection.prepareStatement(ADD_FINISHDATE);
            ps.setInt(1, pBookID);
            ps.setDate(2, sqlDate);
            ps.executeUpdate();
        } else{
            PreparedStatement ps = connection.prepareStatement(UPDATE_FINISHDATE);
            ps.setDate(1, sqlDate);
            ps.setInt(2, pBookID);
            ps.executeUpdate();
        }
    }

    public void deletePBook(int pBookID) throws SQLException{
        PreparedStatement psfin = connection.prepareStatement(DELETE_FINISHEDDATE);
        psfin.setInt(1, pBookID);
        psfin.executeUpdate();

        PreparedStatement psp = connection.prepareStatement(DELETE_PBOOK);
        psp.setInt(1, pBookID);
        psp.executeUpdate();
    }

    public List<PublishedBook> getAllPBooks4BookID(int bookID) throws SQLException{
        List<PublishedBook> pBooks = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(GET_PBOOKS_BY_BOOKID);
        ps.setInt(1, bookID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            pBooks.add(populatePBook(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
                            rs.getInt(6), rs.getString(7)));
        }
        return pBooks;
    }

    private PublishedBook populatePBook(int id, int bookID, String publishedBookName, int yearPublished, int publisherID, int languageID, String translator) throws SQLException{
        Book book = bookDao.getBookByID(bookID);
        Publisher publisher = getPublisherByID(publisherID);
        Language language = languageDao.getLanguageByID(languageID);
        Date finished = finished(id);

        return new PublishedBook(id, book, removeDiacritics(publishedBookName), yearPublished, publisher, language, removeDiacritics(translator),finished);
    }

    private Publisher getPublisherByID(int id) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(GET_PUBLISHER_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Publisher publisher = null;
        while (rs.next()){
            publisher = new Publisher(rs.getInt(1), rs.getString(2));
        }

        return publisher;
    }

    public List<Publisher> getAllPublishers() throws SQLException{
        ResultSet rs = connection.prepareStatement(GET_ALL_PUBLISHERS).executeQuery();
        List<Publisher> publishers = new ArrayList<>();
        while(rs.next()){
            publishers.add(new Publisher(rs.getInt(1),rs.getString(2)));
        }
        return publishers;
    }
    private Date finished(int pBookID) throws SQLException{
        Date date = null;
        PreparedStatement ps = connection.prepareStatement(GET_FINISHED_DATE);
        ps.setInt(1, pBookID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){ date = rs.getDate(1);}
        return date;
    }

    private String removeDiacritics(String diacritics){
        if(diacritics == null){
            return null;
        }
        return Normalizer.normalize(diacritics, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
