
package com.management;

import java.sql.*;
import java.util.Scanner;
import com.exception.IssueNotFoundException;

public class ReturnBook {

    public static void returnBook() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter issue ID: ");
            int issueId = sc.nextInt();

            if (issueId <= 0) {
                throw new IssueNotFoundException("Issue ID must be greater than zero.");
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            String checkQuery = "select book_id from book_issues where issue_id = ? and return_date is null";
            PreparedStatement ps1 = con.prepareStatement(checkQuery);
            ps1.setInt(1, issueId);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                rs.close();
                ps1.close();
                con.close();
                throw new IssueNotFoundException("Issue record not found or book already returned.");
            }

            int bookId = rs.getInt("book_id");
            rs.close();
            ps1.close();

            String returnQuery = "update book_issues set return_date = curdate(), fine = greatest(datediff(curdate(), issue_date) - 14, 0) * 5 where issue_id = ?";
            PreparedStatement ps2 = con.prepareStatement(returnQuery);
            ps2.setInt(1, issueId);
            ps2.executeUpdate();
            ps2.close();

            String quantityQuery = "update books set quantity = quantity + 1 where book_id = ?";
            PreparedStatement ps3 = con.prepareStatement(quantityQuery);
            ps3.setInt(1, bookId);
            ps3.executeUpdate();
            ps3.close();

            String fineQuery = "select fine from book_issues where issue_id = ?";
            PreparedStatement ps4 = con.prepareStatement(fineQuery);
            ps4.setInt(1, issueId);
            ResultSet rs2 = ps4.executeQuery();

            if (rs2.next()) {
                System.out.println("Fine amount: Rs. " + rs2.getDouble("fine"));
            }

            rs2.close();
            ps4.close();
            con.close();

            System.out.println("Book returned successfully.");

        } catch (IssueNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
