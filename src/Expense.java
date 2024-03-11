public class Expense {
    private String name;
    private Double cost;

    public Expense(String name, Double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public Double getCost() {
        return this.cost;
    }
}