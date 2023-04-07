package entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tables.EmployeesTable;

import java.util.Date;

@Entity(name="employee")
@Table(name="employee")
@AttributeOverride(name = "id", column = @Column(name = "idEmployee"))
public class Employee extends BaseEntity{
    private static final Logger LOGGER = LogManager.getLogger(Employee.class);
    @Column(name = "firstName", nullable = false, length = 45)
    private String firstName;
    @Column(name = "lastName", nullable = false, length = 45)
    private String lastName;
    @Column(name = "email", nullable = false, length = 45)
    private String email;
    @Column(name = "phoneNumber", nullable = false, length = 45)
    private String phoneNumber;
    @Column(name = "address", length = 45)
    private String address;
    @Column(name = "Salary", nullable = false)
    private double salary;
    @Column(name = "birthDate", nullable = false)
    private Date birthDate;
    @Column(name = "idCompany", nullable = false)
    private int idCompany;
    @Column(name = "idInstitution", nullable = false)
    private int idInstitution;

    public Employee() {
        super();
    }
    @Override
    public int getId(){
        return id;
    }
    @Override
    public void setId(int id){
        super.setId(id);
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public int getIdCompany() {
        return idCompany;
    }
    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
    public int getIdInstitution() {
        return idInstitution;
    }
    public void setIdInstitution(int idInstitution) {
        this.idInstitution = idInstitution;
    }
    public void printEmployeeInfo(){
        LOGGER.info("ID: "+id+
                "\tFirst Name: "+firstName+
                "\tLast Name: "+lastName+
                "\tEmail: "+email+
                "\tPhone number: "+phoneNumber+
                "\tAddress: "+address+
                "\tSalary: "+salary+
                "\tBirth Date: "+birthDate+
                "\tCompany ID: "+idCompany+
                "\tInstitution ID: "+idInstitution);
    }
}
