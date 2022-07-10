package entity;

public class Publisher {
    private int publisherID;
    private String name;

    public Publisher(String name){
        this.name = name;
    }

    public Publisher(int publisherID, String name) {
        this.publisherID = publisherID;
        this.name = name;
    }

    public void printDetails(){
        System.out.println(publisherID + ". " + name);
    }
    public int getPublisherID() {
        return publisherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
