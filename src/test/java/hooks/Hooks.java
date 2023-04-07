package hooks;


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
    @AfterMethod(onlyForGroups = {"requiresDeleteEntry"}, enabled = false)
    public void deleteMovieFromWatchList(ITestContext ctx){
        try {
            LOGGER.info("Deleting last entry...");
            EmployeesTable table = (EmployeesTable)ctx.getAttribute("table");
            int id = (int)ctx.getAttribute("id");
            table.safeDeleteEmployeeById(id);
        }
        catch (Exception e){
            LOGGER.error("Something went wrong");
        }
    }
}
