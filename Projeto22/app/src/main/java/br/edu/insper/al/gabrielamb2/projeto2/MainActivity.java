package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

public class MainActivity extends AppCompatActivity {
    Workbook wb = new HSSFWorkbook();
    Cell cell = null;
    CellStyle cellStyle = wb.createCellStyle();
    Sheet sheet = wb.createSheet("Orçamentos Antigos");
    int linhas = 0;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonCotacao = findViewById(R.id.button_orcamento);
        Button buttonPedidos = findViewById(R.id.button_orcamento_antigos);
        Button buttonConfiguracao = findViewById(R.id.button_configuracoes);
        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo_titulo);

        final Intent cotacao = new Intent(this, Cotacao.class);

        final Intent pedidos = new Intent(this, Pedidos.class);

        final Intent configuracao = new Intent(this, Configuracao.class);

        getOrcamentos();


        buttonCotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cotacao);
            }
        });
        buttonPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(pedidos);

            }
        });
        buttonConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(configuracao);

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

                    System.out.println( "foiiiii");

                } else {
                    System.out.println("DEU RUIM");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Ouve um problema de conexão",Toast.LENGTH_LONG).show();
            }
        });    }

    private static String parseIso(Date value) {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return dt.format(value);
    }


    private void getClientes(Map<String, Object> cotacoes) {
        System.out.print(cotacoes+"COTACOESSSSSSSSSSSSSSSSSSSSSSSS");
        LinkedList<String> cotcaoinidivual = new LinkedList<>();
        for(Map.Entry<String,Object>entry: cotacoes.entrySet()){
            Map singlecotacao = (Map) entry.getValue();
            Date newdate = new Date(Long.valueOf(entry.getKey()));
            String hora = parseIso(newdate);

            Object nome = singlecotacao.get("cliente");
            Object impressoras = singlecotacao.get("impressoras");
            Object infill = singlecotacao.get("infill");
            Object layer = singlecotacao.get("layer");
            Object maodeobra = singlecotacao.get("mao_de_obra");
            Object materiais = singlecotacao.get("materiais");
            Object peso = singlecotacao.get("peso");
            Object tempo = singlecotacao.get("tempo");
            Object valor = singlecotacao.get("valor");

            cotcaoinidivual.add(hora);
            cotcaoinidivual.add(nome.toString());
            cotcaoinidivual.add(impressoras.toString());
            cotcaoinidivual.add(infill.toString());
            cotcaoinidivual.add(layer.toString());
            cotcaoinidivual.add(maodeobra.toString());
            cotcaoinidivual.add(materiais.toString());
            cotcaoinidivual.add(peso.toString());
            cotcaoinidivual.add(tempo.toString());
            cotcaoinidivual.add(valor.toString());

            //  cotcaoinidivual.add(valor.toString());

            linhas+=1;

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


        cell=row1.createCell(8);
        cell.setCellValue(cotcaoinidivual.get(8));
        cell.setCellStyle(cellStyle);

        cell=row1.createCell(9);
        cell.setCellValue(cotcaoinidivual.get(9));
        cell.setCellStyle(cellStyle);

        Row row =sheet.createRow(0);

        cell=row.createCell(0);
        cell.setCellValue("Dia e Hora");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(1);
        cell.setCellValue("Cliente");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(2);
        cell.setCellValue("Impressora");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(3);
        cell.setCellValue("Infill");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(4);
        cell.setCellValue("Layer");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(5);
        cell.setCellValue("Mão de obra");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(6);
        cell.setCellValue("Materiais");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(7);
        cell.setCellValue("Peso");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(8);
        cell.setCellValue("Tempo");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(9);
        cell.setCellValue("Valor");
        cell.setCellStyle(cellStyle);


        sheet.setColumnWidth(0,(10*500));
        sheet.setColumnWidth(1,(10*200));
        sheet.setColumnWidth(2,(10*300));
        sheet.setColumnWidth(3,(10*200));
        sheet.setColumnWidth(4,(10*200));
        sheet.setColumnWidth(5,(10*400));
        sheet.setColumnWidth(6,(10*200));
        sheet.setColumnWidth(7,(10*200));
        sheet.setColumnWidth(8,(10*200));
        sheet.setColumnWidth(9,(10*200));

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(getExternalFilesDir(null),"orcamentoantigo.xls");
            FileOutputStream outputStream = null;
            try {
                outputStream=new FileOutputStream(file);
                wb.write(outputStream);

            } catch (java.io.IOException e) {
                e.printStackTrace();

                try {
                    assert outputStream != null;
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }else {
            System.out.print("Nao foi possivel acessar o arquivo");
        }

    }
}
