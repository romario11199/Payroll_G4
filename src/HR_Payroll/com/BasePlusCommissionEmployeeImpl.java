/*
Griffiths Marlon - 2301010569
Romario McLymont - 2301010275
Delvian Brown -
Shanelle Farqurson -
*/

package HR_Payroll.com;

public class BasePlusCommissionEmployeeImpl extends BasePlusCommissionEmployee {
    public BasePlusCommissionEmployeeImpl(String name, String SSN, double sales, double commissionRate, double baseSalary) throws InvalidEmployeeDataException {
        super(name, SSN, sales, commissionRate, baseSalary);
    }
}
