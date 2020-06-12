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
    ArrayList<String> nomes = new ArrayList<>();
    public LerExcel(String nome_do_arquivo) {
        this.filename = nome_do_arquivo;
    }

    void readExcelFile(Context context) throws IOException {
        int i =0;


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

            /** We now need something to iterate through the cells.**/
            Iterator<Row> rowIter = mySheet.rowIterator();

            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator<Cell> cellIter = myRow.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    Log.w("FileUtils", "Cell Value: " + myCell.toString());
                    if ( i%8 == 0 & i!=0 ){
                        nomes.add(myCell.toString());
                        System.out.println(myCell.toString()+ "uhullll");

                    }
                    i+=1;

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

}
