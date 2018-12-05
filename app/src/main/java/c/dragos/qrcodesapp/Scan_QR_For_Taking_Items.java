package c.dragos.qrcodesapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;

import java.io.IOException;


public class Scan_QR_For_Taking_Items extends AppCompatActivity {

    private SurfaceView surfaceView;
    private TextView textQRValue;
    private Button AddItemButton;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String Data = ""; // Data means Uid for the ITEM from QR Code

    private DatabaseReference myDatabaseRef;
    private FirebaseAuth myAuth;
    private FirebaseUser currentUser;

    private String ItemNameFromDatabase = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr_for_taking_items);

        surfaceView = findViewById(R.id.surfaceView);
        textQRValue = findViewById(R.id.ItemDetected);
        AddItemButton = findViewById(R.id.TakeButton);

        myDatabaseRef  = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();

        AddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer the date to dd_Or_Remove_Item_From_A_User for add to firebase the item selected
                Intent myIntent = new Intent(Scan_QR_For_Taking_Items.this, Add_Or_Remove_Item_From_A_User.class);
                myIntent.putExtra("Item",Data);
                myIntent.putExtra("Action","Take Item");
                Scan_QR_For_Taking_Items.this.startActivity(myIntent);
            }
        });

    }

    private void initialiseDetectorAndSources() {

        toastMessage("QR code scanner started");
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1920,1080).setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Scan_QR_For_Taking_Items.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(Scan_QR_For_Taking_Items.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });



        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> QRs = detections.getDetectedItems();

                if (QRs.size() != 0) {
                    textQRValue.post(new Runnable() {
                        @Override
                        public void run() {
                            //Aici ramane sa verifici daca merge si fara if-ul asta
                            if(QRs.valueAt(0) != null ) {

                                textQRValue.removeCallbacks(null);
                                Data = QRs.valueAt(0).displayValue;

                                searchForItemNameInFirebaseDatabase(Data);
                                textQRValue.setText(ItemNameFromDatabase);

                            }
                            else {

                                Data = QRs.valueAt(0).displayValue;

                                searchForItemNameInFirebaseDatabase(Data);
                                textQRValue.setText(ItemNameFromDatabase);


                                }

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorAndSources();
    }

    private void toastMessage(String text) {

        Toast.makeText(Scan_QR_For_Taking_Items.this, text,
                Toast.LENGTH_SHORT).show();

    }

    //Next function will search for Item with UID from QRCode in Firebase Realtime Database and will put it into a global variable for showing in GUI

    private void searchForItemNameInFirebaseDatabase(final String QRCode) {

        myDatabaseRef.child("Available Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> items = dataSnapshot.getChildren();

                for (DataSnapshot contact : items) {

                    Items currentItem = contact.getValue(Items.class);

                    if (contact.getKey().equals(QRCode.toString())) {

                        ItemNameFromDatabase = currentItem.Item;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

