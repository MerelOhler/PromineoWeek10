package entity;

public class Author {
    private int authorID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String aka;
    private boolean hasAka;

    public Author(int authorID, String firstName, String middleName, String lastName, String aka) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.aka = aka;
        this.hasAka = !(aka==null);
    }

    public void printDetails(){
        System.out.print( authorID + ". " + firstName);
        if (middleName != null){System.out.print(" " + middleName);}
        if (lastName != null){System.out.print(" " + lastName);}
        if (aka != null){ 
            if(!aka.equals("")){System.out.print(" AKA: " + aka);}
        }
        System.out.println();
    }

    public String getName(){
        String name = firstName;
        if (lastName != null){name += " " + lastName;}
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public boolean hasAka() {
        return hasAka;
    }

    public void setHasAka(boolean hasAka) {
        this.hasAka = hasAka;
    }

    public int getAuthorID() {
        return authorID;
    }
    
}
