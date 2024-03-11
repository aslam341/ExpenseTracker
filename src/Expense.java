import java.math.BigDecimal;

/**
 * Represents an expense incurred by a friend.
 */
public class Expense {
    private String name;
    private BigDecimal cost;

    /**
     * Constructs an Expense object with the specified name and cost.
     *
     * @param name The name of the friend associated with the expense.
     * @param cost The cost of the expense as a BigDecimal.
     */
    public Expense(String name, BigDecimal cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * Gets the name of the friend associated with the expense.
     *
     * @return The name of the friend.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the cost of the expense.
     *
     * @return The cost of the expense as a BigDecimal.
     */
    public BigDecimal getCost() {
        return this.cost;
    }
}