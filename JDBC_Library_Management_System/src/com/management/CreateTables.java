
package com.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTables {

    public static boolean createDatabaseAndTables() {

        String url = "jdbc:mysql://localhost:3306/librarymanagement", username = "root", password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();

            st.executeUpdate("create table if not exists books (book_id int primary key auto_increment, book_name varchar(100) not null, author varchar(100) not null, quantity int not null)");

            st.executeUpdate("create table if not exists members (member_id int primary key auto_increment, member_name varchar(100) not null, email varchar(100) not null unique, phone varchar(15) not null)");

            st.executeUpdate("create table if not exists book_issues (issue_id int primary key auto_increment, book_id int not null, member_id int not null, issue_date date not null, return_date date null, fine decimal(8,2) not null default 0, foreign key (book_id) references books(book_id), foreign key (member_id) references members(member_id))");

            st.close();
            con.close();

            System.out.println("Database tables created successfully.");

            return true;

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");

        } catch (SQLException e) {
            System.out.println("Database setup failed: " + e.getMessage());
        }

        return false;
    }
}
