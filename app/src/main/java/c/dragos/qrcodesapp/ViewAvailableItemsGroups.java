package c.dragos.qrcodesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ViewAvailableItemsGroups extends AppCompatActivity {

    private Button Dem;
    private Button Monitor;
    private Button Phone;
    private Button Camera;
    private Button CANoe;
    private Button Lauterbach;
    private Button BDC;
    private Button Others;
    private Button MainPage;
    private Button SearchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewavailableitemsgroups);

        MainPage = findViewById(R.id.ReturnToMainPageButton);
        SearchItem = findViewById(R.id.TakeButton2);
        Dem = findViewById(R.id.Dem);
        Monitor = findViewById(R.id.Monitor);
        Phone = findViewById(R.id.Phone);
        Camera = findViewById(R.id.CAM);
        CANoe = findViewById(R.id.CANoe);
        Lauterbach = findViewById(R.id.Lauterbach);
        BDC = findViewById(R.id.BDC);
        Others = findViewById(R.id.Others);

        //Define the onClick functions for every button:

        Dem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","DEM");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        Monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","Monitor");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","Phone");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","CAM");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        CANoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","CANoe");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        Lauterbach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","Lauterbach");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        BDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","BDC");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        Others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to View_Available_Items for showing the specified group of items
                Intent myIntent = new Intent(ViewAvailableItemsGroups.this, View_Available_Items.class);
                myIntent.putExtra("Item","Others");
                ViewAvailableItemsGroups.this.startActivity(myIntent);

            }
        });

        MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ViewAvailableItemsGroups.this, Main_Page.class));

            }
        });

        SearchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ViewAvailableItemsGroups.this, Scan_QR_For_Taking_Items.class));

            }
        });
    }


}
