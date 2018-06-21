package com.florian.projet.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.bdd.entity.Article;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;


public class ParseArticlePerfFileTask extends AsyncTask<File, Void, ArrayList<Article>> {
    private Callback callback;
    private Exception mException;

    public interface Callback {
        void onSuccess(ArrayList<Article> dataLineList);
        void onFailed(Exception e);
    }

    public ParseArticlePerfFileTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected ArrayList<Article> doInBackground(File... files) {
        ArrayList<Article> dataLineList = new ArrayList<>();

        try {
            InputStream excelFile;

            excelFile = new FileInputStream(files[0]);
            XSSFWorkbook wb = new XSSFWorkbook(excelFile);

            XSSFSheet sheet = wb.getSheet("Data");
            XSSFRow row;
            XSSFCell cell;

            Iterator rows = sheet.rowIterator();
            Article article;

            boolean hasData = true;

            while (rows.hasNext() && hasData) {
                row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                article = new Article();

                if (row.getRowNum() >= 4) {
                    while (cells.hasNext()) {
                        cell = (XSSFCell) cells.next();

                        switch (cell.getColumnIndex()) {
                            case 1:

                                if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                    Log.d("Date -> ", cell.getDateCellValue() + " _");
                                    article.setDate(cell.getDateCellValue());
                                } else {
                                    hasData = false;
                                }
                                break;

                            case 14:
                                if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                    Log.d("Quantite -> ", cell.getNumericCellValue() + " _");
                                    article.setQuantity(cell.getNumericCellValue());
                                }
                                break;

                            case 24:
                                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                Log.d("Nom -> ", cell.getStringCellValue() + " _ ");
                                article.setName(cell.getStringCellValue());
                                break;

                            default:
                                break;

                        }
                    }

                    if (article.getName() != null && article.getDate() != null) {
                        dataLineList.add(article);
                    }
                }
            }

        } catch (Exception e) {
            Log.d("Article Parse Error ", e.getMessage());
            mException = e;
        }
        return dataLineList;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleArrayList) {
        if (mException == null) {
            callback.onSuccess(articleArrayList);
        } else {
            callback.onFailed(mException);
        }
    }
}
