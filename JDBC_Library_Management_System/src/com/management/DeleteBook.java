package com.management;

import java.sql.*;
import java.util.Scanner;
import com.exception.BookNotFoundException;

public class DeleteBook {

    public static void deleteBook() {

    	String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root",password = "root";
    	Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter book ID to delete: ");
            int id = sc.nextInt();

            if (id <= 0) {
                throw new BookNotFoundException("Book ID must be greater than zero.");
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            String checkQuery = "select book_id from books where book_id = ?";
            PreparedStatement check = con.prepareStatement(checkQuery);
            check.setInt(1, id);
            ResultSet rs = check.executeQuery();
            if (!rs.next()) {
                throw new BookNotFoundException("Book not found.");
            }
            rs.close();
            check.close();
            String issueQuery = "select issue_id from book_issues where book_id = ? and return_date is null";
            PreparedStatement issued = con.prepareStatement(issueQuery);
            issued.setInt(1, id);
            ResultSet rs2 = issued.executeQuery();
            if (rs2.next()) {
                System.out.println("Cannot delete a book that is currently issued.");
                rs2.close();
                issued.close();
                con.close();
                return;
            }
            rs2.close();
            issued.close();
            String deleteQuery = "delete from books where book_id = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Book deleted successfully.");
            ps.close();
            con.close();
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid book ID.");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
