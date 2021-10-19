package com.dbconection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankapp.BankAppMain;



public class DatabaseConnection {
	public static final Logger log=Logger.getLogger(BankAppMain.class);
	/*
	 * public static void main(String[] args) { int ch; do{ Scanner sc= new
	 * Scanner(System.in); System.out.println("1. Add user");
	 * System.out.println("2. check balance"); System.out.println("3. With draw");
	 * System.out.println("4. Transfor"); System.out.println("5. employee login");
	 * System.out.println("6. Customer login"); System.out.println("7. Exit");
	 * System.out.println("Enter your choice"); ch=sc.nextInt();
	 * 
	 * switch (ch) { case 1: adduser(); break; case 2: checkBalance(); break; case
	 * 3: withdraw(); break; case 4: moneyTransfer(); break; case 5: int
	 * a=employeelogin(); System.out.println(a); break; case 6: int
	 * a1=customerlogin(); System.out.println(a1); break; case 7: System.exit(0);
	 * break; default: System.out.println("Enter the correct choice"); break; }
	 * 
	 * }while(ch>=1 || ch<8);
	 * 
	 * 
	 * }
	 */
	public int customerlogin(String username, String password) {
		PreparedStatement pstm =null;
		/*
		 * Scanner sc=new Scanner(System.in); System.out.println("Enter User name");
		 * String username=sc.nextLine(); System.out.println("Enter password"); String
		 * password=sc.nextLine();
		 */
		int a = 0;
		try {
			pstm = (ConnectionInstance.getInstance()).prepareStatement("Select accountno from customer where username=? and password=?");
			pstm.setString(1, username);
			pstm.setString(2, password);
			ResultSet re= pstm.executeQuery();
			while(re.next()) {
				a=re.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(a==0) {
			return 0;
		}
		else {
			return a;
		}
		
	}

	public int employeelogin(String username, String password) {
		PreparedStatement pstm =null;
		//Scanner sc=new Scanner(System.in);
		//System.out.println("Enter User name");
		//String username=sc.nextLine();
		//System.out.println("Enter password");
		//String password=sc.nextLine();
		try {
			pstm = (ConnectionInstance.getInstance()).prepareStatement("Select Employeeid from employee where username=? and password=?");
			pstm.setString(1, username);
			pstm.setString(2, password);
			ResultSet re= pstm.executeQuery();
			while(re.next()) {
				int a=re.getInt(1);
				return a;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}

	public void moneyTransfer(int accountno, int taccountno, float amount) {
		PreparedStatement pstm =null;
		/*
		 * Scanner sc=new Scanner(System.in);
		 * System.out.println("Enter your account number"); int accountno=sc.nextInt();
		 * System.out.println("Enter account number to trasfer the money"); int
		 * taccountno=sc.nextInt(); System.out.println("Enter amount to tranfer"); float
		 * amount=sc.nextFloat();
		 */
		float bal=0;
		try {
			pstm=(ConnectionInstance.getInstance()).prepareStatement("Select balance from customer where accountNo=?");
			pstm.setInt(1, accountno);
			ResultSet racc=pstm.executeQuery();
			pstm=(ConnectionInstance.getInstance()).prepareStatement("Select balance from customer where accountNo=?");
			pstm.setInt(1, taccountno);
			ResultSet racc1=pstm.executeQuery();
			if(racc.next()) {
				if (racc1.next()) {
					bal=racc1.getFloat(1);
					pstm=(ConnectionInstance.getInstance()).prepareStatement("Select balance from customer where accountno=?" );
					pstm.setInt(1, accountno);
					ResultSet rs= pstm.executeQuery();
					while(rs.next()) {
						float ba=rs.getInt(1);
					if(ba>amount & ba>=0) {
						ba=ba-amount;
						pstm=(ConnectionInstance.getInstance()).prepareStatement("UPDATE Customer set balance= ? where accountno=?");
						pstm.setFloat(1, ba);
						pstm.setInt(2, accountno);
						int r1=pstm.executeUpdate();
						//pstm=(ConnectionInstance.getInstance()).prepareStatement("Select balance from customer where accountNo=?");
						//pstm.setInt(1, taccountno);
						//ResultSet r=pstm.executeQuery();
						//while(r.next()) {
							//float bal=r.getInt(1);
							bal=bal+amount;
							pstm=(ConnectionInstance.getInstance()).prepareStatement("UPDATE Customer set balance= ? where accountno=?");
							pstm.setFloat(1, bal);
							pstm.setInt(2, taccountno);
							int result =pstm.executeUpdate();
							pstm=(ConnectionInstance.getInstance()).prepareStatement("insert into transaction(accountno,amount,mode) values(?,?,?)");
							pstm.setInt(1, accountno);
							pstm.setFloat(2, amount);
							pstm.setString(3, "Transfer");
							pstm.executeUpdate();
							if(result!=0) {
								log.info("Amount tranfer comleted");
							}
							else {
								log.info("enter amount is invalid");
							}
						//}
						}
					else {
						log.info("Enter amount is invalid");
					}
					
					
					}
				}
				else {
					log.info("Invalid account number");
				}
			}
			else {
				log.info("Invalid account number");
			}

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

	public void withdraw(int accountno,float amount) {
		PreparedStatement pstm =null;
		/*
		 * Scanner sc=new Scanner(System.in);
		 * System.out.println("Enter account number"); int accountno=sc.nextInt();
		 * System.out.println("Enter amount to withdraw"); float amount=sc.nextFloat()
		 */;
		try {
			pstm=(ConnectionInstance.getInstance()).prepareStatement("Select balance from customer where accountno=?" );
			pstm.setInt(1, accountno);
			ResultSet rs= pstm.executeQuery();
			while(rs.next()) {
				float ba=rs.getInt(1);
			
			if(ba>amount) {
				ba=ba-amount;
				pstm=(ConnectionInstance.getInstance()).prepareStatement("UPDATE Customer set balance= ? where accountno=?");
				pstm.setFloat(1, ba);
				pstm.setInt(2, accountno);
				int result =pstm.executeUpdate();
				pstm=(ConnectionInstance.getInstance()).prepareStatement("insert into transaction(accountno,amount,mode) values(?,?,?)");
				pstm.setInt(1, accountno);
				pstm.setFloat(2, amount);
				pstm.setString(3, "withdraw");
				pstm.executeUpdate();
				if(result!=0) {
					log.info("Amount withdraw successful");
				}
			}
			else {
				log.info("enter amount is invalid");
			}
			
		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void checkBalance(int accountno) {
		PreparedStatement pstm= null;
		try {
			//Scanner sc = new Scanner(System.in);
			//System.out.println("Enter account number:");
			//int accountno= sc.nextInt();
			pstm=(ConnectionInstance.getInstance()).prepareStatement("Select balance from customer where accountNo=?");
			pstm.setInt(1, accountno);
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
				log.info("Available balance: "+rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void getData() {
		PreparedStatement pstm= null;
		try {
			//Scanner sc = new Scanner(System.in);
			//System.out.println("Enter account number:");
			//int accountno= sc.nextInt();
			pstm=(ConnectionInstance.getInstance()).prepareStatement("Select * from tempcus");
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getFloat(3));
			System.out.println(rs.getString(4));
			System.out.println(rs.getString(5));
			System.out.println(rs.getInt(6));
			System.out.println(rs.getString(7));
			System.out.println(rs.getString(8));
			System.out.println(rs.getInt(9));
			System.out.println(rs.getString(10));
			System.out.println(rs.getInt(11));
			System.out.println("--------------------------------------");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void getTransactionData() {
		PreparedStatement pstm= null;
		try {
			//Scanner sc = new Scanner(System.in);
			//System.out.println("Enter account number:");
			//int accountno= sc.nextInt();
			pstm=(ConnectionInstance.getInstance()).prepareStatement("Select * from transaction");
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
				log.info(rs.getInt(1));
				log.info(rs.getInt(2));
				log.info(rs.getFloat(3));
				log.info(rs.getString(4));
				log.info("----------------------------------");
			
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void getCustomerData(int accountno) {
		PreparedStatement pstm= null;
		try {
			//Scanner sc = new Scanner(System.in);
			//System.out.println("Enter account number:");
			//int accountno= sc.nextInt();
			pstm=(ConnectionInstance.getInstance()).prepareStatement("Select * from customer where accountno=?");
			pstm.setInt(1, accountno);
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
				log.info("Account number: "+rs.getInt(1));
				log.info("user name: "+rs.getString(2));
				log.info("Password: "+rs.getString(3));
				log.info("Balance: "+rs.getFloat(4));
				log.info("First name: "+rs.getString(5));
				log.info("Last name "+rs.getString(6));
				log.info("Age:" +rs.getInt(7));
				log.info("phone number: "+rs.getString(8));
				log.info("email: "+rs.getString(9));
				log.info("aadhar number: "+rs.getInt(10));
			
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void adduser() {
		PreparedStatement pstm =null;
		Scanner sc = new Scanner(System.in);
		log.info("Enter first name:");
		String fname= sc.nextLine();
		log.info("Enter last name:");
		String lname= sc.nextLine();
		log.info("Enter your age:");
		int age= sc.nextInt();
		sc.nextLine();
		log.info("Enter aadhar number:");
		String aadharno= sc.nextLine();
		log.info("Enter phone number:");
		String phno= sc.nextLine();
		log.info("Enter email:");
		String email= sc.nextLine();
		log.info("Create user name:");
		String username= sc.nextLine();
		log.info("create Password");
		String password= sc.nextLine();
		log.info("Enter intial deposite amount");
		float balance = sc.nextFloat();
		
		try {
			String sql="insert into temporarycustomer(username,password,balance,fname,lname,age,phono,email,aadharno) values(?,?,?,?,?,?,?,?,?)";
			pstm=(ConnectionInstance.getInstance()).prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setString(2, password);
			pstm.setFloat(3, balance);
			pstm.setString(4, fname);
			pstm.setString(5, lname);
			pstm.setInt(6, age);
			pstm.setString(7, phno);
			pstm.setString(8, email);
			pstm.setString(9, aadharno);
			int result = pstm.executeUpdate();
			if(result!=0) {
				log.info("customer details added successfully");
			}
			else {
				log.info("customer details Not added ");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
		
	}
	public void addCustomerDetails(int id) {
		
		String username = null,password = null,fname = null,lname = null,phno = null,email = null;
		int age = 0,aadharno = 0;
		float balance = 0;
		try {
			PreparedStatement pstm=(ConnectionInstance.getInstance()).prepareStatement("Select username,password,balance,fname,lname,age,phono,email,aadharno from tempcus where id=?");
			pstm.setInt(1, id);
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
			username =rs.getString(1);
			password=rs.getString(2);
			balance=rs.getFloat(3);
			fname=rs.getString(4);
			lname=rs.getString(5);
			age=rs.getInt(6);
			phno=rs.getString(7);
			email =rs.getString(8);
			aadharno=rs.getInt(9);
		}
		String sql=" insert into customer(username,password,balance,fname,lname,age,phono,email,aadharno) select ?,?,?,?,?,?,?,?,? from tempcus where id=?";
		PreparedStatement pstm1=(ConnectionInstance.getInstance()).prepareStatement(sql);
		pstm1.setString(1, username);
		pstm1.setString(2, password);
		pstm1.setFloat(3, balance);
		pstm1.setString(4, fname);
		pstm1.setString(5, lname);
		pstm1.setInt(6, age);
		pstm1.setString(7, phno);
		pstm1.setString(8, email);
		pstm1.setInt(9, aadharno);
		pstm1.setInt(10, id);
		int result = pstm1.executeUpdate();
		if(result!=0) {
			log.info("customer details added successfully");
		}
		else {
			log.info("customer details Not added ");
		}
		
	 
		}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
}
	public void statusUpdate(int id, String status) {
		PreparedStatement pstm =null;
		try {
		pstm=(ConnectionInstance.getInstance()).prepareStatement("UPDATE tempcus set status= ? where id=?");
		pstm.setString(1, status);
		pstm.setInt(2, id);
		pstm.executeUpdate();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	public int validid(int id) {
		PreparedStatement pstm =null;
		int a=0;
		try {
		pstm=(ConnectionInstance.getInstance()).prepareStatement(" select id from tempcus where id= ?");
		pstm.setInt(1, id);
		ResultSet rs= pstm.executeQuery();
		while(rs.next()) {
			a=1;
		}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	if(a==1) {
		return 1;
	}
	else {
		return 0;
	}
	}
	
}
