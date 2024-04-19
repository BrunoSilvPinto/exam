package ch.etmles.payroll.Controllers;

import ch.etmles.payroll.Entities.Employee;
import ch.etmles.payroll.Repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    /* curl sample :
    curl -i localhost:8080/employees
    */
    @GetMapping("/employees")
    List<Employee> all(){
        return repository.findAll();
    }

    /* curl sample :
    curl -i -X POST localhost:8080/employees ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"Russel George\", \"role\": \"gardener\", \"birthdayDate\": \"10-05-2007\"}"
    */
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee){
        if(newEmployee.calculateAge() < 18) {
            throw new EmployeeMinorException(newEmployee.getName());
        }

        return repository.save(newEmployee);
    }

    /* curl sample :
    curl -i localhost:8080/employees/1
    */
    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    /* curl sample :
    curl -i -X PUT localhost:8080/employees/3 ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"Samwise Bing\", \"role\": \"peer-to-peer\", \"birthdayDate\": \"10-05-2010\"}"
     */
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        if(!repository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        if(newEmployee.calculateAge() < 18) {
            throw new EmployeeMinorException(newEmployee.getName());
        }

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    employee.setBirthdayDate((newEmployee.getBirthdayDate()));
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    /* curl sample :
    curl -i -X DELETE localhost:8080/employees/2
    */
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
    }
}
