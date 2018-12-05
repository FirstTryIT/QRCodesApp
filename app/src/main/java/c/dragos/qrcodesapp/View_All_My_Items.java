package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Bogdan on 18.09.2018.
 */

public class View_All_My_Items  extends AppCompatActivity {


    private TextView ItemsTextView;
    private Button TakeItemButton;
    private Button ReturnItemButton;

    private DatabaseReference myDatabaseRef;
    private FirebaseAuth myAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_my_items);

        ItemsTextView = findViewById(R.id.ItemsList);
        ItemsTextView.setMovementMethod(new ScrollingMovementMethod());

        TakeItemButton = findViewById(R.id.TakeItemButton);
        ReturnItemButton = findViewById(R.id.ReturnItemButton);

        myDatabaseRef  = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();



        if (currentUser != null) {

            ItemsTextView.setText("");
            showAllMyItemsFromDatabase();

        }

        TakeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_All_My_Items.this, Scan_QR_For_Taking_Items.class));//3
            }
        });
        ReturnItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_All_My_Items.this, Scan_QR_For_Returning_Items.class));//3
            }
        });
    }

    private void showAllMyItemsFromDatabase() {

        myDatabaseRef.child("Available Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> items = dataSnapshot.getChildren();

                for (DataSnapshot contact : items) {

                    Items currentItem = contact.getValue(Items.class);

                    if (currentItem.UserID.toString().equals(currentUser.getUid().toString())) {

                        ItemsTextView.append(currentItem.Item.toString());
                        ItemsTextView.append(System.getProperty("line.separator"));
                    }
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
