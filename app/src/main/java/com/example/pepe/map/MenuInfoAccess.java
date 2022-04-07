package com.example.pepe.map;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.pepe.map.StoreLocation;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MenuInfoAccess {

    /**
     * for being able to move between different menus and the map
     * https://www.geeksforgeeks.org/http-headers/#
     *  It is a request type header. This is use to hold the previous page link where this new page come, that the back button of the browsers can work.
     */
    public OkHttpClient client = null;

    public void sendGet() {

        if (client == null) {
            client = new OkHttpClient();
        }

        String url = "http://10.0.2.2:3306/310project/sellers";
        Request request = new Request.Builder().url(url).header("Connection", "close").get().build();
         
        System.out.println("Connected");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("onResponse");

                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    System.out.println(responseBody.string());

                }

                finally {
                   response.close();
                }

            }


        });
//        Call call = getClient().newCall(request);
//        call.enqueue(callback);
//
//        return call;
    }

    public void getLocations() {
    }


}





//    String getMapMarkers () {
//
//        String responseBody = null;
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            responseBody = response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//
//             // note that line breaks disappeared after parsing it to string
//            String[] parts = answer.split("#");
//
//            for (String point : parts) {
//                String[] pointData = point.split(",");
//                Float lat= Float.parseFloat(pointData[0]);
//                Float lng= Float.parseFloat(pointData[1]);
///// check if fit your actual map, remember you need everything required here loaded and declared before calling.
//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(lat, lng))
//                        .title("Hello world"));
//
//
//            }
//    }

//    private Connection conn = null;
//    private Statement stmt = null;
//    private ResultSet rs = null;
//
//    private ResultSet getInfo() {
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=java&password=root");
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery("SELECT latitude AND longitude FROM sellers");
//            // Do something with result set
//            System.out.println(rs);
//
//
//        } catch (SQLException e) {
//            // handle any errors
//            System.out.println("SQLException: " + e.getMessage());
//            System.out.println("SQLState: " + e.getSQLState());
//            System.out.println("VendorError: " + e.getErrorCode());
//        }
//        finally {
//            return rs;
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {}
//
//                rs = null;
//            }
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {}
//
//                stmt = null;
//            }
//        }
//    }

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
//                if (u == username && p == password) {
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
//        } catch (SQLException err) {
//            System.out.println(err.getMessage());
//        }
//
////    // in results set (select * from blah blah blah)
////    private void sendGet() throws Exception {
////        // verify url
////        String url = "jdbc:mysql://localhost:3001/310project";
////        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
////        httpClient.setRequestMethod("GET");
////
////        // request handler
////        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
////
////        int responseCode = httpClient.getResponseCode();
////
////        try (BufferedReader in = new BufferedReader(
////                new InputStreamReader(httpClient.getInputStream()))) {
////            StringBuilder response = new StringBuilder();
////            String line;
////
////            while ((line = in.readLine()) != null) {
////                response.append(line);
////            }
////        }
////    }
//}
