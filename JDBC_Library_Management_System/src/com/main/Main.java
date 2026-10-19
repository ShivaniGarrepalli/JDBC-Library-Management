package com.main;

import java.util.Scanner;

import com.management.CreateTables;
import com.management.AddBook;
import com.management.ViewBooks;
import com.management.SearchBook;
import com.management.UpdateBook;
import com.management.DeleteBook;
import com.management.AddMember;
import com.management.ViewMembers;
import com.management.IssueBook;
import com.management.ReturnBook;
import com.management.ViewIssuedBooks;
import com.management.FineCalculation;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Create the database tables before using the menu
        if (!CreateTables.createDatabaseAndTables()) {
            System.out.println("Unable to initialize database. Program stopped.");
            return;
        }

        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add book");
            System.out.println("2. View books");
            System.out.println("3. Search book");
            System.out.println("4. Update book");
            System.out.println("5. Delete book");
            System.out.println("6. Add member");
            System.out.println("7. View members");
            System.out.println("8. Issue book");
            System.out.println("9. Return book");
            System.out.println("10. View issued books");
            System.out.println("11. Calculate fine");
            System.out.println("0. Exit");
            System.out.println("Enter your choice:");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": AddBook.addBook(); break;
                case "2": ViewBooks.viewBooks(); break;
                case "3": SearchBook.searchBook(); break;
                case "4": UpdateBook.updateBook(); break;
                case "5": DeleteBook.deleteBook(); break;
                case "6": AddMember.addMember(); break;
                case "7": ViewMembers.viewMembers(); break;
                case "8": IssueBook.issueBook(); break;
                case "9": ReturnBook.returnBook(); break;
                case "10": ViewIssuedBooks.viewIssuedBooks(); break;
                case "11": FineCalculation.calculateFine(); break;
                case "0":
                    System.out.println("Program closed.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
