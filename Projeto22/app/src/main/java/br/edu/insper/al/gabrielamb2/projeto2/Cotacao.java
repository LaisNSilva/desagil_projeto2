package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.lang.reflect.Array;
import java.net.URL;
import java.util.HashMap;

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

        final File diretorio = getApplicationContext().getFilesDir();

        final EditText cliente = findViewById(R.id.cliente);
        final EditText infill = findViewById(R.id.infill);
        final EditText shell = findViewById(R.id.shell);
        final EditText layer = findViewById(R.id.layer);
        final EditText mao_de_obra = findViewById(R.id.maodeobra);

        Button buttonArq = findViewById(R.id.escolher_arquivo);
        Button processar = findViewById(R.id.button_processar);
        Button enviar = findViewById(R.id.button_enviar);

        //Spinners (Impressoras e Filamentos)
        final Spinner materiais = findViewById(R.id.material);
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
        processar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text1 = cliente.getText().toString();
                String text2 = infill.getText().toString();
                String text3 = shell.getText().toString();
                String text4 = layer.getText().toString();
                String text5 = mao_de_obra.getText().toString();

                PartPriceConfig partPriceConfig = new PartPriceConfig();
                String material_escolhido = materiais.getSelectedItem().toString();

                String color = null;
                String densidade = null;

                if (material_escolhido == "ABS"){
                    color = partPriceConfig.getABS_color();
                    densidade = partPriceConfig.getABS_density();
                }
                else if (material_escolhido == "PLA"){
                    color = partPriceConfig.getPLA_color();
                    densidade = partPriceConfig.getPLA_density();
                }
                else if (material_escolhido == "PC"){
                    color = partPriceConfig.getPC_color();
                    densidade = partPriceConfig.getPC_density();
                }
                else if (material_escolhido == "Nylon"){
                    color = partPriceConfig.getNylon_color();
                    densidade = partPriceConfig.getNylon_density();
                }
                else if (material_escolhido == "LayWood"){
                    color = partPriceConfig.getLayWood_color();
                    densidade = partPriceConfig.getLayWood_density();
                }
                else if (material_escolhido == "BendLAY"){
                    color = partPriceConfig.getBendLAY_color();
                    densidade = partPriceConfig.getBendLAY_density();
                }
                else if (material_escolhido == "TPE"){
                    color = partPriceConfig.getTPE_color();
                    densidade = partPriceConfig.getTPE_density();
                }
                else if (material_escolhido == "SoftPLA"){
                    color = partPriceConfig.getSoftPLA_color();
                    densidade = partPriceConfig.getSoftPLA_density();
                }
                else if (material_escolhido == "HIPS"){
                    color = partPriceConfig.getHIPS_color();
                    densidade = partPriceConfig.getHIPS_density();
                }

                HashMap<String, Object>$_POST = new HashMap<>();

                $_POST.put("material", materiais.getSelectedItem().toString());
                $_POST.put("color", color);
                $_POST.put("layerHeight", layer.getText().toString());
                $_POST.put("infillPercentage", infill.getText().toString());

                if(supportRemoval.isSelected() == true){
                    $_POST.put("supportRemoval", true);
                }else{
                    $_POST.put("supportRemoval", false);
                }

                if (vaporPolishing.isSelected() == true){
                    $_POST.put("vaporPolishing", true);
                }else{
                    $_POST.put("vaporPolishing", false);
                }
                $_POST.put("shipping", "pickup");

                $_POST.put("rushPrinting",false);
                $_POST.put("density", densidade);

                String boundary = "------WebKitFormBoundary" + "1$#23gf784"

                HashMap<String, Object>$_FILES = new HashMap<>();

                RequestMulti requestMulti = new RequestMulti($_POST, $_FILES, boundary);
                String request = requestMulti.buildMultipartPost();


















                String texto_final = "Cliente: "+ text1 + "  " + "Infill: " + text2 + "  " + "Shell: "+ text3 + "  " + "Layer: "+ text4 + "  " + "Mão de Obra: " + text5 + "Peso: " + "  " + "Tempo: " + "  " + "Valor: ";
                texto_final = texto_final.replace("  ","\n");

                try{
                    String filename = "/cotacao_"+text1+".txt";
                    File file = new File(diretorio + filename);
                    System.out.println(file);

                    file.createNewFile();

                    FileOutputStream fout = new FileOutputStream(file, true);

                    fout.write(texto_final.getBytes());

                    fout.close();

                    showToast("Texto Adicionado");

                }catch (Exception e){
                    showToast(e.getMessage());
                }
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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
