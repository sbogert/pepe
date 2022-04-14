package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pepe.data.model.UserProfiles;
import com.example.pepe.map.MapsActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/** class for users logging into the app */
public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private static final String url = "http://10.26.10.68:3306/310project/drinkers";
    private static final String user = "root";
    private static final String pass = "root";
    private UserProfiles userProfiles;
    private Throwable throwExcept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        TextView signupInfo = (TextView) findViewById(R.id.haveaccount);
        TextView info = (TextView) findViewById(R.id.incorrect);
        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        info.setText("");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(Name.getText().toString() + " " + Password.getText().toString());
                getUserDB(Name.getText().toString());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });
    }

    //sends user to signup page
    private void openSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void getUserDB(String givenName) {
        // connect to database
        String connectionUrl = "jdbc:mysql://localhost:3306/310project?useSSL=false&useUnicode=true" +
            "&useJDBCCompliantTimeZoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String id = "";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // query database to get the user's id
        try {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(connectionUrl, "root", "root");
                if (conn != null) {
                    System.out.println("Connected to the database!");
                } else {
                    System.out.println("Failed to make connection!");
                }
            } catch (SQLException sqle) {
                System.err.format("SQL State: %s\n%s", sqle.getSQLState(), sqle.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // making sure there is a connection before continuing
            if (conn == null) {
                throw throwExcept;
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id FROM drinkers WHERE username = " + givenName);
            // print the given ID
            id = rs.getString("id");
            System.out.println(rs.getString("id"));
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            // close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignored) {} // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ignored) {} // ignore
                stmt = null;
            }
            // send user to signup if they dont have an account
            if (id == null) {
                openSignUp();
            }
        }
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("USERID", id);
        startActivity(i);
    }
}


//    private void httpGet(){
//        OkHttpClient client = new OkHttpClient();
//
//        // prints the user and pass twice
//        // goes through all of this each time login is clicked, dont do that
//        // find way to only sent request first time
//        Request request = new Request.Builder()
//                .url(url)
//                .header("Connection", "close")
//                .header("Accept-Encoding", "identity")
//                .build();
//
//        System.out.println("Connected");
//
//
//        // github says these errors are on the server side
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    }
//
//                    System.out.println("N   " + responseBody.string() + "    N");
//
//                    Gson gson =
//                            new GsonBuilder().registerTypeAdapter(UserProfiles.class,
//                                    new MyDeserializer()).create();
//                    userProfiles = gson.fromJson(Objects.requireNonNull(response.body()).string(),
//                            UserProfiles.class);
//
//                }
//
//                finally {
//                    response.close();
//                }
//
//            }
//        });

//
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            public void onResponse(Call call, Response response)
//                    throws IOException {
//                // ...
//            }
//
//            public void onFailure(Call call, IOException e) {
//                fail();
//            }
//        });
//    }
//
//
//
//    private void validate(String username, String password) throws Exception {
//        try {
//            //establish connection
//            //Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection(url, user, pass);
//            Statement stmt = con.createStatement();
//
//            //database name
//            ResultSet rs = stmt.executeQuery("select * from users");
//            while (rs.next()) {
//                String u = rs.getString("username");
//                String p = rs.getString("password");
//                //user exists
//                if (u.equals(username) && p.equals(password)) {
//                    //get user id
//                    rs = stmt.executeQuery("select id from users where username = " + username);
//                    Integer userID = rs.getInt("id");
//                    Intent i = new Intent(this, MapsActivity.class);
//                    i.putExtra("USERID",userID);
//                    startActivity(i);
//                }
//                //user does not exist
//                else {
//                    Info.setText("Username or Password Incorrect");
//                }
//            }
//          String userID = getUserDB();
//          // send userID to the next activity
//            if (userID == null) {
//
//            }
//            else {
//                Intent i = new Intent(this, MapsActivity.class);
//                i.putExtra("USERID",userID);
//                startActivity(i);
//            }
//       } catch (Exception e) {
//          System.out.println(e.getMessage());
//       }
//}
////
////    // Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
////    class MyDeserializer implements JsonDeserializer<UserProfiles> {
////        @Override
////        public UserProfiles deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
////            JsonElement content = je.getAsJsonObject();
////
////            return new Gson().fromJson(content, UserProfiles.class);
////        }
////    }
//}
//
//    /*
//     private void t() throws Exception {
//        URL url = new URL("http://localhost:3001");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//
//
//
//        HttpGet request = new HttpGet("https://");
//
//        // add request headers
//        request.addHeader("custom-key", "mkyong");
//
//        try (CloseableHttpResponse response = httpClient.execute(request)) {
//            // Get HttpResponse Status
//            System.out.println(response.getStatusLine().toString());
//
//            HttpEntity entity = response.getEntity();
//            Header headers = entity.getContentType();
//            System.out.println(headers);
//
//            if (entity != null) {
//                // return it as a String
//                String result = EntityUtils.toString(entity);
//                System.out.println(result);
//            }
//        }
//    }
//     */