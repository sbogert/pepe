package com.example.pepe;

import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MapDBAccess {

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
}
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
