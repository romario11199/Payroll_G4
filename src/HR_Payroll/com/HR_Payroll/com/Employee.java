/*
Griffiths Marlon - 2301010569
Romario McLymont - 2301010275
Delvian Brown - 230101767
Shanelle Farqurson -
Jhenelle Walker -
*/

package HR_Payroll.com;

// Employee Class
public abstract class Employee implements Payable {
    private String name;
    private String SSN;

    public Employee(String name, String SSN) {
        this.name = name;
        this.SSN = SSN;
    }

    public String getName() {
        return name;
    }

    public String getSSN() {
        return SSN;
    }
}