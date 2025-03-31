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
import java.text.SimpleDateFormat;
import java.util.Date;

// BasePlusCommissionEmployee Class
class BasePlusCommissionEmployee extends CommissionEmployee {
    private double baseSalary;

    public BasePlusCommissionEmployee(String name, String SSN, double sales, double commissionRate, double baseSalary) throws InvalidEmployeeDataException {
        super(name, SSN, sales, commissionRate);
        this.baseSalary = baseSalary;
    }

    @Override
    public double getPaymentAmount() {
        return super.getPaymentAmount() + baseSalary;
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("paystub.txt", true)) {
            writer.write("Employee: " + getName() + ", Payment: " + getPaymentAmount() +
                    ", Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}