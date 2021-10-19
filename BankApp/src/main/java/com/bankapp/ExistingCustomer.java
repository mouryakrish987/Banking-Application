package com.bankapp;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.dbconection.DatabaseConnection;


  


public class ExistingCustomer {
	
	public static final Logger log=Logger.getLogger(BankAppMain.class);
	
	public void customerMenu() {
		DatabaseConnection dc =new DatabaseConnection();
		BankAppMain ba= new BankAppMain();
		int ch = 0;
		String username ,password;
		Scanner s=new Scanner(System.in);
		log.info("Enter user name:");
		username=s.nextLine();
		log.info("Enter password:");
		password=s.nextLine();
		int accountno=dc.customerlogin(username,password);
		if(accountno!=0) {
		do {
			log.info("1. Check balance");
			log.info("2. withdraw");
			log.info("3. Money transfer");
			log.info("4. Exit");
			log.info("Enter your choice");
			ch=s.nextInt();
		
			switch (ch) {
			case 1:
				dc.checkBalance(accountno);
				break;
			case 2:
				log.info("Enter amount to withdraw:");
				float amount= s.nextFloat();
				dc.withdraw(accountno, amount);
				break;
			case 3:
				log.info("Enter account number to transfer");
				int taccountno= s.nextInt();
				log.info("Enter amout to tranfer");
				float tamount=s.nextFloat();
				dc.moneyTransfer(accountno, taccountno, tamount);
				break;
			case 4:
				
				ba.menu();
				break;
			default:
				log.info("Enter valid number");
				customerMenu();
				break;
		}
		
		}while(ch<=5 || ch>0);
		}
		else {
			log.info("Invalid choice");
			customerMenu();
		}
		s.close();
	}
	
	

}
