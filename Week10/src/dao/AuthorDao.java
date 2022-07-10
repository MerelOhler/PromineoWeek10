package dao;

import java.sql.*;
import java.text.Normalizer;
import java.util.*;

import entity.Author;

public class AuthorDao {

    private Connection connection;
    private List<Author> authors;
    private final String GET_ALL_AUTHORS_QUERY = "SELECT authorID, FirstName, MiddleName, LastName, aka FROM Author";
    private final String GET_ALL_P_BOOKS_QUERY = "SELECT A.authorID, A.FirstName, A.MiddleName, A.LastName, A.aka, BA.bookID FROM author AS A INNER JOIN bookauthor AS BA ON A.authorID = BA.authorID WHERE BA.BookID = ?";
    private final String ADD_AUTHOR = "INSERT INTO mybooks.author (firstname, middlename, lastname, aka) VALUES(?,?,?,?)";
    private final String GET_NEW_ID = "SELECT authorID FROM author ORDER BY authorID DESC LIMIT 1";

    public AuthorDao() {
        this.connection = DBConnection.getConnection();
    }

    public List<Author> getAllAuthors() throws SQLException{
        ResultSet rs = connection.prepareStatement(GET_ALL_AUTHORS_QUERY).executeQuery();
              
        authors = new ArrayList<>();
        while (rs.next()){
            authors.add(populateAuthor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5)));
        }
        return authors;
    }
    
    public List<Author> getAllAuthors4Book(int bookID) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(GET_ALL_P_BOOKS_QUERY);
        ps.setInt(1, bookID);
        ResultSet rs = ps.executeQuery();

        authors = new ArrayList<>();
        while (rs.next()){
            authors.add(populateAuthor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5)));
        }
        return authors;
    }

    public int addAuthor(String firstName, String middleName, String lastName, String aka) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(ADD_AUTHOR);
        ps.setString(1, firstName);
        ps.setString(2, middleName);
        ps.setString(3, lastName);
        ps.setString(4, aka);
        ps.executeUpdate();

        ResultSet rs = connection.prepareStatement(GET_NEW_ID).executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;        
    }
    
    private Author populateAuthor (int id, String firstName, String middleName, String lastName, String aka){
        return new Author(id, removeDiacritics(firstName), removeDiacritics(middleName), removeDiacritics(lastName),removeDiacritics(aka));
    }

    private String removeDiacritics(String diacritics){
        if(diacritics == null){
            return null;
        }
        return Normalizer.normalize(diacritics, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
