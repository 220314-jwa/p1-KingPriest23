package DAOTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import Beans.Employee;

import org.junit.jupiter.api.TestMethodOrder;

import DAOs.DAOFactory;
import DAOs.EmployeeDAO;

@TestMethodOrder(OrderAnnotation.class)
public class EmployeeDAOTest {
	private static EmployeeDAO empDao = DAOFactory.getEmpDao();
	private static Employee testEmployee = new Employee();
	private static Employee testNewEmployee = new Employee();
	
	@BeforeAll
	public static void setUp() {
		testEmployee.setUsername("test");
		Random rand = new Random();
		testNewEmployee.setUsername("test_" + rand.nextLong());
		testEmployee.setId(empDao.create(testEmployee));
	}
	@AfterAll
	public static void cleanUp() {
		empDao.delete(testEmployee);
	}
	@Test
	public void getByUsernameExists() {
		Employee engineer = empDao.getByUsername("test");
		assertEquals(testEmployee, engineer);
	}
	@Test
	@Order(1)
	public void createUserSuccessfully() {
		int id = empDao.create(testNewEmployee);
		testNewEmployee.setId(id);
		
		assertNotEquals(0, id);
	}
	
	@Test
	public void createUserDuplicateUsername() {
		int id = empDao.create(testEmployee);
		
		assertEquals(0, id);
	}
	
	@Test
	public void getByIdExists() {
		int id = testEmployee.getId();
		
		Employee user = empDao.getById(id);
		
		assertEquals(testEmployee, user);
	}
	
	@Test
	public void getByIdDoesNotExist() {
		Employee user = empDao.getById(0);
		assertNull(user);
	}
	
	@Test
	public void getAll() {
		assertNotNull(empDao.getAll());
	}
	
	@Test
	public void updateUserExists() {
		assertDoesNotThrow(() -> {
			empDao.update(testEmployee);
		});
	}
	
	@Test
	public void updateUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			empDao.update(new Employee());
		});
	}
	
	@Test
	@Order(2)
	public void deleteUserExists() {
		assertDoesNotThrow(() -> {
			empDao.delete(testNewEmployee);
		});
	}
	
	@Test
	public void deleteUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			empDao.delete(new Employee());
		});
	}

}
