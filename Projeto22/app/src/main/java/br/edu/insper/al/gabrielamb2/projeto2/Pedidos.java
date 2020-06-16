package br.edu.insper.al.gabrielamb2.projeto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
    TextView d , n, im, in , l, m , mat, p, t, v;
    String TAG = "main";
    TableLayout t1;
    TableRow tr;
    LinkedList<String>cotacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

       // t1 = (TableLayout)findViewById(R.id.t1);
       // t1.setColumnStretchable(0,true);

        LerExcel lerExcel = new LerExcel("orcamentoantigo.xls");

        try {
            lerExcel.readExcelFile(this);
            cotacoes = lerExcel.getlistacotacao();


            }
         //   cotacao1=null;
        //    tr = new TableRow(this);
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onItemClick(AdapterView<?>parent, View view, int position, long id){
       //oq fazer quando clicar
    }
}