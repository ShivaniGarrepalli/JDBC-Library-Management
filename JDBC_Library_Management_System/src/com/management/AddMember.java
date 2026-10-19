package com.management;

import java.sql.*;
import java.util.Scanner;
import com.exception.InvalidMemberDetailsException;

public class AddMember {

    public static void addMember() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root",password = "root";

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter member name: ");
            String name = sc.nextLine();

            System.out.print("Enter email: ");
            String email = sc.nextLine();

            System.out.print("Enter phone number: ");
            String phone = sc.nextLine();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                throw new InvalidMemberDetailsException("All fields are required.");
            }

            if (!email.contains("@") || !email.contains(".")) {
                throw new InvalidMemberDetailsException("Please enter a valid email address.");
            }

            if (!phone.matches("[0-9]{10}")) {
                throw new InvalidMemberDetailsException("Phone number must contain exactly 10 digits.");
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            String query = "insert into members(member_name, email, phone) values(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.executeUpdate();
            System.out.println("Member added successfully.");
            ps.close();
            con.close();
        } catch (InvalidMemberDetailsException e) {
            System.out.println("Invalid member details: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
