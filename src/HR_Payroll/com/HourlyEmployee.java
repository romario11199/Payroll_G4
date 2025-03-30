/*
Griffiths Marlon - 2301010569
Romario McLymont - 2301010275
Delvian Brown -
Shanelle Farqurson -
*/

package HR_Payroll.com;

import java.io.FileWriter;
import java.io.IOException;

// HourlyEmployee Class
public class HourlyEmployee extends Employee {
    private double wage;
    private double hours;

    public HourlyEmployee(String name, String SSN, double wage, double hours) {
        super(name, SSN);
        this.wage = wage;
        this.hours = hours;
    }

    @Override
    public double getPaymentAmount() {
        return wage * hours;
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
