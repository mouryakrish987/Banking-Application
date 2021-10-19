package com.bankapp;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.dbconection.DatabaseConnection;


public class BankAppMain {
	public static final Logger log=Logger.getLogger(BankAppMain.class);
	public static void main(String[] args) {
		BankAppMain ba= new BankAppMain();
		ba.menu();
	}

	public void menu() {
		int about; 
		ExistingCustomer ec= new ExistingCustomer();
		DatabaseConnection dc =new DatabaseConnection();
		do {
		Scanner s=new Scanner(System.in);
		log.info("1. Employee login");
		log.info("2. customer login");
		log.info("3. new customer");
		log.info("4. Exit");
		log.info("Enter your choice:");
		about=s.nextInt();
		switch (about) {
		case 1:
			//employee
			EmployeeAccess ea =new EmployeeAccess();
			ea.employeeMenu();
			break;
		case 2:
			ec.customerMenu();
			break;
		case 3:
			dc.adduser();
			break;
		case 4:
			System.exit(0);
			break;
		default:
			log.info("Enter valid number:");
			menu();
			break;
		}
		s.close();
		}while(about >1||about<5);
	}
}
