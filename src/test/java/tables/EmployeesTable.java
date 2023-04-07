package tables;

import entities.Employee;
import helper.Helper;
import jpamanager.JPAManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeesTable{
    private static final Logger LOGGER = LogManager.getLogger(EmployeesTable.class);
    JPAManager manager;
    public EmployeesTable(JPAManager manager){
        this.manager = manager;
    }
    public List<Employee> getAllEmployees(){
        LOGGER.info("Getting all the employees from table...");
        List<?> employeesGeneric = manager.getAllEntities("employee");
        List<Employee> employees = new ArrayList<>();
        int i=0;
        for (Object e : employeesGeneric) {
            employees.add((Employee) e);
            employees.get(i).printEmployeeInfo();
            i++;
        }
        return employees;
    }

    public Employee getEmployeeById(int id){
        return manager.getEntityById(Employee.class, id);
    }
    public Employee getEmployeeByLastName(String lastname){
        return (Employee) manager.getEntitiesByParam("employee", "lastName", lastname).get(0);
    }
    public void addEmployee(Employee employee){
        manager.insertEntity(employee);
    }
    public int addRandomEmployee() throws IOException, ParseException {
        LOGGER.info("Adding random employee...");
        Employee anEmployee = new Employee();
        anEmployee.setId(Integer.parseInt(Helper.getRandomNumber(100, 200)));
        anEmployee.setFirstName(Helper.getRandomName());
        anEmployee.setLastName(Helper.getRandomName());
        anEmployee.setEmail(Helper.getRandomEmail());
        anEmployee.setPhoneNumber(Helper.getRandomNumber(1000000,9999999 ));
        anEmployee.setBirthDate(new SimpleDateFormat( "yyyy-MM-dd" ).parse( Helper.getRandomDate()));
        anEmployee.setSalary(Integer.parseInt(Helper.getRandomNumber(1000000, 10000000)));
        anEmployee.setAddress(Helper.getRandomAddress());
        anEmployee.setIdCompany(Integer.parseInt(Helper.getRandomNumber(1, 5)));
        anEmployee.setIdInstitution(Integer.parseInt(Helper.getRandomNumber(1, 3)));
        manager.insertEntity(anEmployee);
        anEmployee.printEmployeeInfo();
        return anEmployee.getId();
    }
    public Employee safeDeleteEmployeeById(int id){
        Employee employee = manager.getEntityById(Employee.class, id);
        manager.deleteEntityById(Employee.class, id);
        return employee;
    }
}
