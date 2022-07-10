package entity;

public class Language {
    private int languageID;
    private String languageDescription;
    
    public Language(String languageDescription) {
        this.languageDescription = languageDescription;
    }

        public Language(int languageID, String languageDescription) {
        this.languageID = languageID;
        this.languageDescription = languageDescription;
    }

    public void printDetails(){
        System.out.println(languageID + ". " + languageDescription);
    }

    public int getLanguageID() {
        return languageID;
    }

    public String getLanguageDescription() {
        return languageDescription;
    }

    public void setLanguageDescription(String languageDescription) {
        this.languageDescription = languageDescription;
    }

}
