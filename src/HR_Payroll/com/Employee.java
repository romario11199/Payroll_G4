/*
Griffiths Marlon - 2301010569
Romario McLymont - 2301010275
Delvian Brown -
Shanelle Farqurson -
*/

package HR_Payroll.com;

// Employee Class
public abstract class Employee implements Payable {
    private String name; //firstName & lastName
    private String SSN; //socialSecurityNumber

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