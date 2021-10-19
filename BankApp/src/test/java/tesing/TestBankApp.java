package tesing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dbconection.DatabaseConnection;

public class TestBankApp {
	DatabaseConnection db=new DatabaseConnection();
	@Test
	public void Test_customerLoginSatstus() {
		
		int a=db.customerlogin("mourya", "mourya@123");
		assertEquals(100000, a);
	}
	@Test
	public void Test_employeelogin() {
		int b= db.employeelogin("krishna", "krishna123");
		assertEquals(100, b);
	}
	@Test
	public void Test_customerLoginSatstus1() {
		
		int a=db.customerlogin("moura", "mourya@123");
		assertEquals(0, a);
	}
	@Test
	public void Test_employeelogin1() {
		int b= db.employeelogin("krisha", "krishna123");
		assertEquals(0, b);
	}
}
