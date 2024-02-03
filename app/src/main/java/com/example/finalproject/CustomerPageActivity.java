package com.example.finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class CustomerPageActivity extends AppCompatActivity {

    TextView CustomerName;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdaptor viewPagerAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);
        CustomerName = findViewById(R.id.customername);
        tabLayout = findViewById(R.id.tablelayout);
        viewPager2 = findViewById(R.id.viewpager);
        viewPagerAdaptor = new ViewPagerAdaptor(this);
        viewPager2.setAdapter(viewPagerAdaptor);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });


        Intent i2 = getIntent();
        String Name = i2.getStringExtra("Name");
        String SurName = i2.getStringExtra("SurName");
        if( SurName == null || Name == null){
            CustomerName.setText("Visitor");
        }
        else{
            CustomerName.setText(Name + " " + SurName);
        }
    }

    private void ScanMethod(){
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setBarcodeImageEnabled(true);
        barLaucher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() != null) {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("items");
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot grandChildSnapshot : childSnapshot.getChildren()) {
                            String katagori = childSnapshot.child("katagori").getValue(String.class);
                            String barcode = grandChildSnapshot.child("barcode").getValue(String.class);

                            if (barcode != null && barcode.equals(result.getContents())) {
                                Toast.makeText(CustomerPageActivity.this, "Items Foundeed", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerPageActivity.this);
                                builder.setTitle("Boykot Ürünüdür : ");
                                builder.setMessage("Ürünün Katagorisini : " + katagori);
                                builder.setPositiveButton("Hemen Alternatif Ürünleri Göster :", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent i = new Intent(CustomerPageActivity.this ,Teknoloji.class);
                                        startActivity(i);
                                    }
                                });
                                builder.setNegativeButton("Yok Sağol : ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                            } else {
                                //Toast.makeText(CustomerPageActivity.this, "Items does not Foundeed", Toast.LENGTH_SHORT).show();
                                   /* AlertDialog.Builder builder = new AlertDialog.Builder(CustomerPageActivity.this);
                                    builder.setTitle("Bilgi : ");
                                    builder.setMessage("Ürünü boykot edilen ürünler arasında yer almıyor \n Ürünün Katagorisini : " + katagori);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent i = new Intent(CustomerPageActivity.this ,Teknoloji.class);
                                            startActivity(i);
                                        }
                                    }).show();*/
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors that may occur while retrieving the data
                    Log.e("DatabaseError", "Error: " + databaseError.getMessage());
                }
            });
            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();




            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
        }
    });

    public void ScanTheBARCODE(View view) {
        ScanMethod();
    }

    public void sikayetver(View view) {
    }
}