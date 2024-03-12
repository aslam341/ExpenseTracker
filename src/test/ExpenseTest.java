import main.Expense;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTest {

    @Test
    public void getName_ValidName_Success() {
        Expense expense = new Expense("Alice", BigDecimal.valueOf(40));
        assertEquals("Alice", expense.getName());
    }

    @Test
    public void getCost_ValidCost_Success() {
        Expense expense = new Expense("Bob", BigDecimal.valueOf(80.50));
        assertEquals(BigDecimal.valueOf(80.50), expense.getCost());
    }
}
