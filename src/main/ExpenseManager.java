package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages expenses and calculates transactions to settle debts among friends.
 */
public class ExpenseManager {
    private Map<String, BigDecimal> expenses;

    /**
     * Initializes the ExpenseManager with an empty expenses map.
     */
    public ExpenseManager() {
        expenses = new HashMap<>();
    }

    /**
     * Adds an expense for a friend.
     *
     * @param expense The expense to be added.
     */
    public void addExpense(Expense expense) {
        BigDecimal zeroValue = new BigDecimal(0.00);
        BigDecimal currentExpense = expenses.getOrDefault(expense.getName(), zeroValue);
        BigDecimal totalExpense = currentExpense.add(expense.getCost());
        expenses.put(expense.getName(), totalExpense);
    }

    /**
     * Calculates transactions to settle debts among friends and returns a list of transactions.
     *
     * @return A list of transactions.
     */
    public List<String> settleExpense() {
        List<String> transactions = new ArrayList<>();
        List<String> borrowers = new ArrayList<>();
        List<String> lenders = new ArrayList<>();

        BigDecimal totalSumOfExpenses = calculateTotalExpenses();
        int numberOfPeople = expenses.size();
        BigDecimal avgExpenseForEachPerson = totalSumOfExpenses.divide(new BigDecimal(numberOfPeople),
                2, RoundingMode.HALF_UP);

        categorizeLendersAndBorrowers(lenders, borrowers, avgExpenseForEachPerson);

        processTransactions(transactions, lenders, borrowers);

        return transactions;
    }

    /**
     * Calculates the total sum of expenses for all friends.
     *
     * @return The total sum of expenses.
     */
    private BigDecimal calculateTotalExpenses() {
        BigDecimal totalSumOfExpenses = BigDecimal.valueOf(0.00);
        for (BigDecimal cost : expenses.values()) {
            totalSumOfExpenses = totalSumOfExpenses.add(cost);
        }
        return totalSumOfExpenses;
    }

    /**
     * Categorizes friends into lenders and borrowers based on their balance.
     *
     * @param lenders The list of lenders.
     * @param borrowers The list of borrowers.
     * @param avgExpenseForEachPerson The average amount spent by each person.
     */
    private void categorizeLendersAndBorrowers(List<String> lenders,
                                               List<String> borrowers,
                                               BigDecimal avgExpenseForEachPerson) {
        BigDecimal zeroValue = new BigDecimal(0.00);
        for (Map.Entry<String, BigDecimal> entry : expenses.entrySet()) {
            BigDecimal balanceOwed = entry.getValue().subtract(avgExpenseForEachPerson);
            expenses.put(entry.getKey(), balanceOwed);
            if (balanceOwed.compareTo(zeroValue) == 1) {
                lenders.add(entry.getKey());
            } else if (balanceOwed.compareTo(zeroValue) == -1) {
                borrowers.add(entry.getKey());
            }
        }
    }

    /**
     * Processes transactions between lenders and borrowers.
     *
     * @param transactions The list to store transactions.
     * @param lenders The list of lenders.
     * @param borrowers The list of borrowers.
     */
    private void processTransactions(List<String> transactions, List<String> lenders, List<String> borrowers) {
        BigDecimal zeroValue = BigDecimal.valueOf(0.00);
        for (String borrower : borrowers) {
            for (String lender : lenders) {
                BigDecimal borrowed = expenses.get(borrower).abs();
                BigDecimal lent = expenses.get(lender);
                BigDecimal minimumPayment = borrowed.min(lent);
                if (borrowed.compareTo(zeroValue) == 0 || minimumPayment.compareTo(zeroValue) == 0) {
                    continue;
                }

                expenses.put(borrower, minimumPayment.subtract(borrowed));
                expenses.put(lender, lent.subtract(minimumPayment));
                String transaction = borrower + " pays " + lender
                        + " $" + minimumPayment.setScale(2, RoundingMode.HALF_UP);
                transactions.add(transaction);
            }
        }
    }
}
