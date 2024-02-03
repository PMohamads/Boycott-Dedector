package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class scanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }

    /*private void startScanning() {
        // Zxing kütüphanesinden IntentIntegrator sınıfını kullanarak tarama işlemini başlat
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true); // Ekran rotasyonunu kilitleme
        integrator.setBeepEnabled(true);

        // Tarama sonucunu almak için onActivityResult metodu çağrılır
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Zxing kütüphanesi tarafından sağlanan sonuçları işleme
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Tarama iptal edildiğinde
                Toast.makeText(this, "Tarama iptal edildi", Toast.LENGTH_SHORT).show();
            } else {
                // Tarama başarılı olduğunda
                String scannedData = result.getContents();
                Toast.makeText(this, "Tarama Sonucu: " + scannedData, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void scannn(View view) {
        startScanning();
    }*/
}