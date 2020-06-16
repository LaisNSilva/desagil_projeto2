package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Pedidos extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String TAG = "main";
    TableLayout t1;
    LinkedList<String> linha = new LinkedList<>();
    LinkedList<String> cotacao = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        t1 = (TableLayout)findViewById(R.id.t1);
        t1.setColumnStretchable(0,true);

        try {
            readExcelFile(this);

        } catch (IOException e) {
            e.printStackTrace();
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