package ch.etmles.payroll.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;

@Entity
public class Employee {

    private @Id
    @GeneratedValue Long id;
    private String name;

    private String birthdayDate;
    private String role;



    public Employee(){}

    public Employee(String name, String role, String birthdayDate){
        this.setName(name);
        this.setRole(role);
        this.setBirthdayDate(birthdayDate);
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public int calculateAge() {
        Year thisYear = Year.now();
        int anneeActuelle = thisYear.getValue();
        int anneeNaissance = Integer.parseInt(this.birthdayDate.substring(this.birthdayDate.length() -4));


        return anneeActuelle - anneeNaissance;
    }

    public Long getID(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Employee employee))
            return false;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role)  && Objects.equals(this.birthdayDate, employee.birthdayDate);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.role, this.birthdayDate);
    }

    @Override
    public String toString(){
        return "Employee{" + "id=" + this.getID() + ", name='" + this.getName() + '\'' + ", role='" + this.getRole() + '\'' + this.getBirthdayDate() + '}';
    }
}
