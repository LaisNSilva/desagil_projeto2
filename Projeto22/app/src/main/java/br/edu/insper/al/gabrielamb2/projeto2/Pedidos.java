package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Pedidos extends AppCompatActivity implements AdapterView.OnItemClickListener{
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Workbook wb = new HSSFWorkbook();
    Cell cell = null;
    CellStyle cellStyle = wb.createCellStyle();
    Sheet sheet = wb.createSheet("Orçamentos Antigos");
    int linhas = 0;
    String TAG = "main";
    TableLayout t1;
    LinkedList<String> linha = new LinkedList<>();
    LinkedList<String> cotacao = new LinkedList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        getorcamentos();

        t1 = (TableLayout)findViewById(R.id.t1);
        t1.setColumnStretchable(0,true);

        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo_titulo);

        try {
            readExcelFile(this);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getorcamentos() {
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
        });
    }
    private static String parseIso(Date value) {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return dt.format(value);
    }


    private void getClientes(Map<String, Object> cotacoes) {

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



        //      cell=row.createCell(6);
        //     cell.setCellValue("Support Removal");
        //      cell.setCellStyle(cellStyle);

        //       cell=row.createCell(7);
        //       cell.setCellValue("Vapor Polishing");
        //       cell.setCellStyle(cellStyle);

        //  cell=row.createCell(1);
        //   cell.setCellValue("Peça");
        //  cell.setCellStyle(cellStyle);

        //  cell=row.createCell(11);
        // cell.setCellValue("Valor");
        //  cell.setCellStyle(cellStyle);



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

    void readExcelFile(Context context) throws IOException {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), "orcamentoantigo.xls");
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /* We now need something to iterate through the cells.*/
            Iterator<Row> rowIter = mySheet.rowIterator();

            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator<Cell> cellIter = myRow.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    // Log.w("FileUtils", "Cell Value: " + myCell.toString());
                    cotacao.add(myCell.toString());
                }
            }
            for (int e = 0; e < cotacao.size(); e++) {
                // System.out.print("COTACAO.GETE" + cotacao.get(e));
                if(e>9) {
                    String elemento = cotacao.get(e);
                    linha.add(elemento);
                    if (e - 19 == 0 | (e - 19) % 10 == 0) {
                        TableRow tr;
                        TextView d , n, im, in , l, m , mat, p, t, v;
                        tr = new TableRow(this);
                        d = new TextView(this);
                        n = new TextView(this);
                        im = new TextView(this);
                        in = new TextView(this);
                        l = new TextView(this);
                        m = new TextView(this);
                        mat = new TextView(this);
                        p = new TextView(this);
                        t = new TextView(this);
                        v = new TextView(this);
                        d.setText(linha.get(0));
                        n.setText(linha.get(1));
                        im.setText(linha.get(2));
                        in.setText(linha.get(3));
                        l.setText(linha.get(4));
                        m.setText(linha.get(5));
                        mat.setText(linha.get(6));
                        p.setText(linha.get(7));
                        t.setText(linha.get(8));
                        v.setText(linha.get(9));

                        d.setGravity(Gravity.CENTER);
                        n.setGravity(Gravity.CENTER);
                        im.setGravity(Gravity.CENTER);
                        in.setGravity(Gravity.CENTER);
                        l.setGravity(Gravity.CENTER);
                        m.setGravity(Gravity.CENTER);
                        mat.setGravity(Gravity.CENTER);
                        p.setGravity(Gravity.CENTER);
                        t.setGravity(Gravity.CENTER);
                        v.setGravity(Gravity.CENTER);

                        tr.addView(d);
                        tr.addView(n);
                        tr.addView(im);
                        tr.addView(in);
                        tr.addView(l);
                        tr.addView(m);
                        tr.addView(mat);
                        tr.addView(p);
                        tr.addView(t);
                        tr.addView(v);
                        t1.addView(tr);

                        System.out.println(e);

                        System.out.println("LISTA ?" + linha);
                        linha = new LinkedList<>();
                    }
                }
            }

        } else {
            // External storage is not usable
            // Try again later
        }

    }



    @Override
    public void onItemClick(AdapterView<?>parent, View view, int position, long id){
       //oq fazer quando clicar
    }
}