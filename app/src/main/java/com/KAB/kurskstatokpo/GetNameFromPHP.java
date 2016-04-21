package com.KAB.kurskstatokpo;

        import android.content.Context;
        import android.os.AsyncTask;
        import android.view.View;
        import android.widget.TextView;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;

public class GetNameFromPHP extends AsyncTask<String, Void, String> {

    private Context mContext;
    private View mView;
    public static String mNameOfOKPO;
    private TextView mTxtNameOKPO;

    public GetNameFromPHP(Context context, View view) {
        this.mContext = context;
        this.mView = view;
        mTxtNameOKPO = (TextView) view.findViewById(R.id.txtNameOKPO);

    }

    protected void onPreExecute() {

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
            link = "http://127.0.0.1/test/signup16n.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            con.disconnect();
            return result;
        } catch (Exception e) {
            return new String("Exception:");
        }
    }

    @Override
    protected void onPostExecute(String result) {

        mNameOfOKPO = result;

        if (mNameOfOKPO != null&&!mNameOfOKPO.equals("Exception:")) {

            mTxtNameOKPO.setText(mNameOfOKPO);
        }
        else {
            mTxtNameOKPO.setText("");
        }
    }
}