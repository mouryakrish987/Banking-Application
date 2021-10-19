package com.bankapp;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.dbconection.DatabaseConnection;

public class EmployeeAccess {
	public static final Logger log=Logger.getLogger(BankAppMain.class);
	public void employeeMenu(){ 
		DatabaseConnection dc =new DatabaseConnection();
		BankAppMain ba= new BankAppMain();
		int ch;
		String uname ,password;
		Scanner s=new Scanner(System.in);
		log.info("Enter user name:");
		uname=s.nextLine();
		log.info("Enter password:");
		password=s.nextLine();
		int astatus;
		int id = 0;
		int loginstatus=dc.employeelogin(uname, password);
		if(loginstatus!=0)
		{
			do {
				log.info("1. Display new Customer");
				log.info("2 Display Customer account");
				log.info("3. Display Transaction");
				log.info("4. logout");
				
				log.info("Enter your choice:");
				ch=s.nextInt();
				switch (ch) {
				case 1:
					dc.getData();
					log.info("Enter 1 for create a account else any number to reject the customer: ");
					astatus=s.nextInt();
					log.info("Eneter id");
					id=s.nextInt();
					if(astatus==1) {
						
						int res=dc.validid(id);
						if(res==1) {
							dc.statusUpdate(id, "added");
							dc.addCustomerDetails(id);
						}
						else {
							log.info("Invalid id");
						}
					}
					else {
						log.info("Account rejucted");
						dc.statusUpdate(id, "Rejucted");
					}
					break;
				case 2:
					log.info("Enter account number to display customer details");
					int accountno=s.nextInt();
					dc.getCustomerData(accountno);
					break;
				case 3:
					dc.getTransactionData();
					break;
				case 4:
					
					ba.menu();
					break;
				default:
					log.info("Enter valid number");
					employeeMenu();
					break;
				}
			}while(ch <=4 ||ch>0);
		}
		else {
			log.info("invalid login ");
			employeeMenu();
	}
		s.close();
	}
}

