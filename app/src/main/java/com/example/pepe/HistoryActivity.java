package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://192.168.0.192:3306/myDB";
    private static final String user = "hitesh";
    private static final String pass = "1234";
    TextView txtData;
    ListView listView;
    List itemList = new ArrayList();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

//        listView = (ListView) findViewById(R.id.menuListView);

        // for loop will add each element from the list of menu items that is passed here
        for (int i=0; i < 5; i++) {
            itemList.add(i);
        }

        arrayAdapter = new ArrayAdapter(HistoryActivity.this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(arrayAdapter);
    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(MapsActivity.this, "Please wait...", Toast.LENGTH_SHORT)
//                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select distinct Country from tblCountries");
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result += rs.getString(1).toString() + "\n";
                }
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            txtData.setText(result);
        }
    }

}
