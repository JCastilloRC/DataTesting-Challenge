package tables;

import entities.Employee;
import helper.Helper;
import jpamanager.JPAManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        for (Object e : employeesGeneric) {
            employees.add((Employee) e);
        }
        return employees;
    }

    public Employee getEmployeeById(int id){
        LOGGER.info("Returning employee with id: " + id);
        return manager.getEntityById(Employee.class, id);
    }
    public Employee getEmployeeByLastName(String lastname){
        LOGGER.info("Returning employee with last name: " + lastname);
        return (Employee) manager.getEntitiesByParam("employee", "lastName", lastname).get(0);
    }
    public Employee getRandomEmployee(){
        List<Employee> employees = getAllEmployees();
        LOGGER.info("Selecting a random employee...");
        int rnd = new Random().nextInt(employees.size());
        return employees.get(rnd);
    }
    public void addEmployee(Employee employee){
        LOGGER.info("Adding employee...");
        employee.printEmployeeInfo();
        manager.insertEntity(employee);
    }
    public int addRandomEmployee() throws IOException, ParseException {
        LOGGER.info("Adding random employee...");
        Employee anEmployee = Employee.generateRandomEmployee();
        manager.insertEntity(anEmployee);
        return anEmployee.getId();
    }
    public void updateEmail(Employee employee, String  newEmail) throws IOException, ParseException {
        LOGGER.info("Updating email from employee with id " + employee.getId()+" to: "+ newEmail);
        employee.setEmail(newEmail);
        manager.updateEntity(employee);
    }
    public Employee safeDeleteEmployeeById(int id){
        LOGGER.info("Deleting employee with id: "+id);
        Employee employee = manager.getEntityById(Employee.class, id);
        manager.deleteEntityById(Employee.class, id);
        return employee;
    }
}
