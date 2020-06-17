package br.edu.insper.al.gabrielamb2.projeto2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;

public class Configuracao extends AppCompatActivity {

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao);

        final File diretorio = getApplicationContext().getExternalFilesDir(null);

        final String filename = "impressoras.txt";

        final EditText nome_impressora_et = findViewById(R.id.nome_impressora);
        final EditText num_velocidade_et = findViewById(R.id.num_velocidade);
        final EditText num_horamaquina_et = findViewById(R.id.num_horamaquina);

        Button botao_adc = findViewById(R.id.botao_adicionar);
        Button botao_ver = findViewById(R.id.botao_ver);

        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo_titulo);


        botao_adc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome_impressora = nome_impressora_et.getText().toString();
                String num_velocidade = num_velocidade_et.getText().toString();
                String num_horamaquina = num_horamaquina_et.getText().toString();
                File file = new File(diretorio + "/" + filename);

                String novo_num_velocidade;
                String novo_num_horamaquina;

                if(num_velocidade.contains(",")){
                    novo_num_velocidade = num_velocidade.replace(",",".");
                }else{
                    novo_num_velocidade = num_velocidade;
                }

                if(num_horamaquina.contains(",")){
                    novo_num_horamaquina = num_horamaquina.replace(",",".");
                }else {
                    novo_num_horamaquina = num_horamaquina;
                }

                String linha = nome_impressora + ":" + novo_num_velocidade + "," + novo_num_horamaquina + "     " + "\n";

                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    if(file.exists()){
                        FileOutputStream outputStream = null;
                        try {
                            outputStream= new FileOutputStream(file,true);
                            outputStream.write(linha.getBytes());
                            showToast("Adicionado");
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        FileOutputStream outputStream = null;
                        try {
                            outputStream= new FileOutputStream(file);
                            outputStream.write(linha.getBytes());
                            showToast("Adicionado");
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                nome_impressora_et.setText("");
                num_horamaquina_et.setText("");
                num_velocidade_et.setText("");
            }
        });

        final Intent impressoras = new Intent(this, Impressoras.class);


        botao_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(impressoras);
            }
        });

    }

}

