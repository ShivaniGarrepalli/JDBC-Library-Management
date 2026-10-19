
package com.management;

import java.sql.*;
import java.util.Scanner;
import com.exception.BookNotFoundException;
import com.exception.InvalidBookDetailsException;

public class UpdateBook {

    public static void updateBook() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter book ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new book name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter new author: ");
            String author = sc.nextLine().trim();

            System.out.print("Enter new quantity: ");
            int quantity = sc.nextInt();

            if (id <= 0) {
                throw new BookNotFoundException("Book ID must be greater than zero.");
            }

            if (name.isEmpty() || author.isEmpty() || quantity < 0) {
                throw new InvalidBookDetailsException("Enter valid book details.");
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            String checkQuery = "select book_id from books where book_id = ?";
            PreparedStatement ps1 = con.prepareStatement(checkQuery);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                rs.close();
                ps1.close();
                con.close();
                throw new BookNotFoundException("Book not found.");
            }

            rs.close();
            ps1.close();

            String updateQuery = "update books set book_name = ?, author = ?, quantity = ? where book_id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateQuery);
            ps2.setString(1, name);
            ps2.setString(2, author);
            ps2.setInt(3, quantity);
            ps2.setInt(4, id);

            ps2.executeUpdate();

            ps2.close();
            con.close();

            System.out.println("Book updated successfully.");

        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (InvalidBookDetailsException e) {
            System.out.println(e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
