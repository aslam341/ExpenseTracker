import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExpenseManager {
    private Map<String, BigDecimal> expenses;

    public ExpenseManager() {
        expenses = new HashMap<>();
    }

    public void addExpense(Expense expense) {
        BigDecimal zeroValue = new BigDecimal(0);
        BigDecimal currentExpense = expenses.getOrDefault(expense.getName(), zeroValue);
        BigDecimal totalExpense = currentExpense.add(expense.getCost());
        expenses.put(expense.getName(), totalExpense);
    }
}
