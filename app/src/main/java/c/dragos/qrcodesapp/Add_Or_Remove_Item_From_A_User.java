package c.dragos.qrcodesapp;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Add_Or_Remove_Item_From_A_User extends AppCompatActivity {

    private TextView ItemNameText;
    private TextView AvailabilityText;
    private TextView OwnerText;

    private Button MainPageButton;

    private FirebaseDatabase myDatabase;
    private DatabaseReference myDatabaseRef;
    private FirebaseAuth myAuth;
    private FirebaseUser currentUser;

    private String Data;
    private Boolean Ok = false;

    private NotificationUtils mNotificationUtils;

    /* Next statement will be used only if we want to schedule a notification after a while.

     Here is calculate the time for showing the notification after an item is took by a user
     The notification is showed on user's phone after the item's time if the item isn't return
     I used this formula beacause the time for item in database is calculated in days and
     time for showing the notification is calculated in miliseconds
     days = miliseconds * seconds * minutes * hour. => a day = 86.400.000 miliseconds

    private static final int days = 1000*60*60*24;

    --------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_remove_item_from_a_user);

        ItemNameText = findViewById(R.id.ItemNameTextView);
        AvailabilityText = findViewById(R.id.AvailabilityTextView);
        OwnerText = findViewById(R.id.OwnerNameTextView);

        MainPageButton = findViewById(R.id.MainPageButton);

        Bundle extras = getIntent().getExtras();
        Data = extras.getString("Item");
        final String Action = extras.getString("Action");

        mNotificationUtils = new NotificationUtils(this);


        myDatabase = FirebaseDatabase.getInstance();
        myDatabaseRef = myDatabase.getReference();
        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();

        if (currentUser != null) {

            if (Action.equals("Take Item")) {

                myDatabaseRef.child("Available Items").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> items = dataSnapshot.getChildren();

                        for (DataSnapshot contact : items) {

                            Items currentItem = contact.getValue(Items.class);

                            if (currentItem != null) {
                                if (contact.getKey().equals(Data.toString())) {
                                    if (currentItem.Availability) {

                                        ItemNameText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this,R.color.Green));
                                        ItemNameText.setText(currentItem.Item);

                                        AvailabilityText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this,R.color.Green));
                                        AvailabilityText.setText("YES");

                                        OwnerText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this,R.color.Green));
                                        OwnerText.setText("None");

                                        myDatabaseRef.child("Available Items").child(Data).child("Availability").setValue(false);
                                        myDatabaseRef.child("Available Items").child(Data).child("UserID").setValue(currentUser.getUid());
                                        myDatabaseRef.child("Available Items").child(Data).child("UserEmail").setValue(currentUser.getEmail());
                                        myDatabaseRef.child("Available Items").child(Data).child("ItemID").setValue(contact.getKey());


                                        //Function used for scheduling a notification. Definition is in notification Publisher class and has to be moved here if we want to schedule a notification
                                        //scheduleNotification(30 * 1000, contact.getKey(), currentItem.Item);



                                        Ok = true;

                                    } else {

                                        ItemNameText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this,R.color.Red));
                                        ItemNameText.setText(currentItem.Item);

                                        AvailabilityText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this,R.color.Red));
                                        AvailabilityText.setText("NO");

                                        OwnerText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this,R.color.Red));
                                        OwnerText.setText(currentItem.UserEmail);

                                    }
                                }

                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            //If the action is to return an item
            } else {

                myDatabaseRef.child("Available Items").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> items = dataSnapshot.getChildren();

                        for (DataSnapshot contact : items) {

                            Items currentItem = contact.getValue(Items.class);

                            if (currentItem != null) {
                                if (contact.getKey().equals(Data.toString())) {


                                    ItemNameText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this, R.color.Red));
                                    ItemNameText.setText(currentItem.Item);

                                    AvailabilityText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this, R.color.Red));
                                    AvailabilityText.setText("NO");

                                    OwnerText.setTextColor(ContextCompat.getColor(Add_Or_Remove_Item_From_A_User.this, R.color.Red));
                                    OwnerText.setText(currentUser.getEmail());

                                    myDatabaseRef.child("Available Items").child(Data).child("Availability").setValue(true);
                                    myDatabaseRef.child("Available Items").child(Data).child("UserID").setValue("None");
                                    myDatabaseRef.child("Available Items").child(Data).child("UserEmail").setValue("None");
                                    myDatabaseRef.child("Available Items").child(Data).child("ItemID").setValue(contact.getKey());
                                    myDatabaseRef.child("Available Items").child(Data).child("LastUser").setValue(currentUser.getEmail());

                                    Ok = true;
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

        MainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Ok) {
                    if (Action.equals("Take Item")) {

                        toastMessage("Item is now in you property. Be careful!");

                        if (Build.VERSION.SDK_INT >= 27) {

                            // Next statement will show a notification with a message
                            //Big text style is used for creating an expandable notification:

                            Notification.BigTextStyle bigText = new Notification.BigTextStyle();
                            bigText.bigText("Item is now in your property.Be careful!");
                            bigText.setSummaryText("More informations: ");

                            Notification.Builder notification = mNotificationUtils.getChannelNotification("Item",bigText,"Normal");
                            mNotificationUtils.getManager().notify(101, notification.build());

                        }

                    } else {

                        toastMessage("Item isn't from now in your property. Put it in the CHEST!");

                        if(Build.VERSION.SDK_INT >= 27) {

                            // Next statement will show a notification with a message
                            //Big text style is used for creating an expandable notification:

                            Notification.BigTextStyle bigText = new Notification.BigTextStyle();
                            bigText.bigText("Item isn't from now in your property. Put it in the CHEST!");
                            bigText.setSummaryText("More informations: ");

                            Notification.Builder notification = mNotificationUtils.getChannelNotification("Item",bigText, "Normal");
                            mNotificationUtils.getManager().notify(101, notification.build());

                        }
                    }
                }
                startActivity(new Intent(Add_Or_Remove_Item_From_A_User.this, Main_Page.class));
            }
        });

    }

    //Next function is used for showing a message down on the phone, like a notification
    private void toastMessage(String text) {
        Toast.makeText(Add_Or_Remove_Item_From_A_User.this, text,
                Toast.LENGTH_SHORT).show();
    }

}