package c.dragos.qrcodesapp;

public class FevUser  {

    public String Name;
    public String PhoneNumber;
    public String Email;
    public boolean Admin;

    public FevUser() {
        ////for associate a FevUser from database to a new FevUser object
    }

    public FevUser (String Email) {

        this.Email = Email;

    }

    public FevUser(String Name, String PhoneNumber, String Email, boolean admin) {

        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.Admin = admin;

    }
}
