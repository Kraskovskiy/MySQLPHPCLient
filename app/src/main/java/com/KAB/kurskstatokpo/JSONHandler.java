package com.KAB.kurskstatokpo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Suxx on 14.04.16.
 */
public class JSONHandler {
    private  Context mContext;
    private  View mView;
    public static  ArrayList<JSONObject> mJSONObject;
    public static String[] mJSFinalObject=new String[9];
    public static String mOKPO = "";
    public String[] mSortList = {"COL1","COL2","COL3",
            "COL4","COL5","COL6","COL7","COL8","COL9","COL10"};


    public JSONHandler(Context context,View view) {
        this.mContext = context;
        this.mView = view;
    }


    public void getObject(JSONObject jsonObj){
        Iterator<String> keys = jsonObj.keys();
        int i = 0;
        mJSONObject = new ArrayList<JSONObject>();
        while (keys.hasNext())
        {
            // Get the key
            String key = keys.next();

            // Get the value
            try {
                mJSONObject.add(jsonObj.getJSONObject(key));
                getFinalObject(jsonObj.getJSONObject(key));
                tableCreate(mView,mJSFinalObject,i%2);
            }
            catch (Exception e) {
              //  Log.i("Exception",e.toString());
            }
            i++;
        }
    }

    public void getFinalObject(JSONObject jsonObj) {
        try {
            mJSFinalObject[0] = jsonObj.getString("COL1");
            mJSFinalObject[1] = jsonObj.getString("COL2");
            mJSFinalObject[2] = jsonObj.getString("COL3");
            mJSFinalObject[3] = jsonObj.getString("COL4");
            mJSFinalObject[4] = jsonObj.getString("COL5");
            mJSFinalObject[5] = jsonObj.getString("COL6");
            mJSFinalObject[6] = jsonObj.getString("COL7");
            mJSFinalObject[7] = jsonObj.getString("COL8");
            mJSFinalObject[8] = jsonObj.getString("COL9");
        }
        catch (Exception e) {

        }
    }

    public String[] getFinalObjectString(JSONObject jsonObj) {
        try {
            mJSFinalObject[0] = jsonObj.getString("COL1");
            mJSFinalObject[1] = jsonObj.getString("COL2");
            mJSFinalObject[2] = jsonObj.getString("COL3");
            mJSFinalObject[3] = jsonObj.getString("COL4");
            mJSFinalObject[4] = jsonObj.getString("COL5");
            mJSFinalObject[5] = jsonObj.getString("COL6");
            mJSFinalObject[6] = jsonObj.getString("COL7");
            mJSFinalObject[7] = jsonObj.getString("COL8");
            mJSFinalObject[8] = jsonObj.getString("COL9");
        }
        catch (Exception e) {
            return null;
        }
        return mJSFinalObject;
    }

    public void getSortArray(ArrayList<JSONObject> array, final int numberOfRow) {
        Collections.sort(array, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                // TODO Auto-generated method stub

                try {
                    return (lhs.getString(mSortList[numberOfRow]).toLowerCase().compareTo(rhs.getString(mSortList[numberOfRow]).toLowerCase()));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    return 0;
                }
            }
        });
    }


    public void tableCreate(View view,String[] d,int j)
    {
        TableLayout table = (TableLayout) view.findViewById(R.id.score_table);

        // create a new TableRow
        TableRow row = new TableRow(view.getContext());

        TextView[] t = new TextView[d.length];

        for (int i=0;i<d.length;i++) {
            t[i] = new TextView(view.getContext());
            if (j==0) {
                t[i].setBackgroundResource(R.drawable.cell_shape);
            }
            else {
                t[i].setBackgroundResource(R.drawable.cell_shape2);
            }
            t[i].setPadding(5, 5, 5, 5);

            View view_instance;
            if (i==0) {
                view_instance = (View) view.findViewById(R.id.txt1);
            }
            else {
                view_instance = (View) view.findViewById(R.id.txt2);
            }

            ViewGroup.LayoutParams params = view_instance.getLayoutParams();


            t[i].setLayoutParams(params);
            t[i].setText(d[i]);
            t[i].setTextColor(view.getContext().getResources().getColor(R.color.White));

            row.addView(t[i]);
        }

        table.addView(row,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

    }


public void saveToCSV(Context context,ArrayList<JSONObject> jsonObj) {
    String columnString =  "\""+context.getString(R.string.tab1)+"\",\""+context.getString(R.string.tab2)
            +"\",\""+context.getString(R.string.tab3)+"\",\""+context.getString(R.string.tab4)+"\",\""+context.getString(R.string.tab5)
            +"\",\""+context.getString(R.string.tab6)+"\",\""+context.getString(R.string.tab7)+"\",\""+context.getString(R.string.tab8)
            +"\",\""+context.getString(R.string.tab9)+"\"";

   // String dataString = "";
    StringBuilder sb = new StringBuilder();

    sb.append(columnString+"\n");
    if (jsonObj!=null&&jsonObj.size()>0) {
        try {

            for (int i = 0; i < jsonObj.size(); i++) {

              //  sb2.append(getFinalObjectString(jsonObj.get(i)).toString());
                sb.append(  "\"" + getFinalObjectString(jsonObj.get(i))[0] +"\",\"" + getFinalObjectString(jsonObj.get(i))[1]
                        + "\",\"" + getFinalObjectString(jsonObj.get(i))[2] + "\",\"" + getFinalObjectString(jsonObj.get(i))[3]
                        + "\",\""  + getFinalObjectString(jsonObj.get(i))[4]+ "\",\""
                        + getFinalObjectString(jsonObj.get(i))[5]+ "\",\""+ getFinalObjectString(jsonObj.get(i))[6]
                        + "\",\"" + getFinalObjectString(jsonObj.get(i))[7]+ "\",\""+ getFinalObjectString(jsonObj.get(i))[8] + "\"");
                sb.append("\n");

            }
        } catch (Exception e) {
            Log.i("saveToCSV", e.toString());
        }
    }

    String combinedString = sb.toString();

    File file   = null;
    File root   = Environment.getExternalStorageDirectory();
    if (root.canWrite()){
        File dir    =   new File (root.getAbsolutePath() + "/OKPO");
        dir.mkdirs();
        file   =   new File(dir, "OKPO_"+mOKPO+".csv");
        FileOutputStream out   =   null;

        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,"Ошибка сохранения на внешнюю карту памяти",Toast.LENGTH_LONG).show();
        }
        try {
            out.write(combinedString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"Ошибка сохранения на внешнюю карту памяти",Toast.LENGTH_LONG).show();
        }
        try {
            out.close();
            Toast.makeText(context,"Файл сохранен по пути: \""+root.getAbsolutePath() + "/OKPO\"",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"Ошибка сохранения на внешнюю карту памяти",Toast.LENGTH_LONG).show();
        }
    }

   }

    public void shareCSV(Context context,ArrayList<JSONObject> jsonObj){
        String columnString =  "\""+context.getString(R.string.tab1)+"\",\""+context.getString(R.string.tab2)
                +"\",\""+context.getString(R.string.tab3)+"\",\""+context.getString(R.string.tab4)+"\",\""+context.getString(R.string.tab5)
                +"\",\""+context.getString(R.string.tab6)+"\",\""+context.getString(R.string.tab7)+"\",\""+context.getString(R.string.tab8)
                +"\",\""+context.getString(R.string.tab9)+"\"";

        // String dataString = "";
        StringBuilder sb = new StringBuilder();

        sb.append(columnString+"\n");
        if (jsonObj!=null&&jsonObj.size()>0) {
            try {

                for (int i = 0; i < jsonObj.size(); i++) {

                    //  sb2.append(getFinalObjectString(jsonObj.get(i)).toString());
                    sb.append(  "\"" + getFinalObjectString(jsonObj.get(i))[0] +"\",\"" + getFinalObjectString(jsonObj.get(i))[1]
                            + "\",\"" + getFinalObjectString(jsonObj.get(i))[2] + "\",\"" + getFinalObjectString(jsonObj.get(i))[3]
                            + "\",\""  + getFinalObjectString(jsonObj.get(i))[4]+ "\",\""
                            + getFinalObjectString(jsonObj.get(i))[5]+ "\",\""+ getFinalObjectString(jsonObj.get(i))[6]
                            + "\",\"" + getFinalObjectString(jsonObj.get(i))[7]+ "\",\""+ getFinalObjectString(jsonObj.get(i))[8] + "\"");
                    sb.append("\n");

                }
            } catch (Exception e) {
                Log.i("saveToCSV", e.toString());
            }
        }
        String combinedString = sb.toString();

        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/OKPO");
            dir.mkdirs();
            file   =   new File(dir, "OKPO_"+mOKPO+".csv");
            FileOutputStream out   =   null;

            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context,"Ошибка сохранения на внешнюю карту памяти",Toast.LENGTH_LONG).show();
            }
            try {
                out.write(combinedString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context,"Ошибка сохранения на внешнюю карту памяти",Toast.LENGTH_LONG).show();
            }
            try {
                out.close();
                Toast.makeText(context,"Файл сохранен по пути: \""+root.getAbsolutePath() + "/OKPO\"",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context,"Ошибка сохранения на внешнюю карту памяти",Toast.LENGTH_LONG).show();
            }
        }

        Uri u1  =  Uri.fromFile(file);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Список форм");
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        context.startActivity(sendIntent);
    }


}
