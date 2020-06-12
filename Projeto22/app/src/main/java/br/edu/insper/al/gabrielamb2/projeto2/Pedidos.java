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
    ListView lvcotacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        lvcotacao = findViewById(R.id.Lvcotacao);
        ArrayList<String>arrayList;

        LerExcel lerExcel = new LerExcel("orcamentoantigo.xls");

        try {
            lerExcel.readExcelFile(this);
            arrayList = lerExcel.getlistanomes();
            ArrayAdapter cotacaoAdapter = new ArrayAdapter<>(this, R.layout.list_item,arrayList);
            lvcotacao.setAdapter(cotacaoAdapter);

            lvcotacao.setOnItemClickListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(AdapterView<?>parent, View view, int position, long id){
       //oq fazer quando clicar
    }




}