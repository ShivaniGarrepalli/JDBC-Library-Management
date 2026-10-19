
package com.management;

import java.sql.*;
import java.util.Scanner;

public class SearchBook {

    public static void searchBook() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter book name to search: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Please enter a book name.");
                return;
            }

            if (name.matches("[0-9]+")) {
                System.out.println("Book name cannot contain only numbers.");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            String query = "select * from books where book_name like ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("------------------------");
            }

            if (!found) {
                System.out.println("Book not found.");
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
