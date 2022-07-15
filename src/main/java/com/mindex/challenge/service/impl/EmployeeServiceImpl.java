package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee create(Employee employee) {
		LOG.debug("Creating employee [{}]", employee);

		employee.setEmployeeId(UUID.randomUUID().toString());
		employeeRepository.insert(employee);

		return employee;
	}

	@Override
	public Employee read(String id) {
		LOG.debug("Creating employee with id [{}]", id);

		Employee employee = employeeRepository.findByEmployeeId(id);

		if (employee == null) {
			throw new RuntimeException("Invalid employeeId: " + id);
		}

		List<Employee> emps = new ArrayList<Employee>();
		emps = employeeRepository.findAll();

		for (Employee emp : emps) {
			System.out.println("emp: " + emp.getDepartment());
			// System.out.println("dr: " + emp.getDirectReports();

		}

		return employee;
	}

	@Override
	public Employee update(Employee employee) {
		LOG.debug("Updating employee [{}]", employee);

		return employeeRepository.save(employee);
	}

	@Override
	public ReportingStructure getReportingStructure(String id) {

		LOG.debug("Retrieving reportingStructure with id [{}]", id);

		Employee singleEmployee = employeeRepository.findByEmployeeId(id);

		ReportingStructure reportingStructure = new ReportingStructure();
		// Create a Set that will contain just unique keys/empIds
		HashSet<String> uniqueEmps = new HashSet<String>();

		if (singleEmployee == null) {
			throw new RuntimeException("Invalid employeeId: " + id);
		}

		// Find and count direct reports
		List<Employee> directReports = singleEmployee.getDirectReports();
		if (directReports != null) {
			for (Employee directReport : directReports) {
				uniqueEmps.add(directReport.getEmployeeId());
			}
		}
		// Find and count distinct reports
		// Distinct reports are emps that exist in the same department and they are the
		// manager and the others are non-Managers

		List<Employee> emps = employeeRepository.findAll();
		for (Employee searchEmp: emps) {
			if (singleEmployee.getPosition().indexOf("Manager") > 0 &&
					searchEmp.getPosition().indexOf("Manager") < 0 &&
					singleEmployee.getDepartment().equalsIgnoreCase(searchEmp.getDepartment())) {
				uniqueEmps.add(searchEmp.getEmployeeId());
			}
		}
		//System.out.print("id:"+searchEmp.getEmployeeId()+"  dept:"+searchEmp.getDepartment()+"   position:"+searchEmp.getPosition()+"\n"); }
		reportingStructure.setEmployeeId(id);
		reportingStructure.setNumberOfReports(uniqueEmps.size());
		System.out.println("size:" + uniqueEmps.size());
		return reportingStructure;
	}
}
