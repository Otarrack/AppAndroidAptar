package com.florian.projet.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.model.ArticleLine;
import com.florian.projet.tools.DateFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;


public class ParseArticlePerfFileTask extends AsyncTask<File, Void, ArrayList<ArticleLine>> {
    private Callback callback;
    private Exception mException;

    public interface Callback {
        void onSuccess(ArrayList<ArticleLine> dataLineList);
        void onFailed(Exception e);
    }

    public ParseArticlePerfFileTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected ArrayList<ArticleLine> doInBackground(File... files) {
        ArrayList<ArticleLine> dataLineList = new ArrayList<>();

        try {
            InputStream excelFile;

            excelFile = new FileInputStream(files[0]);
            InputStreamReader streamReader = new InputStreamReader(excelFile);
            BufferedReader br = new BufferedReader(streamReader);
            String line;
            String csvSplitBy = ";";

            br.readLine();
            ArticleLine article;

            boolean hasData = true;
            int index = 1;

            while ((line = br.readLine()) != null && hasData) {
                article = new ArticleLine();

                if (index > 4) {
                    String[] row = line.split(csvSplitBy);

                    if (row.length >= 24) {

                        if (row[3].equals("")) {
                            hasData = false;
                        }

                        if (!row[4].equals("")) {
                            article.setType(row[4]);
                        }

                        if (!row[5].equals("")) {
                            article.setNumOf(row[5]);
                        }

                        if (!row[12].equals("")) {
                            Log.d("Client -> ", row[12] + " _ ");
                            article.setCustomer(row[12]);
                        }

                        if (!row[14].equals("")) {
                            Double d = Double.valueOf(row[14].replaceAll(" ", ""));
                            Log.d("Quantite -> ", d + " _");
                            article.setQuantity(d);
                        }

                        if (!row[22].equals("")) {
                            Log.d("Date -> ", row[22] + " _");
                            Date date = DateFormatter.parseDate(row[22],"dd/MM/yyyy");
                            if (date != null) {
                                Log.d("Date -> ", date.toString() + " _");
                                article.setDate(date);
                            }
                        }

                        if (!row[24].equals("")) {
                            Log.d("Nom -> ", row[24] + " _ ");
                            article.setName(row[24]);
                        }

                        if (article.getName() != null && article.getDate() != null) {
                            dataLineList.add(article);
                        }
                    } else {
                        for (String s : row) {
                            Log.d("ROW -> ", s + " _");
                        }
                    }
                }

                index++;
            }

        } catch (Exception e) {
            Log.d("Article Parse Error ", e.getMessage() + " ! ");
            mException = e;
        }
        return dataLineList;
    }

    @Override
    protected void onPostExecute(ArrayList<ArticleLine> articleArrayList) {
        if (mException == null) {
            callback.onSuccess(articleArrayList);
        } else {
            callback.onFailed(mException);
        }
    }
}
