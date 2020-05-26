package br.edu.insper.al.gabrielamb2.projetodelta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {
    private Activity activity;

    private void showToast(String text) {

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button localizar = findViewById(R.id.localizar);



        localizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText cepApi = findViewById(R.id.cep);
                String c = cepApi.getText().toString();
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                    } else {
                        Localizar localizar1 = Cep.localizar(c);
                        TextView lugar = findViewById(R.id.lugar);
                        lugar.setText(localizar1.getLocalidade());
                    }
                } catch (Exception e) {
                    showToast(e.getMessage());
                    System.out.println(e.getMessage());

                }
            }

        });
    }

    @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
        {
            switch (requestCode) {
                case 1: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //carregar localização
                        EditText cepApi = findViewById(R.id.cep);
                        String c = cepApi.getText().toString();
                        Localizar localizar1 = null;
                        try {
                            localizar1 = Cep.localizar(c);
                        } catch (JSONException | MalformedURLException e) {
                            showToast(e.getMessage());
                        }
                        TextView lugar = findViewById(R.id.lugar);
                        lugar.setText(localizar1.getLocalidade());
                    } else {
                        showToast("Não vai funcionar!!!");
                    }
                    return;

                }

            }
        }


}