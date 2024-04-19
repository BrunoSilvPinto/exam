package ch.etmles.payroll.Controllers;

public class EmployeeMinorException  extends RuntimeException {
    EmployeeMinorException(String nom){
        super("Hiring a minor employee is not legally possible : Name : " + nom);
    }
}
