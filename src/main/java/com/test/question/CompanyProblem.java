package com.test.question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CompanyProblem {

	public static void main(String[] args) throws IOException {

		// Check if a filename is provided
		if (args.length == 0) {
			System.err.println("Error: No filename provided.");
			System.err.println("Usage: java -jar your-jar-file.jar <filename>");
			System.exit(1); // Exit with a non-zero status to indicate an error
		}

		// Get the filename from arguments
		String filename = args[0];

		// Read the CSV file
		Tree<Employee> employeeTree = readFromCSV(filename);

		// Find the employees earning less
		Map<Employee,  Integer> employeeIdealSalaryMap = new LinkedHashMap<>();
		if(null != employeeTree.getRoot().getChildren()) {
			for(Node<Employee> employee: employeeTree.getRoot().getChildren()) {
				findEachEmployeeSalaryRange(employee, employeeIdealSalaryMap);
			}
		}
		
		System.out.println("---------------All Salary Details in format id,salary,ideal_salary,difference------------------------");
		for(Entry<Employee, Integer> entry: employeeIdealSalaryMap.entrySet()) {
			Employee employee = entry.getKey();
			int employeeSalary = employee.getSalary();
			int idealSalary = entry.getValue();
			System.out.println(employee.getId()+","+employee.getSalary()+","+idealSalary+","+(idealSalary-employeeSalary));
		}
		
		findManagersWithLesserSalary(employeeIdealSalaryMap);
		findManagersWithHigherSalary(employeeIdealSalaryMap);
		findEmployeesMoreThan4And2LevelsDeep(employeeTree.getRoot());

	}



	private static void findEmployeesMoreThan4And2LevelsDeep(Node<Employee> root) {
		int deepLevel = 4+2;
		System.out.println();
		System.out.println("--------Employess having more than 4 Managers between then and CEO in the form id,level,difference");
		Map<Employee, Integer> employeeLevelMap = new LinkedHashMap<>();
		populatEmployeeLevelMap(root, 0, employeeLevelMap);
		for(Entry<Employee, Integer> entry: employeeLevelMap.entrySet()) {
			Employee employee = entry.getKey();
			int level = entry.getValue();
			if(level >= deepLevel) {
				System.out.println(employee.getId()+","+level+","+(level-deepLevel+1));
			}
		}
		System.out.println();
		System.out.println("--------Employess having more than 4 Managers between then and CEO in the form id,level,difference");
	}



	private static void populatEmployeeLevelMap(Node<Employee> currentNode, int level, Map<Employee, Integer> employeeLevelMap) {
		// Disregard root level
		if(level != 0) {
			employeeLevelMap.put(currentNode.getData(), level);
		}
		
		// Go to next levels
		if(null != currentNode.getChildren()) {
			for(Node<Employee> subordinate: currentNode.getChildren()) {
				populatEmployeeLevelMap(subordinate, level + 1, employeeLevelMap);
			}
		}
	}



	private static void findManagersWithHigherSalary(Map<Employee, Integer> employeeIdealSalary) {
		System.out.println();
		System.out.println("---------------Managers Earning High in format id,salary,ideal_salary,difference-------------------------");
		for(Entry<Employee, Integer> entry: employeeIdealSalary.entrySet()) {
			Employee employee = entry.getKey();
			int employeeSalary = employee.getSalary();
			int idealSalary = entry.getValue();
			if(employeeSalary > idealSalary) {
				System.out.println(employee.getId()+","+employee.getSalary()+","+idealSalary+","+(idealSalary-employeeSalary));
			}
		}
		System.out.println("---------------Managers Earning High in format id,salary,ideal_salary,difference-------------------------");
		
	}



	private static void findManagersWithLesserSalary(Map<Employee, Integer> employeeIdealSalary) {
		System.out.println();
		System.out.println("---------------Managers Earning Less in format id,salary,ideal_salary,difference-------------------------");
		for(Entry<Employee, Integer> entry: employeeIdealSalary.entrySet()) {
			Employee employee = entry.getKey();
			int employeeSalary = employee.getSalary();
			int idealSalary = entry.getValue();
			if(employeeSalary < idealSalary) {
				System.out.println(employee.getId()+","+employee.getSalary()+","+idealSalary+","+(idealSalary-employeeSalary));
			}
		}
		System.out.println("---------------Managers Earning Less in format id,salary,ideal_salary,difference-------------------------");
	}

	

	private static int findEachEmployeeSalaryRange(Node<Employee> currentNode, Map<Employee, Integer> employeeIdealSalary) {
		Employee manager = currentNode.getData();
		
		// Check if employee has no subordinates. Then ideal salary is same as employee salary
		if (null == currentNode.getChildren()) {
			employeeIdealSalary.put(manager, manager.getSalary());
			return manager.getSalary();
		} 
		
		// Find Salary Range of Subordinates
		int subordinateIdealSalarySum = 0;
		for(Node<Employee> subordinate: currentNode.getChildren()) {
			subordinateIdealSalarySum += findEachEmployeeSalaryRange(subordinate, employeeIdealSalary);
		}
		
		// Find Average 120 Percent and check if its greater than manager salary
		int average120Percent = (int) (subordinateIdealSalarySum * 1.2 / currentNode.getChildren().size());
		if(average120Percent > manager.getSalary()) {
			employeeIdealSalary.put(manager, average120Percent);
			return average120Percent;
		}
		
		// Find Average 150 Percent and check if its less than manager salary
		int average150Percent = (int) (subordinateIdealSalarySum * 1.5 / currentNode.getChildren().size());
		if(average150Percent < manager.getSalary()) {
			employeeIdealSalary.put(manager, average150Percent);
			return average150Percent;
		}
		
		// If salary is between average120Percent and average150Percent
		employeeIdealSalary.put(manager, manager.getSalary());
		return manager.getSalary();
		
	}



	private static Tree<Employee> readFromCSV(String file) throws IOException {

		// Initialize
		Tree<Employee> employeeTree = new EmployeeTree();

		// Read File line by line
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line = null;
			boolean isFirstLine = true;
			while ((line = reader.readLine()) != null) {
				if (!isFirstLine) {
					Employee employee = convertLineToEmp(line);
					employeeTree.addNode(employee);
				}
				isFirstLine = false;
			}
		}

		return employeeTree;
	}

	private static Employee convertLineToEmp(String line) {
		try {
			String[] data = line.split(",");
			Employee emp = new Employee();
			emp.setId(Integer.parseInt(data[0]));
			emp.setFirstName(data[1]);
			emp.setLastName(data[2]);
			emp.setSalary(Integer.parseInt(data[3]));
			if (data.length >= 5) {
				emp.setManagerId(Integer.parseInt(data[4]));
			} else {
				emp.setManagerId(0);
			}
			return emp;
		} catch (RuntimeException re) {
			throw new RuntimeException("Invalid data for line -->" + line);
		}
	}

}

class EmployeeTree extends Tree<Employee> {

	@Override
	public boolean isChild(Employee emp1, Employee emp2) {
		if (emp1.getId() == emp2.getManagerId()) {
			return true;
		} else {
			return false;
		}
	}

}
