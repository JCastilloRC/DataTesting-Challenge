package runner;

import entities.Employee;
import helper.Helper;
import helper.TestNGListener;
import hooks.Hooks;
import jdk.jfr.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tables.EmployeesTable;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Listeners(TestNGListener.class)
public class TestRunner extends Hooks{
    private static final Logger LOGGER = LogManager.getLogger("TestRunner");
    @Test
    @Description("Verifying that all registered employees phone numbers are numbers and hava valid emails")
    public void getAllRecords(){
       List<Employee> employees = new EmployeesTable(manager).getAllEmployees();
       for(Employee e: employees){
           e.printEmployeeInfo();
           assertThat("Phone number of employee with ID " + e.getId() + " is not a number",
                        e.getPhoneNumber(),
                        matchesPattern("^\\d+$")
           );
           assertThat("Employee with ID " + e.getId() + " has an invalid email",
                   e.getEmail(),
                   matchesPattern("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
           );
       }
    }
    @Test
    @Description("Validating that last names from database match the actual last names of employees")
    public void getEmployeeByLastName() throws IOException {
        Employee validEmployee = Helper.getRandomValidEmployee();
        LOGGER.info("Searching for employee with last name: " + validEmployee.getLastName());
        Employee employee = new EmployeesTable(manager).getEmployeeByLastName(validEmployee.getLastName());
        assertThat("The last names don't match",
                employee.getLastName(),
                equalTo(employee.getLastName())
        );
    }
    @Test(groups = "requiresDeleteEntry")
    @Description("Verifying that when you add a new employee, the database is updated")
    public void insertNewEmployee(ITestContext ctx) throws IOException, ParseException {
        EmployeesTable table = new EmployeesTable(manager);
        ctx.setAttribute("table", table);
        int id = table.addRandomEmployee();
        ctx.setAttribute("id", id);
        assertThat("Entry was not made successfully",
                table.getEmployeeById(id),
                notNullValue()
        );
    }
    @Test(groups = {"requiresRestoreEmail", "requiresRestorePhoneNumber"})
    @Description("Verifying that when you modify an existing employee, the database is updated")
    public void updateEmployeeEmailandPhone(ITestContext ctx) throws IOException{
        EmployeesTable table = new EmployeesTable(manager);
        ctx.setAttribute("table", table);
        Employee employee = table.getRandomEmployee();
        ctx.setAttribute("employee", employee);
        ctx.setAttribute("originalEmail", employee.getEmail());
        ctx.setAttribute("originalPhoneNumber", employee.getPhoneNumber());
        String newEmail = Helper.getRandomEmail();
        String newPhone = Helper.getRandomNumber(1000000, 9999999);
        table.updateEmail(employee, newEmail);
        table.updatePhoneNumber(employee,newPhone);
        assertThat("The email was not updated correctly",
                table.getEmployeeById(employee.getId()).getEmail(),
                equalTo(newEmail)
        );
        assertThat("The phone number was not updated correctly",
                table.getEmployeeById(employee.getId()).getPhoneNumber(),
                equalTo(newPhone)
        );
    }
    @Test(groups = "requiresRestoreEntry")
    @Description("Verifying that when you delete an employee, the database is updated")
    public void deleteEmployee(ITestContext ctx){
        EmployeesTable table = new EmployeesTable(manager);
        ctx.setAttribute("table", table);
        Employee employee = table.getRandomEmployee();
        ctx.setAttribute("employee", employee);
        table.deleteEmployeeById(employee.getId());
        assertThat("The entry was not deleted",
                table.getEmployeeById(employee.getId()),
                nullValue()
        );
    }
}
