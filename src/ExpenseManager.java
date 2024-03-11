import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExpenseManager {
    private Map<String, BigDecimal> expenses;

    public ExpenseManager() {
        expenses = new HashMap<>();
    }
}
