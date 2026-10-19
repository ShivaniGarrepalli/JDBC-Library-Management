
package com.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewIssuedBooks {

    public static void viewIssuedBooks() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            String sql = "select bi.issue_id, b.book_name, m.member_name, bi.issue_date, bi.return_date, bi.fine from book_issues bi join books b on bi.book_id = b.book_id join members m on bi.member_id = m.member_id order by bi.issue_id desc";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("===== BOOK ISSUE HISTORY =====");

            while (rs.next()) {
                found = true;

                System.out.println("Issue ID: " + rs.getInt("issue_id"));
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Member Name: " + rs.getString("member_name"));
                System.out.println("Issue Date: " + rs.getDate("issue_date"));
                System.out.println("Return Date: " + rs.getDate("return_date"));
                System.out.println("Fine: Rs. " + rs.getDouble("fine"));
                System.out.println("----------------------");
            }

            if (!found) {
                System.out.println("No issue records found.");
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
