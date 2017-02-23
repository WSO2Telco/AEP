package tip.wso2telco.com.tiptopup;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final EditText msisdn = (EditText)findViewById(R.id.editText);
        final EditText amount = (EditText)findViewById(R.id.editText2);

        final TextView statusview = (TextView)findViewById(R.id.textView3);
        final TextView balanceview = (TextView)findViewById(R.id.textView4);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msisdnValue = msisdn.getText().toString();
                String amountValue = amount.getText().toString();

                InputStream inputStream = null;
                URL url;
                String urlstring = "http://209.249.227.63:8280/accountbalance/v1/";
                String jsonString = "{\"rechargeAmount\" : value}";
                jsonString = jsonString.replace("value",amountValue);
                try {
                    urlstring = urlstring+msisdnValue;
                    url = new URL(urlstring);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Authorization", "Bearer 922f38fe-d4a7-3872-b995-83886ef7c00f");

                    DataOutputStream localDataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                    localDataOutputStream.writeBytes(jsonString);
                    localDataOutputStream.flush();
                    localDataOutputStream.close();

                    urlConnection.connect();

                    Log.d("URL", url.toString());
                    Log.d("JSON", jsonString);

                    String statusString;
                    int status = urlConnection.getResponseCode();
                    if (status != HttpURLConnection.HTTP_OK) {
                        inputStream = urlConnection.getErrorStream();
                        statusString = "Status : ERROR";
                    }else {
                        statusString = "Status : SUCCESS";
                        inputStream = urlConnection.getInputStream();
                    }

                    statusview.setText(statusString);
                    String resultJson = IOUtils.toString(inputStream, "UTF-8");
                    Log.d("RESULT", resultJson);

                    JSONObject reader = new JSONObject(resultJson);
                    JSONObject accinfo  = reader.getJSONObject("accountInfo");
                    String balance = accinfo.getString("balance");

                    String currentBalalnce = "Current Balance : "+balance;
                    balanceview.setText(currentBalalnce);
                    Log.d("Balance",balance);

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
    }
}
