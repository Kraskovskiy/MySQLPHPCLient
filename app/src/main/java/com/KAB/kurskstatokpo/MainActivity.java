package com.KAB.kurskstatokpo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEt1;
    ViewGroup mViewGroup;
    TextView mTxt1;
    TextView mTxt2;
    TextView mTxt3;
    TextView mTxt4;
    TextView mTxt5;
    TextView mTxt6;
    TextView mTxt7;
    TextView mTxt8;
    TextView mTxt9;
    TextView mTxtNameOKPO;
    TableLayout mTable;
    JSONHandler mJsonHandler;
    SharedPreferences mSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroup = (ViewGroup) this.findViewById(android.R.id.content);
        mJsonHandler = new JSONHandler(getApplicationContext(),mViewGroup);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("КурскСтат");
        setSupportActionBar(toolbar);

        mTxt1 = (TextView) findViewById(R.id.txt1);
        mTxt1.setOnClickListener(this);
        mTxt2 = (TextView) findViewById(R.id.txt2);
        mTxt2.setOnClickListener(this);
        mTxt3 = (TextView) findViewById(R.id.txt3);
        mTxt3.setOnClickListener(this);
        mTxt4 = (TextView) findViewById(R.id.txt4);
        mTxt4.setOnClickListener(this);
        mTxt5 = (TextView) findViewById(R.id.txt5);
        mTxt5.setOnClickListener(this);
        mTxt6 = (TextView) findViewById(R.id.txt6);
        mTxt6.setOnClickListener(this);
        mTxt7 = (TextView) findViewById(R.id.txt7);
        mTxt7.setOnClickListener(this);
        mTxt8 = (TextView) findViewById(R.id.txt8);
        mTxt8.setOnClickListener(this);
        mTxt9 = (TextView) findViewById(R.id.txt9);
        mTxt9.setOnClickListener(this);
        mTable = (TableLayout) findViewById(R.id.score_table);

        mTxtNameOKPO = (TextView) findViewById(R.id.txtNameOKPO);

        mEt1 = (EditText) findViewById(R.id.et1);
        loadText();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (editTextValidator(mEt1.getText().toString())) {
                    hideKeyboard(mViewGroup);
                    clearTable();
                    new GetDataFromPHP(getApplicationContext(), mViewGroup).execute(mEt1.getText().toString());
                    new GetNameFromPHP(getApplicationContext(), mViewGroup).execute(mEt1.getText().toString());
                    JSONHandler.mOKPO = mEt1.getText().toString();
                }

            }
        });


        //tableCreate("x","x2","x3","x4");
   }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.txt1:
                getSortTableFromJSON(mJsonHandler.mJSONObject,0);
                break;
            case R.id.txt2:
                getSortTableFromJSON(mJsonHandler.mJSONObject,1);
                break;
            case R.id.txt3:
                getSortTableFromJSON(mJsonHandler.mJSONObject,2);
                break;
            case R.id.txt4:
                getSortTableFromJSON(mJsonHandler.mJSONObject,3);
                break;
            case R.id.txt5:
                getSortTableFromJSON(mJsonHandler.mJSONObject,4);
                break;
            case R.id.txt6:
                getSortTableFromJSON(mJsonHandler.mJSONObject,5);
                break;
            case R.id.txt7:
                getSortTableFromJSON(mJsonHandler.mJSONObject,6);
                break;
            case R.id.txt8:
                getSortTableFromJSON(mJsonHandler.mJSONObject,7);
                break;
            case R.id.txt9:
                getSortTableFromJSON(mJsonHandler.mJSONObject,8);
                break;

        }
    }

    public Boolean editTextValidator(String s) {
        if (s.length()>2&&s.length()<128) {
            saveText();
            return true;
        }
        Toast.makeText(getApplicationContext(),"Проверьте корректность вводимых данных",Toast.LENGTH_SHORT).show();

        return false;
    }


    public void getSortTableFromJSON(ArrayList<JSONObject> jsonObj,int numberOfRow) {
        try {
            clearTable();
            mJsonHandler.getSortArray(jsonObj, numberOfRow);

            for (int i = 0; i < jsonObj.size(); i++) {
                Log.i("getSortTableFromJSON",mJsonHandler.getFinalObjectString(mJsonHandler.mJSONObject.get(i))[numberOfRow]);
                mJsonHandler.tableCreate(mViewGroup, mJsonHandler.getFinalObjectString(mJsonHandler.mJSONObject.get(i)), i % 2);

            }
        }
        catch (Exception e) {
            Log.i("getSortTableFromJSON","onClick"+e.toString());
        }
    }

    public void getTableFromJSON(ArrayList<JSONObject> jsonObj) {
        if (jsonObj!=null&&jsonObj.size()>0) {
            try {
                clearTable();

                for (int i = 0; i < jsonObj.size(); i++) {

                    mJsonHandler.tableCreate(mViewGroup, mJsonHandler.getFinalObjectString(mJsonHandler.mJSONObject.get(i)), i % 2);

                }
            } catch (Exception e) {
                Log.i("getSortTableFromJSON", "onClick" + e.toString());
            }
        }
    }

    public void clearTable() {
        mTable.removeAllViews();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

           // mJsonHandler.saveToCSV(getApplicationContext(),mJsonHandler.getFinalObjectString(mJsonHandler.mJSONObject.get(0)));
            if (mJsonHandler.mJSONObject!=null) {
                mJsonHandler.saveToCSV(getApplicationContext(), mJsonHandler.mJSONObject);
            }
            return true;
        }

        if (id == R.id.action_print) {
            if (mJsonHandler.mJSONObject!=null) {
                mJsonHandler.shareCSV(getApplicationContext(),mJsonHandler.mJSONObject);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getTableFromJSON(mJsonHandler.mJSONObject);
        if (GetNameFromPHP.mNameOfOKPO!=null) {
            mTxtNameOKPO.setText(GetNameFromPHP.mNameOfOKPO);
        }

    }

   public void saveText() {
        mSP = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = mSP.edit();
        ed.putString("SAVED_TEXT", mEt1.getText().toString());
        ed.apply();

    }

   public void loadText() {
        mSP = getPreferences(MODE_PRIVATE);
        String savedText = mSP.getString("SAVED_TEXT", "");
        mEt1.setText(savedText);

    }


}
