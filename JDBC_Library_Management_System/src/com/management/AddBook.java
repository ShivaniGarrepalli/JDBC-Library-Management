package com.management;

import java.sql.*;
import java.util.Scanner;
import com.exception.InvalidBookDetailsException;

public class AddBook {
    public static void addBook() {
        String url = "jdbc:mysql://localhost:3306/librarymanagement",username = "root",password = "root";
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter book name: ");
            String name = sc.nextLine();
            System.out.print("Enter author name: ");
            String author = sc.nextLine();
            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(sc.nextLine());
            if (name.trim().isEmpty() || author.trim().isEmpty() || qty <= 0) {
                throw new InvalidBookDetailsException("Book name and author are required. Quantity must be greater than zero.");
            }
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            String query = "insert into books(book_name, author, quantity) values(?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, author);
            ps.setInt(3, qty);
            ps.executeUpdate();
            System.out.println("Book added successfully.");
            ps.close();
            con.close();
        } catch (InvalidBookDetailsException e) {
            System.out.println("Invalid book details: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for quantity.");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
