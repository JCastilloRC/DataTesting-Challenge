package hooks;


import entities.Employee;
import jpamanager.JPAManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.*;
import tables.EmployeesTable;


import java.io.IOException;

public class Hooks{
    private static final Logger LOGGER = LogManager.getLogger("Hooks");
    protected JPAManager manager;
    @BeforeTest()
    public void setUp() throws IOException {
        manager = new JPAManager();
    }
    @AfterMethod(onlyForGroups = {"requiresDeleteEntry"})
    public void deleteLastEmployee(ITestContext ctx){
        try {
            LOGGER.info("Deleting last entry...");
            EmployeesTable table = (EmployeesTable)ctx.getAttribute("table");
            int id = (int)ctx.getAttribute("id");
            table.deleteEmployeeById(id);
        }
        catch (Exception e){
            LOGGER.error("Something went wrong");
        }
    }
    @AfterMethod(onlyForGroups = {"requiresRestoreEmail"})
    public void restoreEmployeeEmail(ITestContext ctx){
        try {
            LOGGER.info("Restoring last email...");
            EmployeesTable table = (EmployeesTable)ctx.getAttribute("table");
            Employee employee = (Employee) ctx.getAttribute("employee");
            String originalEmail = (String) ctx.getAttribute("originalEmail");
            table.updateEmail(employee, originalEmail);
        }
        catch (Exception e){
            LOGGER.error("Something went wrong");
        }
    }
    @AfterMethod(onlyForGroups = {"requiresRestorePhoneNumber"})
    public void restoreEmployeePhoneNumber(ITestContext ctx){
        try {
            LOGGER.info("Restoring last phone number...");
            EmployeesTable table = (EmployeesTable)ctx.getAttribute("table");
            Employee employee = (Employee) ctx.getAttribute("employee");
            String originalPhoneNumber = (String) ctx.getAttribute("originalPhoneNumber");
            table.updatePhoneNumber(employee, originalPhoneNumber);
        }
        catch (Exception e){
            LOGGER.error("Something went wrong");
        }
    }
    @AfterMethod(onlyForGroups = {"requiresRestoreEntry"})
    public void restoreLastEmployee(ITestContext ctx){
        try {
            LOGGER.info("Restoring last entry...");
            EmployeesTable table = (EmployeesTable)ctx.getAttribute("table");
            Employee employee = (Employee) ctx.getAttribute("employee");
            table.addEmployee(employee);
        }
        catch (Exception e){
            LOGGER.error("Something went wrong");
        }
    }
}
