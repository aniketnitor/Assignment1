package org.example;

import java.io.*;
import java.util.*;

class Person {
    private String name;
    private int age;
    private String address;

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Address: " + address;
    }
}

class Employee extends Person implements Serializable {
    private int employeeId;
    private String department;
    private String designation;
    private double salary;

    public Employee(int employeeId, String name, int age, String address, String department, String designation, double salary) {
        super(name, age, address);
        this.employeeId = employeeId;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + ", Employee ID: " + employeeId + ", Department: " + department + ", Designation: " + designation + ", Salary: " + salary;
    }
}

public class EmployeeManagementSystem {
    private static final String FILE_NAME = "employees.dat";
    private Map<Integer, Employee> employeeMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public EmployeeManagementSystem() {
        loadEmployees();
    }

    public void start() {
        while(true) {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee Details");
            System.out.println("5. Delete Employee");
            System.out.println("6. Save & Exit");
            System.out.println("Choose an option");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> searchEmployee();
                //case 4 -> updateEmployee();
                case 5 -> deleteEmployee();
                case 6 -> {
                    saveEmployees();
                    System.out.println("Data saved successfully. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addEmployee() {
        System.out.println("Enter Employee ID: ");
        int id = scanner.nextInt();
        if (employeeMap.containsKey(id)) {
            System.out.println("Employee ID already exists!");
            return;
        }
        scanner.nextLine();
        System.out.println("Enter Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter age: ");
        int age = scanner.nextInt();
        System.out.println("Enter address: ");
        String address = scanner.nextLine();
        System.out.println("Enter department: ");
        String department = scanner.nextLine();
        System.out.println("Enter designation: ");
        String designation = scanner.nextLine();
        System.out.println("Enter salary: ");
        double salary = scanner.nextDouble();

        Employee employee = new Employee(id, name, age, address, department, designation, salary);
        employeeMap.put(id, employee);
        System.out.println("Employee added successfully!");
    }

    private void viewEmployees() {
        if (employeeMap.isEmpty()) {
            System.out.println("No employees to display!");
            return;
        }
        employeeMap.values().forEach(System.out::println);
    }

    private void searchEmployee() {
        System.out.println("Enter Employee ID to search: ");
        int id = scanner.nextInt();
        Employee employee = employeeMap.get(id);
        if (employee == null) {
            System.out.println("Employee not found!");
        } else {
            System.out.println(employee);
        }
    }

//    private void updateEmployee() {
//        System.out.println("Enter Employee ID to update: ");
//        int id o
//    }

    private void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = scanner.nextInt();
        if(employeeMap.remove(id) != null) {
            System.out.println("Employee deleted successfully");
        } else {
            System.out.println("Employee not found");
        }
    }

    private void saveEmployees() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employeeMap);
        } catch(IOException e) {
            System.out.println("Error saving employee data");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadEmployees() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employeeMap = (Map<Integer, Employee>) ois.readObject();
            System.out.println("Employee data loaded successfully");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found, Starting fresh...");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading");
        }
    }

    public static void main(String[] args) {
        new EmployeeManagementSystem().start();
    }
}
