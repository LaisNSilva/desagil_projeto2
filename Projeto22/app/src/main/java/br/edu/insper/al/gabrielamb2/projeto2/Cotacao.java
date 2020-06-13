package br.edu.insper.al.gabrielamb2.projeto2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Cotacao extends AppCompatActivity{
    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "Uri";
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    String arquivo;
    private String requisição;
    double velocidade;

//  ------- Variaveis Excel ------------------
    Workbook wb = new HSSFWorkbook();
    Cell cell = null;
    CellStyle cellStyle = wb.createCellStyle();
    Sheet sheet = wb.createSheet("Orçamentos Antigos");
    int linhas = 0;


    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacao);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo_titulo);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        final File diretorio = getApplicationContext().getExternalFilesDir(null);

        final EditText cliente = findViewById(R.id.cliente);
        final EditText infill = findViewById(R.id.infill);
        final EditText layer = findViewById(R.id.layer);
        final EditText mao_de_obra = findViewById(R.id.maodeobra);

        final TextView peso = findViewById(R.id.peso);
        final TextView tempo = findViewById(R.id.tempo);
        final TextView valor = findViewById(R.id.valor);

        Button buttonArq = findViewById(R.id.escolher_arquivo);
        Button processar = findViewById(R.id.button_processar);
        Button enviar = findViewById(R.id.button_enviar);

//      =======================Ler o nome e velocidade das impressoras===================================

        String buff = "";
        String data = "";

        final String filename_impressoras = "impressoras.txt";

        File file_impressoras = new File(diretorio + "/" + filename_impressoras);

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file_impressoras);
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

//      -------------------------------Hashmap das Impressoras---------------------------------------------
        HashMap<String,Double> mapa_impressoras = new HashMap<>();
        String[] array = buff.split("  ");

        for(String linha : array){
            String[] key_value = linha.split(":");
            mapa_impressoras.put(key_value[0],Double.parseDouble(key_value[1]));
        }

        String nomes_impressoras = "";

        for(Map.Entry<String,Double> set : mapa_impressoras.entrySet()){
            nomes_impressoras += set.getKey() + "  ";
        }

        String[] impressoras_array = nomes_impressoras.split("  ");
        ArrayAdapter<String> colocar_na_lista = new ArrayAdapter<String>(Cotacao.this, android.R.layout.simple_spinner_dropdown_item, impressoras_array);

        final Spinner impressoras = findViewById(R.id.impressora);
        impressoras.setAdapter(colocar_na_lista);

//      ------------------------------Velocidade de acordo com a Impressora------------------------------

        String impressora_escolhida = impressoras.getSelectedItem().toString();

        for(Map.Entry<String,Double> set : mapa_impressoras.entrySet()){
            if (impressora_escolhida.equals(set.getKey())){
                velocidade = set.getValue();
            }
        }

        final Spinner materiais = findViewById(R.id.material);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.materiais, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materiais.setAdapter(adapter1);

//      ===============================Importar um aqruivo================================================
        buttonArq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent pasta = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                pasta.addCategory(Intent.CATEGORY_OPENABLE);

                pasta.setType("*/*");

                startActivityForResult(pasta, READ_REQUEST_CODE);

            }
        });

//      ====================== API + TXT + Excel =============================================================
        processar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PartPriceConfig partPriceConfig = new PartPriceConfig();
                String material_escolhido = materiais.getSelectedItem().toString();

                String color = null;
                String densidade = null;

                if (material_escolhido.equals("ABS")){
                    color = partPriceConfig.getABS_color();
                    densidade = partPriceConfig.getABS_density();
                }
                else if (material_escolhido.equals("PLA")){
                    color = partPriceConfig.getPLA_color();
                    densidade = partPriceConfig.getPLA_density();
                }
                else if (material_escolhido.equals("PC")){
                    color = partPriceConfig.getPC_color();
                    densidade = partPriceConfig.getPC_density();
                }
                else if (material_escolhido.equals("Nylon")){
                    color = partPriceConfig.getNylon_color();
                    densidade = partPriceConfig.getNylon_density();
                }
                else if (material_escolhido.equals("LayWood")){
                    color = partPriceConfig.getLayWood_color();
                    densidade = partPriceConfig.getLayWood_density();
                }
                else if (material_escolhido.equals("BendLAY")){
                    color = partPriceConfig.getBendLAY_color();
                    densidade = partPriceConfig.getBendLAY_density();
                }
                else if (material_escolhido.equals("TPE")){
                    color = partPriceConfig.getTPE_color();
                    densidade = partPriceConfig.getTPE_density();
                }
                else if (material_escolhido.equals("SoftPLA")){
                    color = partPriceConfig.getSoftPLA_color();
                    densidade = partPriceConfig.getSoftPLA_density();
                }
                else if (material_escolhido.equals("HIPS")){
                    color = partPriceConfig.getHIPS_color();
                    densidade = partPriceConfig.getHIPS_density();
                }

                HashMap<String, Object>$_POST = new HashMap<>();

                $_POST.put("material", materiais.getSelectedItem().toString());
                $_POST.put("color", color);
                $_POST.put("layerHeight", layer.getText().toString());
                $_POST.put("infillPercentage", infill.getText().toString());


                $_POST.put("supportRemoval", false);

                $_POST.put("vaporPolishing", false);

                $_POST.put("shipping", "delivery");

                $_POST.put("rushPrinting",false);
                $_POST.put("density", densidade);

                String config = "YTo3OntzOjk6Im1hdGVyaWFscyI7YTo5OntzOjM6IkFCUyI7YTo1OntzOjg6ImZ1bGx" +
                        "OYW1lIjtzOjMxOiJBY3J5bG9uaXRyaWxlIEJ1dGFkaWVuZSBTdHlyZW5lIjtzOjU6InByaWNlIj" +
                        "thOjI6e3M6NjoiYW1vdW50IjtkOjAuMjtzOjQ6InVuaXQiO3M6NToiVVNEL2ciO31zOjE4OiJjY" +
                        "W5CZVZhcG9yUG9saXNoZWQiO2I6MTtzOjc6ImRlbnNpdHkiO2E6Mjp7czo2OiJhbW91bnQiO2Q6" +
                        "MS4wNDtzOjQ6InVuaXQiO3M6NjoiZy9jbV4zIjt9czo2OiJjb2xvcnMiO2E6MTI6e2k6MDtzOjc" +
                        "6IiMwMDAwMDAiO2k6MTtzOjc6IiNGRkZGRkYiO2k6MjtzOjc6IiNGRkZBRTAiO2k6MztzOjc6Ii" +
                        "NGRjBGMEYiO2k6NDtzOjc6IiNGRjgzMjQiO2k6NTtzOjc6IiNGRkE4QzgiO2k6NjtzOjc6IiNGN" +
                        "0ZGMDAiO2k6NztzOjc6IiM3MEZGMzMiO2k6ODtzOjc6IiMxNDBBQTMiO2k6OTtzOjc6IiM4OTIx" +
                        "RkYiO2k6MTA7czo3OiIjOTI5MUI1IjtpOjExO3M6NzoiIzg3NTkzRSI7fX1zOjM6IlBMQSI7YTo" +
                        "1OntzOjg6ImZ1bGxOYW1lIjtzOjE1OiJQb2x5bGFjdGljIGFjaWQiO3M6NToicHJpY2UiO2E6Mj" +
                        "p7czo2OiJhbW91bnQiO2Q6MC4yNTtzOjQ6InVuaXQiO3M6NToiVVNEL2ciO31zOjE4OiJjYW5CZ" +
                        "VZhcG9yUG9saXNoZWQiO2I6MTtzOjc6ImRlbnNpdHkiO2E6Mjp7czo2OiJhbW91bnQiO2Q6MS4y" +
                        "NTtzOjQ6InVuaXQiO3M6NjoiZy9jbV4zIjt9czo2OiJjb2xvcnMiO2E6MTI6e2k6MDtzOjc6IiM" +
                        "wMDAwMDAiO2k6MTtzOjc6IiNGRkZGRkYiO2k6MjtzOjc6IiNGRkZBRTAiO2k6MztzOjc6IiNGRj" +
                        "BGMEYiO2k6NDtzOjc6IiNGRjgzMjQiO2k6NTtzOjc6IiNGRkE4QzgiO2k6NjtzOjc6IiNGN0ZGM" +
                        "DAiO2k6NztzOjc6IiM3MEZGMzMiO2k6ODtzOjc6IiMxNDBBQTMiO2k6OTtzOjc6IiM4OTIxRkYi" +
                        "O2k6MTA7czo3OiIjOTI5MUI1IjtpOjExO3M6NzoiIzg3NTkzRSI7fX1zOjI6IlBDIjthOjU6e3M" +
                        "6ODoiZnVsbE5hbWUiO3M6MTM6IlBvbHljYXJib25hdGUiO3M6NToicHJpY2UiO2E6Mjp7czo2Oi" +
                        "JhbW91bnQiO2Q6MC42O3M6NDoidW5pdCI7czo1OiJVU0QvZyI7fXM6MTg6ImNhbkJlVmFwb3JQb" +
                        "2xpc2hlZCI7YjoxO3M6NzoiZGVuc2l0eSI7YToyOntzOjY6ImFtb3VudCI7ZDoxLjI7czo0OiJ1" +
                        "bml0IjtzOjY6ImcvY21eMyI7fXM6NjoiY29sb3JzIjthOjEyOntpOjA7czo3OiIjMDAwMDAwIjt" +
                        "pOjE7czo3OiIjRkZGRkZGIjtpOjI7czo3OiIjRkZGQUUwIjtpOjM7czo3OiIjRkYwRjBGIjtpOj" +
                        "Q7czo3OiIjRkY4MzI0IjtpOjU7czo3OiIjRkZBOEM4IjtpOjY7czo3OiIjRjdGRjAwIjtpOjc7c" +
                        "zo3OiIjNzBGRjMzIjtpOjg7czo3OiIjMTQwQUEzIjtpOjk7czo3OiIjODkyMUZGIjtpOjEwO3M6" +
                        "NzoiIzkyOTFCNSI7aToxMTtzOjc6IiM4NzU5M0UiO319czo1OiJOeWxvbiI7YTo1OntzOjg6ImZ" +
                        "1bGxOYW1lIjtOO3M6NToicHJpY2UiO2E6Mjp7czo2OiJhbW91bnQiO2Q6MC4zNTtzOjQ6InVuaX" +
                        "QiO3M6NToiVVNEL2ciO31zOjE4OiJjYW5CZVZhcG9yUG9saXNoZWQiO2I6MDtzOjc6ImRlbnNpd" +
                        "HkiO2E6Mjp7czo2OiJhbW91bnQiO2Q6MS4yNTtzOjQ6InVuaXQiO3M6NjoiZy9jbV4zIjt9czo2" +
                        "OiJjb2xvcnMiO2E6Njp7aTowO3M6NzoiIzAwMDAwMCI7aToxO3M6NzoiI0ZGRkZGRiI7aToyO3M" +
                        "6NzoiI0ZGMEYwRiI7aTozO3M6NzoiIzcwRkYzMyI7aTo0O3M6NzoiIzE0MEFBMyI7aTo1O3M6NT" +
                        "oiY2xlYXIiO319czo3OiJMYXlXb29kIjthOjU6e3M6ODoiZnVsbE5hbWUiO047czo1OiJwcmljZ" +
                        "SI7YToyOntzOjY6ImFtb3VudCI7ZDowLjg7czo0OiJ1bml0IjtzOjU6IlVTRC9nIjt9czoxODoi" +
                        "Y2FuQmVWYXBvclBvbGlzaGVkIjtiOjA7czo3OiJkZW5zaXR5IjthOjI6e3M6NjoiYW1vdW50Ijt" +
                        "kOjEuMDU7czo0OiJ1bml0IjtzOjY6ImcvY21eMyI7fXM6NjoiY29sb3JzIjthOjE6e2k6MDtzOj" +
                        "c6IiNGRkZGRkYiO319czo3OiJCZW5kTEFZIjthOjU6e3M6ODoiZnVsbE5hbWUiO047czo1OiJwc" +
                        "mljZSI7YToyOntzOjY6ImFtb3VudCI7ZDowLjU7czo0OiJ1bml0IjtzOjU6IlVTRC9nIjt9czox" +
                        "ODoiY2FuQmVWYXBvclBvbGlzaGVkIjtiOjE7czo3OiJkZW5zaXR5IjthOjI6e3M6NjoiYW1vdW5" +
                        "0IjtkOjEuMDI7czo0OiJ1bml0IjtzOjY6ImcvY21eMyI7fXM6NjoiY29sb3JzIjthOjE6e2k6MD" +
                        "tzOjc6IiM4NzU5M0UiO319czozOiJUUEUiO2E6NTp7czo4OiJmdWxsTmFtZSI7czoyMzoiVGhlc" +
                        "m1vcGxhc3RpYyBlbGFzdG9tZXIiO3M6NToicHJpY2UiO2E6Mjp7czo2OiJhbW91bnQiO2Q6MC42" +
                        "O3M6NDoidW5pdCI7czo1OiJVU0QvZyI7fXM6MTg6ImNhbkJlVmFwb3JQb2xpc2hlZCI7YjowO3M" +
                        "6NzoiZGVuc2l0eSI7YToyOntzOjY6ImFtb3VudCI7ZDoxLjE7czo0OiJ1bml0IjtzOjY6ImcvY2" +
                        "1eMyI7fXM6NjoiY29sb3JzIjthOjE6e2k6MDtzOjU6ImNsZWFyIjt9fXM6NzoiU29mdFBMQSI7Y" +
                        "To1OntzOjg6ImZ1bGxOYW1lIjtOO3M6NToicHJpY2UiO2E6Mjp7czo2OiJhbW91bnQiO2Q6MC41" +
                        "O3M6NDoidW5pdCI7czo1OiJVU0QvZyI7fXM6MTg6ImNhbkJlVmFwb3JQb2xpc2hlZCI7YjowO3M" +
                        "6NzoiZGVuc2l0eSI7YToyOntzOjY6ImFtb3VudCI7ZDoxLjE1O3M6NDoidW5pdCI7czo2OiJnL2" +
                        "NtXjMiO31zOjY6ImNvbG9ycyI7YTo0OntpOjA7czo3OiIjMDAwMDAwIjtpOjE7czo3OiIjRkYwR" +
                        "jBGIjtpOjI7czo3OiIjMTQwQUEzIjtpOjM7czo3OiIjRkZGRkZGIjt9fXM6NDoiSElQUyI7YTo1" +
                        "OntzOjg6ImZ1bGxOYW1lIjtzOjIzOiJIaWdoLWltcGFjdCBQb2x5c3R5cmVuZSI7czo1OiJwcml" +
                        "jZSI7YToyOntzOjY6ImFtb3VudCI7ZDowLjI7czo0OiJ1bml0IjtzOjU6IlVTRC9nIjt9czoxOD" +
                        "oiY2FuQmVWYXBvclBvbGlzaGVkIjtiOjE7czo3OiJkZW5zaXR5IjthOjI6e3M6NjoiYW1vdW50I" +
                        "jtkOjEuMDY7czo0OiJ1bml0IjtzOjY6ImcvY21eMyI7fXM6NjoiY29sb3JzIjthOjE6e2k6MDtz" +
                        "Ojc6IiNGRkZBRTAiO319fXM6MTI6InByaW50aW5nQ29zdCI7YToyOntzOjY6ImFtb3VudCI7czo" +
                        "0OiI0LjAwIjtzOjQ6InVuaXQiO3M6ODoiVVNEL2hvdXIiO31zOjY6ImFkZE9ucyI7YTozOntzOj" +
                        "I0OiJzdXBwb3J0UmVtb3ZhbE11bHRpcGxpZXIiO2Q6MS4zMztzOjI0OiJ2YXBvclBvbGlzaGluZ" +
                        "011bHRpcGxpZXIiO2Q6MS4yNTtzOjIyOiJydXNoUHJpbnRpbmdNdWx0aXBsaWVyIjtkOjEuNTt9" +
                        "czoxMzoiZGVsaXZlcnlDb3N0cyI7YToyOntzOjQ6ImJhc2UiO2E6Mjp7czo2OiJhbW91bnQiO2Q" +
                        "6NS44O3M6NDoidW5pdCI7czozOiJVU0QiO31zOjExOiJ3ZWlnaHRQcmljZSI7YToyOntzOjY6Im" +
                        "Ftb3VudCI7ZDowLjAxO3M6NDoidW5pdCI7czo1OiJVU0QvZyI7fX1zOjEyOiJzbGljZXJQYXJhb" +
                        "XMiO2E6MTp7czo3OiJzbGljZXJzIjthOjE6e2k6MDtzOjQ6ImN1cmEiO319czoxMjoibGF5ZXJI" +
                        "ZWlnaHRzIjthOjM6e3M6NzoiZGVmYXVsdCI7YToyOntzOjY6ImFtb3VudCI7czo1OiIwLjI1NCI" +
                        "7czo0OiJ1bml0IjtzOjI6Im1tIjt9czozOiJtaW4iO2E6Mjp7czo2OiJhbW91bnQiO2Q6MC4wNz" +
                        "U7czo0OiJ1bml0IjtzOjI6Im1tIjt9czozOiJtYXgiO2E6Mjp7czo2OiJhbW91bnQiO2Q6MC40O" +
                        "3M6NDoidW5pdCI7czoyOiJtbSI7fX1zOjExOiJwcmludFNwZWVkcyI7YToxOntzOjc6ImRlZmF1" +
                        "bHQiO2E6Mjp7czo2OiJhbW91bnQiO2k6NTA7czo0OiJ1bml0IjtzOjQ6Im1tL3MiO319fQ%3D%3D";


                $_POST.put("configFile",config);

                String boundary = "------WebKitFormBoundary" + "b511c710cb33734f";

                HashMap<String, HashMap<String, String>> $_FILES = new HashMap<>();
                HashMap<String, String>values_files = new HashMap<>();

                values_files.put("name", "Cube_3d_printing_sample.stl");
                values_files.put("type", "model/stl");
                try{
                    values_files.put("tmp_name", arquivo);

                }catch (Exception e){
                    showToast("Arquivo vazio!");

                }
                $_FILES.put("stlFiles[]", values_files);

                RequestMulti requestMulti = new RequestMulti($_POST, $_FILES, boundary);
                String output_request = requestMulti.buildMultipartPost($_POST, $_FILES, boundary);
                System.out.println(output_request);

                try {
                    requisição = requestMulti.Request(output_request, boundary);
                    System.out.println("requisicao "+requisição);
                    requestMulti.getJsonTempo(requisição);
                    requestMulti.getJsonPeso(requisição);
                    tempo.setText(requestMulti.getJsonTempo(requisição));
                    peso.setText(requestMulti.getJsonPeso(requisição));

                } catch (IOException e) {
                    e.printStackTrace();
                }

//              -------------------------------- TXT -----------------------------------------------------

                String cliente_ = cliente.getText().toString();
                String infill_ = infill.getText().toString();
                String layer_ = layer.getText().toString();
                String impressora = impressoras.getSelectedItem().toString();
                String maodeobra = mao_de_obra.getText().toString();
                String peso_ = peso.getText().toString();
                String tempo_ = tempo.getText().toString();
                String valor_ = valor.getText().toString();

                String texto_final = "Cliente: "+ cliente_ + "  " + "Infill: " + infill_ + "  " + "Layer: "+ layer_ + "  " + "Impressora: " + impressora + "  "
                        + "Material: " + material_escolhido + "  "+ "Mão de Obra: " + maodeobra + "  " + "Peso: " + peso_ + "  " + "Tempo: " +  tempo_ + "  " +"Valor: " + valor_;

                texto_final = texto_final.replace("  ","\n");

                String filename = "cotacao_"+cliente_+".txt";

                File file = new File(diretorio + "/" + filename);

                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    FileOutputStream outputStream = null;
                    try {
                        outputStream= new FileOutputStream(file);
                        outputStream.write(texto_final.getBytes());
                        showToast("Arquivo Criado");
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }

                Cliente cliente = new Cliente(cliente_,infill.getText().toString(),layer.getText().toString(),impressoras.getSelectedItem().toString(),materiais.getSelectedItem().toString(),mao_de_obra.getText().toString(), peso_, tempo_);
                mDatabase.child("users").child(String.valueOf(new Date().getTime())).setValue(cliente);
                getOrcamentos();

            }
        });

//      ===========================Compartilhar + Criar EXCEL======================================

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cliente_ = cliente.getText().toString();
                String filename = "cotacao_"+cliente_+".txt";
                String path = diretorio+filename;
                File minhaFile = new File(path);

                try{
                    Uri uri = FileProvider.getUriForFile(Cotacao.this, "br.edu.insper.al.gabrielamb2.projeto2.fileprovider", minhaFile);
                    Intent share = new Intent();

                    share.setAction(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(share);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

                Row row = sheet.createRow(0);
                cell=row.createCell(0);
                cell.setCellValue("Cliente");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(1);
                cell.setCellValue("Impressora");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(2);
                cell.setCellValue("Infill");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(3);
                cell.setCellValue("Layer");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(4);
                cell.setCellValue("Mão de obra");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(5);
                cell.setCellValue("Materiais");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(6);
                cell.setCellValue("Peso");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(7);
                cell.setCellValue("Tempo");
                cell.setCellStyle(cellStyle);

                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File file = new File(getExternalFilesDir(null),"orcamentoantigo.xls");
                    FileOutputStream outputStream = null;
                    try {
                        outputStream=new FileOutputStream(file);
                        wb.write(outputStream);
                        Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();

                        Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
                        try {
                            assert outputStream != null;
                            outputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                } else {
                    showToast("Nao foi possivel acessar o arquivo");
                }
            }
        });
    }

    private void getOrcamentos() {
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {

                    // O método getValue recebe como parâmetro uma
                    // classe Java que representa o tipo de dado
                    // que você acredita estar lá. Se você errar,
                    // esse método vai lançar uma DatabaseException.
                    getClientes((Map<String, Object>)dataSnapshot.getValue());

                } else {
                    System.out.println("DEU RUIM");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Ouve um problema de conexão",Toast.LENGTH_LONG).show();
            }
        });    }

    private void getClientes(Map<String, Object> cotacoes){

        LinkedList<String> cotcaoinidivual = new LinkedList<>();
        for(Map.Entry<String,Object>entry: cotacoes.entrySet()){
            Map singlecotacao = (Map) entry.getValue();
            Object nome = singlecotacao.get("cliente");
            Object impressoras = singlecotacao.get("impressoras");
            Object infill = singlecotacao.get("infill");
            Object layer = singlecotacao.get("layer");
            Object maodeobra = singlecotacao.get("mao_de_obra");
            Object materiais = singlecotacao.get("materiais");
            Object peso = singlecotacao.get("peso");
            Object tempo = singlecotacao.get("tempo");

            cotcaoinidivual.add(nome.toString());
            cotcaoinidivual.add(impressoras.toString());
            cotcaoinidivual.add(infill.toString());
            cotcaoinidivual.add(layer.toString());
            cotcaoinidivual.add(maodeobra.toString());
            cotcaoinidivual.add(materiais.toString());
            cotcaoinidivual.add(peso.toString());
            cotcaoinidivual.add(tempo.toString());

            linhas += 1;

            colocarnoexcel(cotcaoinidivual);
            cotcaoinidivual = new LinkedList<>();
        }
    }

    private void colocarnoexcel(LinkedList<String> cotcaoinidivual) {

        Row row1 = sheet.createRow(linhas);

        cell=row1.createCell(0);
        cell.setCellValue(cotcaoinidivual.get(0));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(1);
        cell.setCellValue(cotcaoinidivual.get(1));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(2);
        cell.setCellValue(cotcaoinidivual.get(2));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(3);
        cell.setCellValue(cotcaoinidivual.get(3));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(4);
        cell.setCellValue(cotcaoinidivual.get(4));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(5);
        cell.setCellValue(cotcaoinidivual.get(5));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(6);
        cell.setCellValue(cotcaoinidivual.get(6));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(7);
        cell.setCellValue(cotcaoinidivual.get(7));
        cell.setCellStyle(cellStyle);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        final TextView textoarquivo = findViewById(R.id.texto_arquivo);

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri uri = resultData.getData();
            textoarquivo.setText(uri.toString());

            String path = uri.getPath();
            String filename = path.substring(path.lastIndexOf("/") + 1);
            String file;
            if (filename.indexOf(".") > 0) {
                file = filename.substring(0, filename.lastIndexOf("."));
            } else {
                file = filename;
            }
            Log.i("nome", filename);

            InputStream inputStream = null;
            try {
                inputStream = getBaseContext().getContentResolver().openInputStream(uri);

                byte[] bytes = IOUtils.toByteArray(inputStream);
                arquivo = new String(bytes, "ISO-8859-1");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}