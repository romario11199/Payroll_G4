/*
Griffiths Marlon - 2301010569
Romario McLymont - 2301010275
Delvian Brown - 230101767
Shanelle Farqurson -
Jhenelle Walker -
*/

package HR_Payroll.com;

import java.io.FileWriter;
import java.io.IOException;

// SalariedEmployee Class
public class SalariedEmployee extends Employee {
    private double weeklySalary;

    public SalariedEmployee(String name, String SSN, double weeklySalary) {
        super(name, SSN);
        this.weeklySalary = weeklySalary;
    }

    @Override
    public double getPaymentAmount() {
        return weeklySalary;
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