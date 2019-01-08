package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class View_Available_Items extends AppCompatActivity {

    private Button MainPageButton;
    private TextView AvailableItemsText;
    private FirebaseDatabase myDatabase;
    private DatabaseReference myDatabaseRef;
    private String Item;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_available_items);

        progress=findViewById(+R.id.progressBar);
        MainPageButton = findViewById(R.id.ReturnToMainPageButton);
        AvailableItemsText = findViewById(R.id.AvailableItemsTextView);
        AvailableItemsText.setMovementMethod(new ScrollingMovementMethod());
        AvailableItemsText.setText("");

        myDatabase = FirebaseDatabase.getInstance();
        myDatabaseRef = myDatabase.getReference();

        Button SearchButton = findViewById(R.id.TakeButton2);

        progress.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        Item = extras.getString("Item");

        myDatabaseRef.child("Available Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> items = dataSnapshot.getChildren();

                for (DataSnapshot contact : items) {

                    Items currentItem = contact.getValue(Items.class);

                    if (currentItem.Availability) {

                        if (currentItem.ItemGroup.equals(Item)) {

                            AvailableItemsText.append(System.getProperty("line.separator"));
                            AvailableItemsText.append(currentItem.Item);

                        }
                    }
                }
                progress.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        MainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(View_Available_Items.this, Main_Page.class));

            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Available_Items.this, Scan_QR_For_Taking_Items.class));
            }
        });

    }
}
