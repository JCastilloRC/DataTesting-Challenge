package helper;




import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import entities.Employee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Helper{
    public static String getRandomNumber(int lowBound, int upBound) {
        int rnd = lowBound + new Random().nextInt(upBound-lowBound);
        return Integer.toString(rnd);
    }
    public static String getRandomAddress() throws IOException {
        List<String>  addresses = parseBagOfWordsYAML("testdata/bagofaddresses.yml");
        int rnd = new Random().nextInt(addresses.size());
        return addresses.get(rnd);
    }
    public static String getRandomEmail() throws IOException {
        List<String>  emails = parseBagOfWordsYAML("testdata/bagofemails.yml");
        int rnd = new Random().nextInt(emails.size());
        return emails.get(rnd);
    }
    public static String getRandomDate() throws IOException {
        List<String>  dates = parseBagOfWordsYAML("testdata/bagofdates.yml");
        int rnd = new Random().nextInt(dates.size());
        return dates.get(rnd);
    }
    public static String getRandomName() throws IOException {
        List<String> names = parseBagOfWordsYAML("testdata/bagofnames.yml");
        int rnd = new Random().nextInt(names.size());
        return names.get(rnd);
    }
    public static Employee getRandomValidEmployee() throws IOException {
        List<Employee> employees = parseBagOfValidEmployees("testdata/validemployees.yml");
        int rnd = new Random().nextInt(employees.size());
        return employees.get(rnd);
    }
    public static List<String> parseBagOfWordsYAML(String yamlPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(yamlPath), new TypeReference<List<String>>() {});
    }
    public static List<Employee> parseBagOfValidEmployees(String yamlPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        CollectionType listType = mapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, Employee.class);
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(yamlPath), listType);
    }
}
