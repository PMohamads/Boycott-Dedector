package com.example.finalproject;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KozmatikFragment extends Fragment {

    LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kozmatik, container, false);
        linearLayout = view.findViewById(R.id.linear_kozmatik_layout);
        getir();
        return view;

    }
    public void getir() {
        linearLayout.removeAllViews();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("items").child("kozmatik");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    TextView dosyaBos = new TextView(getActivity());
                    dosyaBos.setText("Veri Bulunamadı");
                    linearLayout.addView(dosyaBos);
                    return;
                }

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String marketName = itemSnapshot.child("MarketName").getValue(String.class);
                    String imageSrc = itemSnapshot.child("ImageSrc").getValue(String.class);


                    LinearLayout subdirectoryLayout = new LinearLayout(getActivity());
                    subdirectoryLayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams subdirectoryLayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    subdirectoryLayout.setPadding(15,0,0,0);
                    subdirectoryLayoutParams.setMargins(0, 18, 0, 0);
                    subdirectoryLayout.setLayoutParams(subdirectoryLayoutParams);
                    subdirectoryLayout.setBackgroundResource(R.drawable.border);

                    TextView subdirectoryTitle = new TextView(getActivity());
                    subdirectoryTitle.setText("Market Name: " + marketName);
                    subdirectoryTitle.setTypeface(null, Typeface.BOLD);
                    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    titleParams.gravity = Gravity.CENTER;
                    titleParams.leftMargin = 170;

                    subdirectoryTitle.setLayoutParams(titleParams);
                    subdirectoryTitle.setTextColor(getResources().getColor(R.color.darkblue));

                    ImageView showImage = new ImageView(getActivity());
                    showImage.setLayoutParams(new LinearLayout.LayoutParams(
                            300,
                            340
                    ));
                    showImage.setPadding(10, 10, 10, 10);

                    // Load image using Glide
                    Glide.with(getActivity()).load(Uri.parse(imageSrc)).into(showImage);

                    View ayiran = new View(getActivity());
                    ayiran.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, 3
                    ));
                    ayiran.setBackgroundResource(R.color.black);

                    subdirectoryLayout.addView(showImage);
                    subdirectoryLayout.addView(subdirectoryTitle);
                    linearLayout.addView(subdirectoryLayout);
                    linearLayout.addView(ayiran);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}