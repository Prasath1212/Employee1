import java.util.*;
import java.util.stream.Collectors;

class Employee {
    String employeeId;
    String name;
    String department;
    String gender;
    String phone;

    public Employee(String employeeId, String name, String department, String gender, String phone) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.gender = gender;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

class CollectionHandler {
    private Map<String, List<Employee>> collections = new HashMap<>();

    public void createCollection(String collectionName) {
        collections.put(collectionName, new ArrayList<>());
    }

    public void indexData(String collectionName, String excludeColumn) {
        List<Employee> employees = collections.get(collectionName);
        employees.forEach(employee -> {
            if (!excludeColumn.equalsIgnoreCase("Department")) {
                System.out.println("Indexing: " + employee.department);
            }
            if (!excludeColumn.equalsIgnoreCase("Gender")) {
                System.out.println("Indexing: " + employee.gender);
            }
            if (!excludeColumn.equalsIgnoreCase("Phone")) {
                System.out.println("Indexing: " + employee.phone);
            }
        });
    }

    public List<Employee> searchByColumn(String collectionName, String columnName, String columnValue) {
        List<Employee> employees = collections.get(collectionName);
        return employees.stream()
                .filter(emp -> {
                    switch (columnName) {
                        case "Department":
                            return emp.department.equals(columnValue);
                        case "Gender":
                            return emp.gender.equals(columnValue);
                        case "Phone":
                            return emp.phone.equals(columnValue);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public int getEmpCount(String collectionName) {
        return collections.get(collectionName).size();
    }

    public void delEmpById(String collectionName, String employeeId) {
        List<Employee> employees = collections.get(collectionName);
        employees.removeIf(emp -> emp.employeeId.equals(employeeId));
    }

    public Map<String, Long> getDepFacet(String collectionName) {
        List<Employee> employees = collections.get(collectionName);
        return employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.department, Collectors.counting()));
    }

    public void addEmployee(String collectionName, Employee employee) {
        collections.get(collectionName).add(employee);
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        CollectionHandler handler = new CollectionHandler();

        String v_nameCollection = "Prasath";
        String v_phoneCollection = "3210"; 
        handler.createCollection(v_nameCollection);
        handler.createCollection(v_phoneCollection);
        handler.addEmployee(v_nameCollection, new Employee("E02001", "Palani", "IT", "Male", "1234"));
        handler.addEmployee(v_nameCollection, new Employee("E02002", "Kali", "HR", "Male", "3210"));
        handler.addEmployee(v_nameCollection, new Employee("E02003", "Hafil", "IT", "Male", "4321"));
        
        handler.addEmployee(v_phoneCollection, new Employee("E02004", "Ranjith", "Finance", "Male", "1234"));
        handler.addEmployee(v_phoneCollection, new Employee("E02005", "Gayu", "IT", "Female", "5678"));
        System.out.println("Employee count in " + v_nameCollection + ": " + handler.getEmpCount(v_nameCollection));
        handler.indexData(v_nameCollection, "Department");
        handler.indexData(v_phoneCollection, "Gender");
        handler.delEmpById(v_nameCollection, "E02003");
        System.out.println("Employee count in " + v_nameCollection + ": " + handler.getEmpCount(v_nameCollection));
        System.out.println("Search by Department 'IT' in " + v_nameCollection + ": " + handler.searchByColumn(v_nameCollection, "Department", "IT"));
        System.out.println("Search by Gender 'Male' in " + v_nameCollection + ": " + handler.searchByColumn(v_nameCollection, "Gender", "Male"));
        System.out.println("Search by Department 'IT' in " + v_phoneCollection + ": " + handler.searchByColumn(v_phoneCollection, "Department", "IT"));
        System.out.println("Department facet for " + v_nameCollection + ": " + handler.getDepFacet(v_nameCollection));
        System.out.println("Department facet for " + v_phoneCollection + ": " + handler.getDepFacet(v_phoneCollection));
    }
}
