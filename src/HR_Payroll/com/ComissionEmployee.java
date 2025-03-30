package HR_Payroll.com;

import java.io.FileWriter;
import java.io.IOException;

// CommissionEmployee Class
class CommissionEmployee extends Employee {
    private double grossSales;
    private double commissionRate;

    public CommissionEmployee(String name, String SSN, double sales, double commissionRate) throws InvalidEmployeeDataException {
        super(name, SSN);
        if (sales < 0 || commissionRate < 0) {
            throw new InvalidEmployeeDataException("Sales and commission rate must be non-negative.");
        }
        this.grossSales = sales;
        this.commissionRate = commissionRate;
    }

    @Override
    public double getPaymentAmount() {
        return grossSales * commissionRate;
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("paystub.txt", true)) {
            writer.write("Employee: " + getName() + ", Payment: " + getPaymentAmount() + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}