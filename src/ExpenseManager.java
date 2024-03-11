import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    private Map<String, BigDecimal> expenses;

    public ExpenseManager() {
        expenses = new HashMap<>();
    }

    public void addExpense(Expense expense) {
        BigDecimal zeroValue = new BigDecimal(0.00);
        BigDecimal currentExpense = expenses.getOrDefault(expense.getName(), zeroValue);
        BigDecimal totalExpense = currentExpense.add(expense.getCost());
        expenses.put(expense.getName(), totalExpense);
    }

    public List<String> settleExpense() {
        List<String> transactions = new ArrayList<>();
        List<String> borrowers = new ArrayList<>();
        List<String> lenders = new ArrayList<>();

        BigDecimal totalSumOfExpenses = calculateTotalExpenses();
        int numberOfPeople = expenses.size();
        BigDecimal amountForEachPerson = totalSumOfExpenses.divide(new BigDecimal(numberOfPeople),
                2, RoundingMode.HALF_UP);

        categorizeLendersAndBorrowers(lenders, borrowers, amountForEachPerson);

        processTransactions(transactions, lenders, borrowers);

        return transactions;
    }

    private BigDecimal calculateTotalExpenses() {
        BigDecimal totalSumOfExpenses = BigDecimal.valueOf(0.00);
        for (BigDecimal cost : expenses.values()) {
            totalSumOfExpenses = totalSumOfExpenses.add(cost);
        }
        return totalSumOfExpenses;
    }

    private void categorizeLendersAndBorrowers(List<String> lenders,
                                               List<String> borrowers,
                                               BigDecimal amountForEachPerson) {
        BigDecimal zeroValue = new BigDecimal(0.00);
        for (Map.Entry<String, BigDecimal> entry : expenses.entrySet()) {
            BigDecimal balanceOwed = entry.getValue().subtract(amountForEachPerson);
            expenses.put(entry.getKey(), balanceOwed);
            if (balanceOwed.compareTo(zeroValue) == 1) {
                lenders.add(entry.getKey());
            } else if (balanceOwed.compareTo(zeroValue) == -1) {
                borrowers.add(entry.getKey());
            }
        }
    }

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
