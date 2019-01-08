package c.dragos.qrcodesapp;


public class Items {

    public String Item;
    public Boolean Availability;
    public String UserID;
    public String ItemID;
    public String UserEmail;
    public String LastUser;
    public String ItemGroup;

    public Items () {
        //for associate an item from database to a new Items object
    }

    public Items (String item, Boolean Availability, String UserID, String ItemID, String UserEmail, String LastUser, String ItemGroup) {

        this.Item = item;
        this.Availability = Availability;
        this.UserID = UserID;
        this.ItemID = ItemID;
        this.UserEmail = UserEmail;
        this.LastUser = LastUser;
        this.ItemGroup = ItemGroup;

    }
}
