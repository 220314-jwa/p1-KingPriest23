package DAOTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import Beans.Request;
import DAOs.DAOFactory;
import DAOs.RequestDAO;

@TestMethodOrder(OrderAnnotation.class)
public class RequestDAOTest {
	
	private static RequestDAO reqDao = DAOFactory.getReqDao();
	private static Request testReq = new Request();
	private static Request testNewReq = new Request();
	
	@BeforeAll
	public static void setUp() {
		testReq.setDescription("test");
		Random rand = new Random();
		testNewReq.setDescription("test_" + rand.nextLong());
		testReq.setRequestId(reqDao.create(testReq));
	}
	
	@AfterAll
	public static void cleanUp() throws SQLException {
		reqDao.delete(testReq);
	}

	
	@Test
	public void getByStatus() {
		String testStatus = "Approved";
		List<Request> requests = reqDao.getByStatus(testStatus);
		
		boolean onlyCorrectStatus = true;
		for (Request reqs : requests) {
			if (!(reqs.getStatus().equals(testStatus))) {
				onlyCorrectStatus = false;
				break;
			}
		}
		assertTrue(onlyCorrectStatus);
	}
	
	@Test
	@Order(1)
	public void createRequestSuccessfully() {
		int id = reqDao.create(testNewReq);
		testNewReq.setRequestId(id);
		
		assertNotEquals(0, id);
	}
	
	@Test
	public void getByIdExists() {
		int id = testReq.getRequestId();
		
		Request req = reqDao.getById(id);
		
		assertEquals(testReq, req);
	}
	
	@Test
	public void getByIdDoesNotExist() {
		Request req = reqDao.getById(0);
		assertNull(req);
	}
	
	@Test
	public void getAll() {
		assertNotNull(reqDao.getAll());
	}
	
	@Test
	public void updateReqExists() {
		assertDoesNotThrow(() -> {
			reqDao.update(testReq);
		});
	}
	
	
	@Test
	@Order(2)
	public void deleteReqExists() {
		assertDoesNotThrow(() -> {
			reqDao.delete(testNewReq);
		});
	}
	
	
	}

	
	
	