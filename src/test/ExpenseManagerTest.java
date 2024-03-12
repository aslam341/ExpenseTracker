import main.Expense;
import main.ExpenseManager;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseManagerTest {

    @Test
    public void settleExpense_OnePersonInvolvedInTwoTransactionsDecimal_Success() {
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.addExpense(new Expense("Ali", BigDecimal.valueOf(40.105)));
        expenseManager.addExpense(new Expense("Bob", BigDecimal.valueOf(40.105)));
        expenseManager.addExpense(new Expense("Charlie", BigDecimal.valueOf(10)));

        List<String> transactions = expenseManager.settleExpense();

        assertEquals(2, transactions.size());
        assertEquals("Charlie pays Bob $10.04", transactions.get(0));
        assertEquals("Charlie pays Ali $10.04", transactions.get(1));
    }

    @Test
    public void settleExpense_OnePersonInvolvedInTwoTransactions_Success() {
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.addExpense(new Expense("Ali", BigDecimal.valueOf(40)));
        expenseManager.addExpense(new Expense("Bob", BigDecimal.valueOf(40)));
        expenseManager.addExpense(new Expense("Charlie", BigDecimal.valueOf(10)));

        List<String> transactions = expenseManager.settleExpense();

        assertEquals(2, transactions.size());
        assertEquals("Charlie pays Bob $10.00", transactions.get(0));
        assertEquals("Charlie pays Ali $10.00", transactions.get(1));
    }

    @Test
    public void settleExpense_FourPeopleSmallTransactions_Success() {
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.addExpense(new Expense("Ali", BigDecimal.valueOf(10)));
        expenseManager.addExpense(new Expense("Bob", BigDecimal.valueOf(20)));
        expenseManager.addExpense(new Expense("Charlie", BigDecimal.valueOf(0)));
        expenseManager.addExpense(new Expense("Don", BigDecimal.valueOf(10)));

        List<String> transactions = expenseManager.settleExpense();

        assertEquals(1, transactions.size());
        assertEquals("Charlie pays Bob $10.00", transactions.get(0));
    }

    @Test
    public void settleExpense_FourPeopleMediumTransactions_Success() {
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.addExpense(new Expense("Ali", BigDecimal.valueOf(10)));
        expenseManager.addExpense(new Expense("Bob", BigDecimal.valueOf(20)));
        expenseManager.addExpense(new Expense("Charlie", BigDecimal.valueOf(0)));
        expenseManager.addExpense(new Expense("Don", BigDecimal.valueOf(10)));

        List<String> transactions = expenseManager.settleExpense();

        assertEquals(1, transactions.size());
        assertEquals("Charlie pays Bob $10.00", transactions.get(0));
    }

    @Test
    public void settleExpense_FourPeopleOneHighTransaction_Success() {
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.addExpense(new Expense("Alice", BigDecimal.valueOf(200)));
        expenseManager.addExpense(new Expense("Bob", BigDecimal.valueOf(80)));
        expenseManager.addExpense(new Expense("Charlie", BigDecimal.valueOf(50)));
        expenseManager.addExpense(new Expense("Don", BigDecimal.valueOf(20)));

        List<String> transactions = expenseManager.settleExpense();

        assertEquals(3, transactions.size());
        assertEquals("Don pays Alice $67.50", transactions.get(0));
        assertEquals("Bob pays Alice $7.50", transactions.get(1));
        assertEquals("Charlie pays Alice $37.50", transactions.get(2));
    }

    @Test
    public void settleExpense_FourPeopleTwoHighTransactions_Success() {
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.addExpense(new Expense("Alice", BigDecimal.valueOf(160)));
        expenseManager.addExpense(new Expense("Bob", BigDecimal.valueOf(120)));
        expenseManager.addExpense(new Expense("Charlie", BigDecimal.valueOf(50)));
        expenseManager.addExpense(new Expense("Don", BigDecimal.valueOf(20)));

        List<String> transactions = expenseManager.settleExpense();

        assertEquals(3, transactions.size());
        assertEquals("Don pays Bob $32.50", transactions.get(0));
        assertEquals("Don pays Alice $35.00", transactions.get(1));
        assertEquals("Charlie pays Alice $37.50", transactions.get(2));
    }
}
