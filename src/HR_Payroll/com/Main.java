/*
Griffiths Marlon - 2301010569
Romario McLymont - 2301010275
Delvian Brown -
Shanelle Farqurson -
*/
package HR_Payroll.com;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create an ArrayList of Payable objects
        ArrayList<Payable> payroll = new ArrayList<>();

        try {
            // Creating different types of employees
            CommissionEmployee commissionEmployee = new CommissionEmployee("John Brown", "123-45-6789", 5000, 0.1);
            HourlyEmployee hourlyEmployee = new HourlyEmployee("Alice Doe", "987-65-4321", 20, 40);
            SalariedEmployee salariedEmployee = new SalariedEmployee("Michael Smith", "111-22-3333", 1000);
            BasePlusCommissionEmployee basePlusCommissionEmployee = new BasePlusCommissionEmployee("Emma Davis", "222-33-4444", 7000, 0.08, 500);


            // Add all to the payroll list
            payroll.add(commissionEmployee);
            payroll.add(hourlyEmployee);
            payroll.add(salariedEmployee);
            payroll.add(basePlusCommissionEmployee);

            // Process payments
            for (Payable payable : payroll) {
                System.out.println("Processing payment for: " + (payable instanceof Employee ? ((Employee) payable).getName() : "Invoice"));
                System.out.println("Payment Amount: $" + payable.getPaymentAmount());
                payable.writeToFile(); // Write to file
                System.out.println("-------------------------------");
            }

        } catch (InvalidEmployeeDataException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
