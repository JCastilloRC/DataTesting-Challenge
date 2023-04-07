package runner;

import entities.Employee;
import helper.Helper;
import helper.TestNGListener;
import hooks.Hooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tables.EmployeesTable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Listeners(TestNGListener.class)
public class TestRunner extends Hooks{
    private static final Logger LOGGER = LogManager.getLogger("TestRunner");
    @Test
    public void getAllRecords(){
       List<Employee> employees = new EmployeesTable(manager).getAllEmployees();
       for(Employee e: employees){
           e.printEmployeeInfo();
           assertThat("Phone number of employee with ID " + e.getId() + " is not a number",
                        e.getPhoneNumber(),
                        matchesPattern("^\\d+$")
           );
       }
    }
    @Test
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
    @Test(groups = "requiresRestoreEmail")
    public void updateEmployeeEmail(ITestContext ctx) throws IOException, ParseException {
        EmployeesTable table = new EmployeesTable(manager);
        ctx.setAttribute("table", table);
        Employee employee = table.getRandomEmployee();
        ctx.setAttribute("employee", employee);
        ctx.setAttribute("originalEmail", employee.getEmail());
        String newEmail = Helper.getRandomEmail();
        table.updateEmail(employee, newEmail);
        assertThat("The email was not updated correctly",
                table.getEmployeeById(employee.getId()).getEmail(),
                equalTo(newEmail)
        );
    }
    @Test(groups = "requiresRestoreEntry")
    public void deleteEmployee(ITestContext ctx){
        EmployeesTable table = new EmployeesTable(manager);
        ctx.setAttribute("table", table);
        Employee employee = table.getRandomEmployee();
        ctx.setAttribute("employee", employee);
        table.safeDeleteEmployeeById(employee.getId());
        assertThat("The entry was not deleted",
                table.getEmployeeById(employee.getId()),
                nullValue()
        );
    }
}
