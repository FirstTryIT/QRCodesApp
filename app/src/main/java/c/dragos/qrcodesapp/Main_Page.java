package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_Page extends AppCompatActivity {

    private DatabaseReference myDatabaseRef;

    private String ID;
    private String Email;

    private Button ShowInformations;
    private Button ShowItemsList;
    private Button ViewAvailableItemsButton;
    private Button AddNewItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        ShowInformations = findViewById(R.id.ViewInfoButton);
        ShowItemsList = findViewById(R.id.ViewItemsButton);
        ViewAvailableItemsButton = findViewById(R.id.ViewAvailableItemsButton);
        AddNewItemButton = findViewById(R.id.AddItem);

        myDatabaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth myAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = myAuth.getCurrentUser();

        if (currentUser != null) {

            ID = currentUser.getUid();
            Email = currentUser.getEmail();

            myDatabaseRef.child("Users").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        WriteNewUserOnDatabase(ID);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }

        ShowInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Page.this, View_Informations_about_User.class));//3
            }
        });

        ShowItemsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Page.this, View_All_My_Items.class));//3

            }
        });

        ViewAvailableItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Page.this, View_Available_Items.class));
            }
        });

        AddNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Now I will verify if the current user has permission for adding new Item in Database
                //Have To Be put here the Mirela, Raluca, Luiza, Stefan etc UID
                if((currentUser.getUid().equals("D9WHRnV8mcRfxKsBdwALQpsQiaF3")) || (currentUser.getUid().equals("UhnpYuUGkoUuizn7qJEopbfKu4n1")) || (currentUser.getUid().equals("WVdyEgKNbsYUiAc3CXS1HnzUG2z1")) || (currentUser.getUid().equals("qc5hc6VMrPRkrr5UnrunxrMt5yW2"))) {

                    startActivity(new Intent(Main_Page.this, Add_New_Item_On_Database.class));

                }
                else {

                    toastMessage("You haven't permission for adding a new Item in Database!");

                }

            }
        });

    }

    private void WriteNewUserOnDatabase(String ID) {

        FevUser user = new FevUser(Email);
        myDatabaseRef.child("Users").child(ID).setValue(user);

    }

    //Next function is used for showing a message down on the phone, like a notification
    private void toastMessage(String text) {
        Toast.makeText(Main_Page.this, text,
                Toast.LENGTH_SHORT).show();
    }

}
