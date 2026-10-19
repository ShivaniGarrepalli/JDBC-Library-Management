
package com.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.exception.IssueNotFoundException;

public class FineCalculation {

    public static void calculateFine() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Enter issue ID:");
            int issueId = sc.nextInt();

            if (issueId <= 0) {
                throw new IssueNotFoundException("Invalid issue ID.");
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            String sql = "select issue_date, return_date, fine from book_issues where issue_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, issueId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                java.sql.Date issueDate = rs.getDate("issue_date");
                java.sql.Date returnDate = rs.getDate("return_date");

                double fine = 0;

                if (returnDate != null) {
                    fine = rs.getDouble("fine");
                    System.out.println("Book returned on: " + returnDate);

                } else {
                    long days = (System.currentTimeMillis() - issueDate.getTime()) / 86400000;

                    if (days > 14) {
                        fine = (days - 14) * 5;
                    }

                    System.out.println("Book has not been returned.");
                }

                System.out.println("Issue Date: " + issueDate);
                System.out.println("Fine: Rs. " + fine);

            } else {
                throw new IssueNotFoundException("Issue record not found.");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (IssueNotFoundException e) {
            System.out.println("Custom Exception: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Please enter a valid issue ID.");
        }
    }
}
