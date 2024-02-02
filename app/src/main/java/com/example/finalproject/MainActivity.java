package com.example.finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SignUpFunction(View view) {
        startActivity(new Intent(MainActivity.this,NewCustomer.class));
    }

    public void LogInFunction(View view) {
        startActivity(new Intent(MainActivity.this,LogInPageActivity.class));

    }

    public void CarryOnFuncion(View view) {
        startActivity(new Intent(MainActivity.this,CustomerPageActivity.class));
    }
}

/*public class MainActivity extends AppCompatActivity {

    TextView MarketID,MarketName,MarketPostaCode;
    //private String MarketCode = "Bim44" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MarketID = findViewById(R.id.marketid);
        MarketName = findViewById(R.id.marketname);
        MarketPostaCode = findViewById(R.id.markepostalcode);

    }

    private void ScanCode(){
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setBarcodeImageEnabled(true);
        barLaucher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() != null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Sonuc");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();


            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            String scannedContent = result.getContents();
            database.child("MarketID").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String barcodeValue = dataSnapshot.child("barcodenumber").getValue(String.class);
                        if (barcodeValue != null && barcodeValue.equals(scannedContent)) {
                            String DatabaseMarketName = dataSnapshot.child("name").getValue(String.class);
                            Long DatabaseMarketpostCode = dataSnapshot.child("postalcode").getValue(Long.class);
                            String PostaCode = String.valueOf(DatabaseMarketpostCode);
                            Toast.makeText(MainActivity.this, "QR Code matches database", Toast.LENGTH_SHORT).show();
                            MarketID.setText(scannedContent);
                            MarketName.setText(DatabaseMarketName);
                            MarketPostaCode.setText(PostaCode);
                            //MarketCode = DatabaseMarketName;

                            return; // If a match is found, no need to continue checking
                        }
                        else {
                            Toast.makeText(MainActivity.this, "QR Code does not match database", Toast.LENGTH_SHORT).show();
                        }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            Toast.makeText(this,"The QR Code is Empty..... ",Toast.LENGTH_LONG).show();
        }
    });

    public void ScanTheQR(View view) {
        ScanCode();
    }

    public void False(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert");
        builder.setMessage("ReScan The QR Code...");
        builder.setPositiveButton("ReScanning", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ScanCode();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void True(View view) {
        Intent i = new Intent(MainActivity.this,LogInPageActivity.class);
       // i.putExtra("MarketName", MarketCode);
        startActivity(i);
    }
}*/