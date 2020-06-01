package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class Cotacao extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "Uri";
    private void showToast(String text) {

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacao);

        //TextView arquivoPeca = findViewById(R.id.peca);

        final EditText cliente = findViewById(R.id.cliente);
        final EditText infill = findViewById(R.id.infill);
        final EditText shell = findViewById(R.id.shell);
        final EditText layer = findViewById(R.id.layer);
        final EditText mao_de_obra = findViewById(R.id.maodeobra);

        Button buttonArq = findViewById(R.id.escolher_arquivo);
        Button enviar = findViewById(R.id.button_enviar);


        //Spinners (Impressoras e Filamentos)

        Spinner materiais = findViewById(R.id.material);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.materiais, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materiais.setAdapter(adapter1);

        Spinner impressoras = findViewById(R.id.impressora);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.impressoras, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        impressoras.setAdapter(adapter2);

        //Botao para Importar um arquivo
        buttonArq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent pasta = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                pasta.addCategory(Intent.CATEGORY_OPENABLE);

                pasta.setType("*/*");

                startActivityForResult(pasta, READ_REQUEST_CODE);

            }
        });

        //Botao para Escrever um arquivo txt
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text1 = cliente.getText().toString();
                String text2 = infill.getText().toString();
                String text3 = shell.getText().toString();
                String text4 = layer.getText().toString();
                String text5 = mao_de_obra.getText().toString();

                try{
                    File file = new File("/sdcard/myfile.txt");
                    file.createNewFile();

                    FileOutputStream fout = new FileOutputStream(file, true);

                    fout.write(text1.getBytes());
                    fout.write(text2.getBytes());
                    fout.write(text3.getBytes());
                    fout.write(text4.getBytes());
                    fout.write(text5.getBytes());
                    fout.close();

                    showToast("Texto Adicionado");

                }catch (Exception e){
                    showToast(e.getMessage());
                }

            }
        });

    }
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri uri = resultData.getData();
            arquivoPeca.setText(uri.toString());

        }
    }

     */

}
