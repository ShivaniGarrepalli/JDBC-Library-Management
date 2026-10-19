
package com.management;

import java.sql.*;

public class ViewMembers {

    public static void viewMembers() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            String query = "select * from members";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Member ID: " + rs.getInt("member_id"));
                System.out.println("Member Name: " + rs.getString("member_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
