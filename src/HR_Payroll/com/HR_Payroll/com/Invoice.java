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

//Invoice Class
public class Invoice implements Payable {
    private String partNum;
    private String partDes;
    private int quantity;
    private double pPi; // price Per Item

    public Invoice(String partNum, String partDes, int quantity, double pPi) {
        this.partNum = partNum;
        this.partDes = partDes;
        this.quantity = quantity;
        this.pPi = pPi;
    }

    @Override
    public double getPaymentAmount() {
        return quantity * pPi; // Total cost of the invoice
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("invoice.txt", true)) {
            writer.write("Invoice: " + partNum + ", Amount: " + getPaymentAmount() +
                    ", Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}