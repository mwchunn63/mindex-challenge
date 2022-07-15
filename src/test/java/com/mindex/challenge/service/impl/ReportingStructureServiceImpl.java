package com.mindex.challenge.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.DataBootstrap;
import com.mindex.challenge.config.MongoConfig;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImpl {

	private String employeeUrl;
	private String employeeIdUrl;
	private String reportingStructureUrl;
	private static final String DATASTORE_LOCATION = "/static/employee_database.json";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setup() {
		employeeUrl = "http://localhost:" + port + "/employee";
		employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
		reportingStructureUrl = "http://localhost:" + port + "/reportingstructure/{id}";

	}

	@Test
	public void testFourDirectReports() {

		// Read checks 
		ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
		ReportingStructure createdReportingStructure = new ReportingStructure("16a596ae-edd3-4847-99fe-c4518e82c86f",4);
		assertReportingStructureEquivalence(createdReportingStructure, readReportingStructure);
		
	}
	
	@Test
	public void testTwoDirectReports() {

		// Read checks 
		ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "03aa1462-ffa9-4978-901b-7c001562cf6f").getBody();
		ReportingStructure createdReportingStructure = new ReportingStructure("03aa1462-ffa9-4978-901b-7c001562cf6f",2);
		assertReportingStructureEquivalence(createdReportingStructure, readReportingStructure);
		
	}
	
	@Test
	public void testZeroReports() {
		ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "62c1084e-6e34-4630-93fd-9153afb6530").getBody();
		ReportingStructure createdReportingStructure = new ReportingStructure("62c1084e-6e34-4630-93fd-9153afb6530",0);
		assertReportingStructureEquivalence(createdReportingStructure, readReportingStructure);	}


	private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getDepartment(), actual.getDepartment());
		assertEquals(expected.getPosition(), actual.getPosition());
	}

	private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
		assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
	}
}
