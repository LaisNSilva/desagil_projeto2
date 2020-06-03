package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class Cotacao extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "Uri";
    EditText texto_arquivo;
    private void showToast(String text) {

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacao);

        //TextView arquivoPeca = findViewById(R.id.peca);

        final File diretorio = getApplicationContext().getExternalFilesDir(null);

        final EditText cliente = findViewById(R.id.cliente);
        final EditText infill = findViewById(R.id.infill);
        final EditText layer = findViewById(R.id.layer);
        final EditText mao_de_obra = findViewById(R.id.maodeobra);
        texto_arquivo = (EditText) findViewById(R.id.texto_arquivo);

        final TextView peso = findViewById(R.id.peso);
        final TextView tempo = findViewById(R.id.tempo);
        final TextView valor = findViewById(R.id.valor);

        Button buttonArq = findViewById(R.id.escolher_arquivo);
        Button processar = findViewById(R.id.button_processar);
        Button enviar = findViewById(R.id.button_enviar);

        final CheckBox supportRemoval = findViewById(R.id.suport_check);
        final CheckBox vaporPolishing = findViewById(R.id.vapor_check);

        
        //Spinners (Impressoras e Filamentos)
        final Spinner materiais = findViewById(R.id.material);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.materiais, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materiais.setAdapter(adapter1);

        final Spinner impressoras = findViewById(R.id.impressora);
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
                System.out.println(materiais.getSelectedItem().toString());
                $_POST.put("color", color);
                System.out.println(color);
                $_POST.put("layerHeight", layer.getText().toString());
                System.out.println(layer.getText().toString());
                $_POST.put("infillPercentage", infill.getText().toString());
                System.out.println(infill.getText().toString());

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

                String boundary = "------WebKitFormBoundary" + "1$#23gf784";

                HashMap<String, HashMap<String, String>> $_FILES = new HashMap<>();
                HashMap<String, String>values_files = new HashMap<>();

                //values_files.put("name", ???);
                //values_files.put("type", ???);
                //values_files.put("tmp_name", ARQUIVO_DA_LAIS);

                //$_FILES.put("stlFiles[]", values_files);

                RequestMulti requestMulti = new RequestMulti($_POST, $_FILES, boundary);
                String output_request = requestMulti.buildMultipartPost($_POST, $_FILES, boundary);
                System.out.println(output_request);
                String cliente_ = cliente.getText().toString();
                String infill_ = infill.getText().toString();
                String layer_ = layer.getText().toString();
                String impressora = impressoras.getSelectedItem().toString();
                String maodeobra = mao_de_obra.getText().toString();
//---------------------------------------------
//Calculos
//---------------------------------------------
                String peso_ = peso.getText().toString();
                String tempo_ = tempo.getText().toString();
                String valor_ = valor.getText().toString();
                String texto_final = "Cliente: "+ cliente_ + "  " + "Infill: " + infill_ + "  " + "Layer: "+ layer_ + "  " + "Impressora: " + impressora + "  "
                        + "Material: " + material_escolhido + "  "+ "Mão de Obra: " + maodeobra + "  " + "Peso: " + peso_ + "  " + "Tempo: " +  tempo_ + "  " +"Valor: " + valor_;
                texto_final = texto_final.replace("  ","\n");
                String filename = "cotacao_"+cliente_+".txt";
                File file = new File(diretorio + "/" + filename);

                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // External storage is usable
                    FileOutputStream outputStream = null;
                    try {
                        outputStream= new FileOutputStream(file);
                        outputStream.write(texto_final.getBytes());
                        showToast("Arquivo Criado");
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String cliente_ = cliente.getText().toString();
              String filename = "cotacao_"+cliente_+".txt";
              File outputFile = new File(diretorio+"/"+filename);

              Uri uri = Uri.fromFile(outputFile);

              //Intent share = new Intent();
             // share.setAction(Intent.ACTION_SEND);
             // share.setType("text/plain");
             // share.putExtra(Intent.EXTRA_STREAM, uri);
              //share.setPackage("com.whatsapp");

            //  Cotacao.this.startActivity(share);





                //PARTE DE CRIAR UM EXCEL
                Workbook wb=new HSSFWorkbook();
                Cell cell=null;
                CellStyle cellStyle=wb.createCellStyle();
                //Now we are creating sheet
                Sheet sheet=null;
                sheet = wb.createSheet("Orçamentos Antigos");
                //Now column and row
                Row row =sheet.createRow(0);
                cell=row.createCell(0);
                cell.setCellValue("Cliente");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(1);
                cell.setCellValue("Peça");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(2);
                cell.setCellValue("Infill (%)");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(3);
                cell.setCellValue("Support Removal");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(4);
                cell.setCellValue("Vapor Polishing");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(5);
                cell.setCellValue("Layer");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(6);
                cell.setCellValue("Impressora");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(7);
                cell.setCellValue("Material");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(8);
                cell.setCellValue("Mão de obra");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(9);
                cell.setCellValue("Peso");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(10);
                cell.setCellValue("Tempo");
                cell.setCellStyle(cellStyle);

                cell=row.createCell(11);
                cell.setCellValue("Valor");
                cell.setCellStyle(cellStyle);

                Row row1 =sheet.createRow(1);
                cell=row1.createCell(0);
                cell.setCellValue( cliente.getText().toString());
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(1);
                cell.setCellValue("Peça");
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(2);
                cell.setCellValue(infill.getText().toString());
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(3);
                cell.setCellValue("Support Removal");
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(4);
                cell.setCellValue("Vapor Polishing");
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(5);
                cell.setCellValue(layer.getText().toString());
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(6);
                cell.setCellValue(impressoras.getSelectedItem().toString());
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(7);
                cell.setCellValue("Material");
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(8);
                cell.setCellValue(mao_de_obra.getText().toString());
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(9);
                cell.setCellValue(peso.getText().toString());
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(10);
                cell.setCellValue( tempo.getText().toString());
                cell.setCellStyle(cellStyle);

                cell=row1.createCell(11);
                cell.setCellValue(valor.getText().toString());
                cell.setCellStyle(cellStyle);


                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // External storage is usable
                    File file = new File(getExternalFilesDir(null),"orcamentoantigo.xls");
                    FileOutputStream outputStream =null;
                    try {
                        outputStream=new FileOutputStream(file);
                        wb.write(outputStream);
                        Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();

                        Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
                        try {
                            outputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                } else {
                    // External storage is not usable
                    // Try again later
                }



            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri uri = resultData.getData();
            texto_arquivo.setText(uri.toString());
            //rquivoPeca.setText(uri.toString());
            //Creating an InputStream object

            String path = uri.getPath();
            String filename = path.substring(path.lastIndexOf("/") + 1);
            String file;
            if (filename.indexOf(".") > 0) {
                file = filename.substring(0, filename.lastIndexOf("."));
            } else {
                file = filename;
            }
            Log.i("nome", filename);

            //System.out.println(uri);
            InputStream inputStream = null;
            try {
                inputStream = getBaseContext().getContentResolver().openInputStream(uri);
                //creating an InputStreamReader object
                InputStreamReader isReader = new InputStreamReader(inputStream);
                //Creating a BufferedReader object
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer sb = new StringBuffer();
                String str;
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                //System.out.println(sb.toString());
                //Log.i("meuapp", sb.toString());
                String arquivo = sb.toString();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
