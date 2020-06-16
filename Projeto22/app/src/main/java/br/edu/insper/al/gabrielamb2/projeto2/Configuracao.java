package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Configuracao extends AppCompatActivity {

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Toolbar toolbar = findViewById(R.id.mytoolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setLogo(R.drawable.logo_titulo);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao);

        final File diretorio = getApplicationContext().getExternalFilesDir(null);

        final String filename = "impressoras.txt";

        final EditText nome_impressora_et = findViewById(R.id.nome_impressora);
        final EditText num_velocidade_et = findViewById(R.id.num_velocidade);
        final EditText num_horamaquina_et = findViewById(R.id.num_horamaquina);

        Button botao_adc = findViewById(R.id.botao_adicionar);
        Button botao_ver = findViewById(R.id.botao_ver);


        botao_adc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome_impressora = nome_impressora_et.getText().toString();
                String num_velocidade = num_velocidade_et.getText().toString();
                String num_horamaquina = num_horamaquina_et.getText().toString();
                File file = new File(diretorio + "/" + filename);

                String linha = nome_impressora + ":" + num_velocidade + "," + num_horamaquina + "     " + "\n";

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

