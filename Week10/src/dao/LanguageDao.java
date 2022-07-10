package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Language;

public class LanguageDao {
    private Connection connection;
    private final String GET_ALL_LANGUAGES = "SELECT * FROM languages";
    private final String GET_LANGUAGE_BY_ID_QUERY = "SELECT * FROM languages WHERE languageID = ?";
    private final String ADD_LANGUAGE = "INSERT INTO mybooks.languages (LanguageDescription) VALUES (?)";
    private final String GET_NEW_LANGUAGEID = "SELECT languageID FROM Languages ORDER BY languageID DESC LIMIT 1";

    public LanguageDao() {
        this.connection = DBConnection.getConnection();
    }

    public List<Language> getAllLanguages() throws SQLException{
        List<Language> languages = new ArrayList<>();
        ResultSet rs = connection.prepareStatement(GET_ALL_LANGUAGES).executeQuery();
        while (rs.next()){
            languages.add(new Language(rs.getInt(1), rs.getString(2)));
        }
        return languages;
    }

    public Language getLanguageByID(int languageID) throws SQLException{

         PreparedStatement ps = connection.prepareStatement(GET_LANGUAGE_BY_ID_QUERY);
         ps.setInt(1, languageID);
         ResultSet rs = ps.executeQuery();
         Language language = null;
         while (rs.next()){
             language = new Language(rs.getInt(1), rs.getString(2));
         }

        return language;
    }

    public int addLanguage(String language) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(ADD_LANGUAGE);
        ps.setString(1, language);
        ps.executeUpdate();

        ResultSet rs = connection.prepareStatement(GET_NEW_LANGUAGEID).executeQuery();
        if (rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

}
