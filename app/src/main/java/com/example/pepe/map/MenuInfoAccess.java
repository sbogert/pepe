package com.example.pepe.map;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class MenuInfoAccess {
    public static StoreLocationArray getMarkerz() throws ClassNotFoundException {
        String sqlSelectUser = "SELECT * FROM SELLERS";
        String connectionUrl = "jdbc:mysql://localhost:3306/CS310project";
        StoreLocationArray storeLocArray = new StoreLocationArray();
        StoreLocation storeLocation;
        Class.forName("com.mysql.jdbc.Driver");

        // query database to get the user's id
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "");
             PreparedStatement ps = conn.prepareStatement(sqlSelectUser);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                double storeLat = rs.getDouble("latitude");
                double storeLong = rs.getDouble("longitude");
                String storeName = rs.getString("username");
                storeLocation = new StoreLocation(storeName, storeLat, storeLong);
                storeLocArray.addStore(storeLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // handle the exception
        }
        return storeLocArray;

    }
}


    // i think i need to have some type of handler for this so that there are no issues
    // stack overflow example explains it but I haven't changed anythings2
//    /**
//     * for being able to move between different menus and the map
//     * https://www.geeksforgeeks.org/http-headers/#
//     *  It is a request type header. This is use to hold the previous page link where this new page come, that the back button of the browsers can work.
//     */
//    public OkHttpClient menuClient = null;
//
//    public void sendGet() {
//
//        if (menuClient == null) {
//            menuClient = new OkHttpClient();
//        }
//
//        // trying port 3000 from 3306 nownp
//        String url = "http://10.0.2.2:3001/310project/sellers";
//        Request request = new Request.Builder().url(url).addHeader("Connection", "close").get().build();
//
//        System.out.println("Connected");
//
//        menuClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println("onResponse");
//
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    }
//
//                    System.out.println(responseBody.string());
//
//                    Gson gson =
//                            new GsonBuilder().registerTypeAdapter(StoreLocation.class,
//                                    new MyDeserializer()).create();
////                    return gson.fromJson(Objects.requireNonNull(response.body()).string(),
////                            StoreLocation.class);
//
//                }
//
//                finally {
//                   response.close();
//                }
//
//            }
//
//
//        });
////        Call call = getClient().newCall(request);
////        call.enqueue(callback);
////
////        return call;
//    }
//
//    public void getLocations() {
//    }
//    // Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
//    class MyDeserializer implements JsonDeserializer<StoreLocationArray> {
//        @Override
//        public StoreLocationArray deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
//            JsonElement content = je.getAsJsonObject();
//
//            return new Gson().fromJson(content, StoreLocationArray.class);
//        }
//    }
//
//
//}
//
//
//
//
//
////    String getMapMarkers () {
////
////        String responseBody = null;
////
////        Request request = new Request.Builder()
////                .url(url)
////                .build();
////
////        try (Response response = client.newCall(request).execute()) {
////            responseBody = response.body().string();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } finally {
////
////             // note that line breaks disappeared after parsing it to string
////            String[] parts = answer.split("#");
////
////            for (String point : parts) {
////                String[] pointData = point.split(",");
////                Float lat= Float.parseFloat(pointData[0]);
////                Float lng= Float.parseFloat(pointData[1]);
/////// check if fit your actual map, remember you need everything required here loaded and declared before calling.
////                mMap.addMarker(new MarkerOptions()
////                        .position(new LatLng(lat, lng))
////                        .title("Hello world"));
////
////
////            }
////    }
//
////    private Connection conn = null;
////    private Statement stmt = null;
////    private ResultSet rs = null;
////
////    private ResultSet getInfo() {
////        try {
////            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=java&password=root");
////            stmt = conn.createStatement();
////            rs = stmt.executeQuery("SELECT latitude AND longitude FROM sellers");
////            // Do something with result set
////            System.out.println(rs);
////
////
////        } catch (SQLException e) {
////            // handle any errors
////            System.out.println("SQLException: " + e.getMessage());
////            System.out.println("SQLState: " + e.getSQLState());
////            System.out.println("VendorError: " + e.getErrorCode());
////        }
////        finally {
////            return rs;
////            if (rs != null) {
////                try {
////                    rs.close();
////                } catch (SQLException e) {}
////
////                rs = null;
////            }
////            if (stmt != null) {
////                try {
////                    stmt.close();
////                } catch (SQLException e) {}
////
////                stmt = null;
////            }
////        }
////    }
//
////    private void validate(String username, String password) throws Exception {
////        try {
////            //establish connection
////            //Class.forName("com.mysql.jdbc.Driver");
////            Connection con = DriverManager.getConnection(url, user, pass);
////            Statement stmt = con.createStatement();
////
////            //database name
////            ResultSet rs = stmt.executeQuery("select * from users");
////            while (rs.next()) {
////                String u = rs.getString("username");
////                String p = rs.getString("password");
////                //user exists
////                if (u == username && p == password) {
////                    //get user id
////                    rs = stmt.executeQuery("select id from users where username = " + username);
////                    Integer userID = rs.getInt("id");
////                    Intent i = new Intent(this, MapsActivity.class);
////                    i.putExtra("USERID",userID);
////                    startActivity(i);
////                }
////                //user does not exist
////                else {
////                    Info.setText("Username or Password Incorrect");
////                }
////            }
////        } catch (SQLException err) {
////            System.out.println(err.getMessage());
////        }
////
//////    // in results set (select * from blah blah blah)
//////    private void sendGet() throws Exception {
//////        // verify url
//////        String url = "jdbc:mysql://localhost:3001/310project";
//////        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
//////        httpClient.setRequestMethod("GET");
//////
//////        // request handler
//////        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
//////
//////        int responseCode = httpClient.getResponseCode();
//////
//////        try (BufferedReader in = new BufferedReader(
//////                new InputStreamReader(httpClient.getInputStream()))) {
//////            StringBuilder response = new StringBuilder();
//////            String line;
//////
//////            while ((line = in.readLine()) != null) {
//////                response.append(line);
//////            }
//////        }
//////    }
////}
