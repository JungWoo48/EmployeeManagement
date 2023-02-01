package edu.kh.emp.run;

import edu.kh.emp.view.EmployeeView;

public class EmployeeRun {
	public static void main(String[] args) {
		
		//EmployeeView view = new EmployeeView();
		
		//view.displayMenu();
		
		//1. run - 2. View - 3. DAO - 4. Vo 순서로 진행
		
		new EmployeeView().displayMenu();
	}

}
