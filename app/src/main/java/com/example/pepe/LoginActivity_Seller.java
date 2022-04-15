package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pepe.map.MapsActivity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import okhttp3.Authenticator;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.TlsVersion;

public class LoginActivity_Seller extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button login;
    private Button signup;
    private static final String url = "jdbc:mysql://localhost:3306/CS310project";
    private static final String user = "root";
    private static final String pass = "root";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seller);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.signupButton);
        signup = (Button) findViewById(R.id.loginButton);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String proxyHost = System.getProperty("http.proxyHost");
                String proxyPort = System.getProperty("http.proxyPort");
                String proxyUser = System.getProperty("http.proxyUser");
                String proxyPassword = System.getProperty("http.proxyPassword");
                System.out.println("Using proxy with host: "+ proxyHost + ", port: "+ proxyPort + ", user: " + proxyUser);
                getUserDB(Name.getText().toString(), Password.getText().toString());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });
    }

    //Enter name for signup page
    private void openSignUp() {
        Intent intent = new Intent(this, SignupActivity_Sellers.class);
        startActivity(intent);
    }

        private void getUserDB(String givenName, String givenPass) {
            Intent i = new Intent(this, MapsActivity.class);
            String url = "http://localhost:3306/310project";

            // prints the user and pass twice
            // goes through all of this each time login is clicked, dont do that
            // find way to only sent request first time
//        .header("Connection", "close")
//        .header("Accept-Encoding", "identity")
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                    .supportsTlsExtensions(true)
                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
                    .build();

            Authenticator proxyAuthenticator = new Authenticator() {
                @Override public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic(user, pass);
                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                }
            };

            OkHttpClient client =
                    new OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)).build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/drinkers").newBuilder();
            urlBuilder.addQueryParameter("username", givenName);
            String fullUrl = urlBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(fullUrl)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                System.out.println(response.body().string());
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
//        // github says these errors are on the server side
//        Call call  = client.newCall(request);
//        call.enqueue(new Callback() {
//            public void onFailure( Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            public void onResponse(Call call, Response response) throws IOException {
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    }
//
//                    assert responseBody != null;
//                    System.out.println("N   " + responseBody.string() + "    N");
//
//                    Gson gson =
//                            new GsonBuilder().registerTypeAdapter(UserProfiles.class,
//                                    new MyDeserializer()).create();
//                    userProfiles = gson.fromJson(Objects.requireNonNull(response.body()).string(),
//                            UserProfiles.class);
//
//                } finally {
//                    response.close();
//                    String id = userProfiles.findUser(givenName, givenPass).getId();
//                    i.putExtra("USERID", id);
//                }
//
//            }
//        });
            startActivity(i);
        }
    }
//
//    private void validate(String username, String password) throws Exception {
//        try {
//            //establish connection
//            //Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection(url, user, pass);
//            Statement stmt = con.createStatement();
//
//            //database name
//            ResultSet rs = stmt.executeQuery("select * from sellers");
//            while (rs.next()) {
//                String u = rs.getString("username");
//                String p = rs.getString("password");
//                //user exists
//                if (u == username && p == password) {
//                    //get user id
//                    rs = stmt.executeQuery("select id from sellers where username = " + username);
//                    Integer userID = rs.getInt("id");
//                    Intent i = new Intent(this, MapsActivity.class);
//                    i.putExtra("USERID",userID);
//                    startActivity(i);
//                }
//                //user does not exist
//                else {
//                    Info.setText("@string/incorrect_login");
//                }
//            }
//        } catch (SQLException err) {
//            System.out.println(err.getMessage());
//        }
//    }
//}

    /*
     private void t() throws Exception {
        URL url = new URL("http://localhost:3001");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");



        HttpGet request = new HttpGet("https://");

        // add request headers
        request.addHeader("custom-key", "mkyong");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        }
    }
     */
