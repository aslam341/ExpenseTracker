package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

/**
 * The main class for managing expenses and calculating transactions to settle debts among friends.
 */
public class Main {
    /**
     * The main method to run the expense management program.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("ExpenseTracker has started!\nEnter names and expense in the following format:\n"
                    + "Alice,10.0\nBob,20.5\nCharlie,43.76\nBob,31.42\netc...\n"
                    + "Press enter without typing anything once you are done adding all expenses for processing\n\n");
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                String name = parts[0];
                BigDecimal cost = new BigDecimal(Double.parseDouble(parts[1]));

                Expense expense = new Expense(name, cost);
                expenseManager.addExpense(expense);
            }

            List<String> transactions = expenseManager.settleExpense();
            printTransactions(transactions);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the list of transactions to the standard output.
     *
     * @param transactions The list of transactions to be printed.
     */
    private static void printTransactions(List<String> transactions) {
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("Number of transactions: " + transactions.size() + "\n");
    }
}