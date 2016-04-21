package com.KAB.kurskstatokpo;

/**
 * Created by Suxx on 08.04.16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GetDataFromPHP extends AsyncTask<String, Void, String> {

    private Context mContext;
    private View mView;
    private ProgressBar mProgressBar;
    JSONHandler mJsonHandler;

    public GetDataFromPHP(Context context, View view) {
        this.mContext = context;
        this.mView = view;
        mProgressBar = (ProgressBar) view.findViewById(R.id.pBar);
        mJsonHandler = new JSONHandler(context,view);

    }

    protected void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Void... progress) {

    }
    @Override
    protected String doInBackground(String... arg0) {
        String fullName = arg0[0];
        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?fullname=" + URLEncoder.encode(fullName, "UTF-8");
            //example link
            link = "http://127.0.0.1/test/signup16.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            con.disconnect();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mProgressBar.setVisibility(View.INVISIBLE);
        String jsonStr = result;
        if (jsonStr != null) {
            try {

                JSONObject jsonObj = new JSONObject(jsonStr);
                mJsonHandler.getObject(jsonObj);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Ошибка получения данных", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "Проверьте корректность введенных данных", Toast.LENGTH_SHORT).show();
        }

    }

 }
