package com.example.finalproject;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;


public class TeknolojiFragment extends Fragment {

    LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teknoloji, container, false);
        linearLayout = view.findViewById(R.id.linear_tekno_layout);
        getir();
        return view;
    }
    public void getir() {
        linearLayout.removeAllViews();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("hepsi").child("tekno");
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if (listResult.getItems().isEmpty() && listResult.getPrefixes().isEmpty()) {
                    TextView dosyaBos = new TextView(getActivity());
                    dosyaBos.setText("Seçilen Dosyanın içinde Resim Bulunmadı ");
                    linearLayout.addView(dosyaBos);
                }

                // Iterate through subdirectories (prefixes)
                for (StorageReference prefix : listResult.getPrefixes()) {
                    String subdirectoryName = prefix.getName();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items").child("tekno").child(subdirectoryName);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // dataSnapshot contains the data at the specified database reference
                            if (dataSnapshot.exists()) {
                                String mense = dataSnapshot.child("mense").getValue(String.class);
                                String yili = dataSnapshot.child("yili").getValue(String.class);
                                String sahibi = dataSnapshot.child("sahibi").getValue(String.class);

                                storageReference.child(subdirectoryName).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                    @Override
                                    public void onSuccess(ListResult listResult) {
                                        for (StorageReference Resim : listResult.getItems()) {
                                            String imageName = Resim.getName();
                                            StorageReference reference = storageReference.child(subdirectoryName).child(imageName);
                                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    LinearLayout subdirectoryLayout = new LinearLayout(getActivity());
                                                    subdirectoryLayout.setOrientation(LinearLayout.HORIZONTAL);

                                                    TextView subdirectoryTitle = new TextView(getActivity());
                                                    subdirectoryTitle.setText("Menşeisi : "+mense+"\n Kuruluş Yılı : "+ yili+"\n Sahibisi : "+sahibi);
                                                    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                                    );
                                                    titleParams.gravity = Gravity.CENTER;
                                                    titleParams.leftMargin = 170;

                                                    subdirectoryTitle.setLayoutParams(titleParams);

                                                    ImageView showImage = new ImageView(getActivity());
                                                    showImage.setLayoutParams(new LinearLayout.LayoutParams(
                                                            300,
                                                            300
                                                    ));
                                                    showImage.setPadding(10, 10, 10, 10);

                                                    Glide.with(getActivity()).load(uri).into(showImage);

                                                    View ayiran = new View(getActivity());
                                                    ayiran.setLayoutParams(new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.WRAP_CONTENT,3
                                                    ));
                                                    ayiran.setBackgroundResource(R.color.black);

                                                    subdirectoryLayout.addView(showImage);
                                                    subdirectoryLayout.addView(subdirectoryTitle);
                                                    linearLayout.addView(subdirectoryLayout);
                                                    linearLayout.addView(ayiran);

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getActivity(), "Resimlerin Url Verini Getirirken Hata Olustu ", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors here
                            Toast.makeText(getActivity(), "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Dosyalari Getirirken Hata Olustu ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}