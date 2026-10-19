package com.management;

import java.sql.*;
import java.util.Scanner;
import com.exception.BookNotFoundException;
import com.exception.MemberNotFoundException;
import com.exception.BookNotAvailableException;

public class IssueBook {

    public static void issueBook() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter book ID: ");
            int bookId = sc.nextInt();

            System.out.print("Enter member ID: ");
            int memberId = sc.nextInt();

            if (bookId <= 0 || memberId <= 0) {
                System.out.println("IDs must be greater than zero.");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            String bookQuery = "select quantity from books where book_id = ?";
            PreparedStatement ps1 = con.prepareStatement(bookQuery);
            ps1.setInt(1, bookId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                throw new BookNotFoundException("Book not found.");
            }

            int quantity = rs1.getInt("quantity");
            rs1.close();
            ps1.close();

            if (quantity <= 0) {
                con.close();
                throw new BookNotAvailableException("Book is not available.");
            }

            String memberQuery = "select member_id from members where member_id = ?";
            PreparedStatement ps2 = con.prepareStatement(memberQuery);
            ps2.setInt(1, memberId);
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                rs2.close();
                ps2.close();
                con.close();
                throw new MemberNotFoundException("Member not found.");
            }

            rs2.close();
            ps2.close();

            String issueQuery = "insert into book_issues(book_id, member_id, issue_date, return_date, fine) values(?, ?, curdate(), null, 0)";
            PreparedStatement ps3 = con.prepareStatement(issueQuery);
            ps3.setInt(1, bookId);
            ps3.setInt(2, memberId);
            ps3.executeUpdate();
            ps3.close();

            String updateQuery = "update books set quantity = quantity - 1 where book_id = ?";
            PreparedStatement ps4 = con.prepareStatement(updateQuery);
            ps4.setInt(1, bookId);
            ps4.executeUpdate();
            ps4.close();

            con.close();

            System.out.println("Book issued successfully.");

        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (BookNotAvailableException e) {
            System.out.println(e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
