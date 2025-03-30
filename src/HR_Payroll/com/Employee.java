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