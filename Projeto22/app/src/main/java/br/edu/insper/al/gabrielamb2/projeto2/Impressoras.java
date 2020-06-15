package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Impressoras extends AppCompatActivity {

    String velocidade;
    String horamaquina;
    HashMap<String,String> mapa_impressoras = new HashMap<>();
    String nova_linha;

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impressoras);


        final TextView valor_vel = findViewById(R.id.vel2);
        final TextView valor_hora = findViewById(R.id.hora2);

        final Spinner lista = findViewById(R.id.spinner);

        final Button botao_ler = findViewById(R.id.botao_ler);

        final Button botao_apagar = findViewById(R.id.botao_apagar);

        final File diretorio = getApplicationContext().getExternalFilesDir(null);
        final String filename = "impressoras.txt";

        File file = new File(diretorio + "/" + filename);

        if(file.exists()){

            String data = "";
            String buff = "";



            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true){
                try {
                    if (!((data = bufferedReader.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buff += data;
            }

            nova_linha = buff;

            final String[] array = buff.split("     ");

            for(String linha : array){
                String[] key_value = linha.split(":");
                mapa_impressoras.put(key_value[0],key_value[1]);
            }

            String nomes_impressoras = "";

            for(Map.Entry<String,String> set : mapa_impressoras.entrySet()){
                nomes_impressoras += set.getKey() + "  ";
            }

            String[] impressoras_array = nomes_impressoras.split("  ");
            ArrayAdapter<String> colocar_na_lista = new ArrayAdapter<String>(Impressoras.this, android.R.layout.simple_spinner_dropdown_item, impressoras_array);

            lista.setAdapter(colocar_na_lista);

            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            botao_ler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String impressora_escolhida = lista.getSelectedItem().toString();

                    for(Map.Entry<String,String> set : mapa_impressoras.entrySet()){
                        if (impressora_escolhida.equals(set.getKey())){
                            String valor_impressora = set.getValue();
                            String[] valores = valor_impressora.split(",");
                            velocidade = valores[0];
                            horamaquina = valores[1];
                        }
                    }
                    valor_vel.setText(velocidade);
                    valor_hora.setText(horamaquina);

                }
            });

            botao_apagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    File file = new File(diretorio + "/" + filename);

                    String impressora_escolhida = lista.getSelectedItem().toString();

                    String[] array_nova = nova_linha.split("     ");

                    String linha_nova = "";

                    for(String linha : array_nova){
                        if(!linha.contains(impressora_escolhida)){
                            linha_nova += linha + "     ";
                        }
                    }

                    FileOutputStream outputStream = null;
                    try {
                        outputStream= new FileOutputStream(file);
                        outputStream.write(linha_nova.getBytes());
                        showToast("Apagado");
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }else{
            showToast("Nenhuma Impressora Adcionada");
        }

    }
}
