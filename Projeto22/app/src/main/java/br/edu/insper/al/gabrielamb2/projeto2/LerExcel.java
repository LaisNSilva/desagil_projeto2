package br.edu.insper.al.gabrielamb2.projeto2;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class LerExcel {
    String filename;
    ArrayList<String> datas = new ArrayList<>();
    ArrayList<String> nomes = new ArrayList<>();
    ArrayList<String> impressora = new ArrayList<>();
    ArrayList<String> infill = new ArrayList<>();
    ArrayList<String> layer = new ArrayList<>();
    ArrayList<String> mao_de_obra = new ArrayList<>();
    ArrayList<String> materiais = new ArrayList<>();
    ArrayList<String> peso = new ArrayList<>();
    ArrayList<String> tempo = new ArrayList<>();
    ArrayList<String> valor = new ArrayList<>();
    LinkedList<String> cotacao = new LinkedList<>();
    LinkedList<LinkedList> cotacaogeral = new LinkedList();
    LinkedList<String> linha = new LinkedList<>();
    public LerExcel(String nome_do_arquivo) {
        this.filename = nome_do_arquivo;
    }

    void readExcelFile(Context context) throws IOException {
        int i =0;
        String data_nome ="";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), filename);
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
                    //if(cotacao.size()>10){
                    //    System.out.println(cotacao+"COTACAOOOOOOO");
                    //     cotacao = null;
                    // }

                    if ( i%10 == 0 | i==0 ){
                        datas.add(myCell.toString());
                        System.out.println(myCell.toString()+ "DATAAA");
                    }
                    if ( i-11 == 0 | (i-11)%10 ==0 | i==1){
                        nomes.add(myCell.toString());
                      //  System.out.println(myCell.toString()+ "NOMEEEE");
                    }
                    if ( i-12 == 0 | (i-12)%10 ==0 | i==2){
                        impressora.add(myCell.toString());
                      //  System.out.println(myCell.toString()+ "IMPRESSORA");
                    }
                    if ( i-13 == 0 | (i-13)%10 ==0 | i==3){
                        infill.add(myCell.toString());
                      //  System.out.println(myCell.toString()+ "infill");
                    }

                    if ( i-14 == 0 | (i-14)%10 ==0 | i==4){
                        layer.add(myCell.toString());
                      //  System.out.println(myCell.toString()+ "layer");
                    }

                    if ( i-15 == 0 | (i-15)%10 ==0 | i==5){
                        mao_de_obra.add(myCell.toString());
                    //    System.out.println(myCell.toString()+ "mao_de_obra");
                    }
                    if ( i-16 == 0 | (i-16)%10 ==0 | i==6){
                        materiais.add(myCell.toString());
                  //      System.out.println(myCell.toString()+ "materiais");
                    }
                    if ( i-17 == 0 | (i-17)%10 ==0 | i==7){
                        peso.add(myCell.toString());
                     //   System.out.println(myCell.toString()+ "peso");
                    }
                    if ( i-18 == 0 | (i-18)%10 ==0 | i==8){
                        tempo.add(myCell.toString());
                      //  System.out.println(myCell.toString()+ "tempo");
                    }
                    if ( i-19 == 0 | (i-19)%10 ==0 | i==9){
                        valor.add(myCell.toString());
                      //  System.out.println(myCell.toString()+ "valor");
                    }



                    i+=1;

                }
            }
            for (int e = 0; e < cotacao.size(); e++) {
                // System.out.print("COTACAO.GETE" + cotacao.get(e));
                if(e>9) {
                    String elemento = cotacao.get(e);
                    linha.add(elemento);
                    if (e - 19 == 0 | (e - 19) % 10 == 0) {
                        System.out.println(e);
                        getlistacotacao(linha);
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

    public ArrayList getlistanomes(){
        return nomes;
    }
    public ArrayList getlistadatas(){
        return datas;
    }
    public ArrayList getlistaimpressora(){
        return impressora;
    }
    public ArrayList getlistainfill(){
        return infill;
    }
    public ArrayList getlistalayer(){
        return layer;
    }
    public ArrayList getlistamao_de_obra(){
        return mao_de_obra;
    }
    public ArrayList getlistamateriais(){
        return materiais;
    }
    public ArrayList getlistapeso(){return peso;    }
    public ArrayList getlistatempo(){
        return tempo;
    }
    public ArrayList getlistavalor(){
        return valor;
    }
    public LinkedList<String> getlistacotacao(LinkedList<String>linha){
        return linha;
    }




}