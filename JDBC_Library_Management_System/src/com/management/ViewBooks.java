
package com.management;

import java.sql.*;

public class ViewBooks {

    public static void viewBooks() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            String query = "select * from books";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
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
