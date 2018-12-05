package c.dragos.qrcodesapp;


public class Items {

    public String Item;
    public Boolean Availability;
    public String UserID;
    public String ItemID;
    public String UserEmail;

    public Items () {
        //for associate an item from database to a new Items object
    }

    public Items (String item, Boolean Availability, String UserID, String ItemID, String UserEmail) {

        this.Item = item;
        this.Availability = Availability;
        this.UserID = UserID;
        this.ItemID = ItemID;
        this.UserEmail = UserEmail;

    }
}
