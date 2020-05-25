package br.edu.insper.al.laisns.projeto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

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
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
                    }
                    else {
                        Localizar localizar1 = Cep.localizar(c);
                        TextView lugar = findViewById(R.id.lugar);
                        lugar.setText(localizar1.getLocalidade());
                    }
                } catch (JSONException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
                {
                    switch (requestCode) {
                        case 1: {
                            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                //carregar localização
                                EditText cepApi = findViewById(R.id.cep);
                                String c = cepApi.getText().toString();
                                Localizar localizar1 = Cep.localizar(c);
                                TextView lugar = findViewById(R.id.lugar);
                                lugar.setText(localizar1.getLocalidade());
                            } else {
                                Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                            }
                            return;

                        }

                    }
                }
        });




    }
}
