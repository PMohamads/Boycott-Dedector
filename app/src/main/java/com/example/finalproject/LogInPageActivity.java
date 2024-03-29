package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInPageActivity extends AppCompatActivity {

    EditText Email,Sifre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        Email = findViewById(R.id.email);
        Sifre = findViewById(R.id.sifre);
    }

    public void SignningUp(View view) {
        startActivity(new Intent(LogInPageActivity.this,NewCustomer.class));
    }

    public void Loggingin(View view) {
        String GirilenEmail = Email.getText().toString().trim();
        String GirilenSifre = Sifre.getText().toString().trim();
        if(TextUtils.isEmpty(GirilenEmail) || TextUtils.isEmpty(GirilenSifre)){
            Toast.makeText(this,"Lütfen Boşlukları Doldurunuz...",Toast.LENGTH_LONG).show();
            return;
        }
        DatabaseReference veritabani = FirebaseDatabase.getInstance().getReference();
        String modifyedemail = GirilenEmail.replace(".",",");
        veritabani.child("kullanicilar").child(modifyedemail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String KayitliSifre = snapshot.child("password").getValue(String.class);
                    if (KayitliSifre.equals(GirilenSifre)){
                        String KayitliAdi = snapshot.child("name").getValue(String.class);
                        String KayitliSoyAdi = snapshot.child("surname").getValue(String.class);
                        Toast.makeText(LogInPageActivity.this,"HoşGeldiniz",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LogInPageActivity.this,CustomerPageActivity.class);
                        i.putExtra("Name",KayitliAdi);
                        i.putExtra("SurName",KayitliSoyAdi);
                        i.putExtra("Email",modifyedemail);
                        startActivity(i);

                    }
                    else {
                        Toast.makeText(LogInPageActivity.this,"Girdiğniz Şifre Yanlıştır Yeniden Deneyiniz",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogInPageActivity.this);
                    builder.setTitle("Dikkat");
                    builder.setMessage("Girilen Bilgiler Kayıtlı Bir Hesaba Eşleşmedi istersiniz Yeniden Hesab Oluşturunuz Veya Yeniden Deneyiniz");
                    builder.setPositiveButton("SignUp", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(LogInPageActivity.this,NewCustomer.class));
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Yeniden Dene", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogInPageActivity.this,"Hata Oluştu Yeniden Deneyiniz .",Toast.LENGTH_LONG).show();
            }
        });
    }
}