package edu.kh.emp.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

//import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

// 화면용 클래스( 입력(Scanner) / 출력(print()) )
public class EmployeeView {

	private Scanner sc = new Scanner(System.in);
	

	
	// DAO 객체 생성
	//private EmployeeDAO dao = new EmployeeDAO();
	
	
	
	// 메인 메뉴
	public void displayMenu() {
		
		int input = 0;
		
		do {
			try {
				System.out.println("---------------------------------------------------------");
				System.out.println("----- 사원 관리 프로그램 -----");
				System.out.println("1. 새로운 사원 정보 추가");
				System.out.println("2. 전체 사원 정보 조회");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				
				System.out.println("6. 입력 받은 부서와 일치하는 모든 사원 정보 조회");
				// selectDeptEmp()
				
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				// selectSalaryEmp()
				
				System.out.println("8. 부서별 급여 합 전체 조회");
				// selectDeptTotalSalary()
				// DB 조회 결과를 HashMap<String, Integer>에 옮겨 담아서 반환
				// 부서코드, 급여 합 조회
				
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				
				System.out.println("10. 직급별 급여 평균 조회");
				// selectJobAvgSalary()
				// DB 조회 결과를 HashMap<String, Double>에 옮겨 담아서 반환 
				// 직급명, 급여 평균(소수점 첫째자리) 조회
				
				
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 >> ");
				input = sc.nextInt();
				sc.nextLine(); //  추가!
				
				
				System.out.println();				
				
				
				switch(input) {
				case 1:  insertEmployee();   break;
				case 2:  selectAll();  break;
				case 3:  selectEmpId();   break;
				case 4:  updateEmployee();   break;
				case 5:  deleteEmployee();   break;
				case 6:  selectDeptEmp();   break;
				case 7:  selectSalaryEmp();   break;
				case 8:  selectDeptTotalSalary();   break;
				case 9:  selectEmpNo();   break;
				case 10: selectJobAvgSalary();   break;
				
				case 0:  System.out.println("프로그램을 종료합니다...");   break;
				default: System.out.println("메뉴에 존재하는 번호만 입력하세요.");
				}
				
				
			}catch(InputMismatchException e) {
				System.out.println("정수만 입력해주세요.");
				input = -1; // 반복문 첫 번째 바퀴에서 잘못 입력하면 종료되는 상황을 방지
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못 입력된 문자열 제거해서
							   // 무한 반복 방지
			}
			
		}while(input != 0);
		
	}
	
	
	/**
	 * 전체 사원 정보 조회
	 */
	public void selectAll() {
		Connection conn = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, PHONE, DEPT_CODE, JOB_CODE, SAL_LEVEL, SALARY, BONUS, MANAGER_ID, HIRE_DATE, ENT_DATE, ENT_YN"
					+ " FROM EMPLOYEE";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName= rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String phone = rs.getString("PHONE");
				String deptcode = rs.getString("DEPT_CODE");
				String jobcode = rs.getString("JOB_CODE");
				String salLevel = rs.getString("SAL_LEVEL");
				int salary = rs.getInt("SALARY");
				int bonus = rs.getInt("BONUS");
				String managerId = rs.getString("MANAGER_ID");
				String hiredate = rs.getString("HIRE_DATE");
				String entdate = rs.getString("ENT_DATE");
				String entyn = rs.getString("ENT_YN");
				
				System.out.printf("사번 : %s / 이름 : %s / 주민번호 : %s / 전화번호 : %s / 부서코드 : %s / 직급 : %s / 급여단계 : %s /"
						+ " 급여 : %d / 보너스 : %d / 사수번호 : %s / 입사일 : %s / 퇴사일 : %s / 퇴직여부 : %s\n",
						empId, empName, empNo, phone, deptcode, jobcode, salLevel, salary, bonus, managerId, hiredate, entdate, entyn.toString() );
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}finally {
		
			try {
				if(rs !=null) rs.close();
				if(stmt !=null) stmt.close();
				if(conn !=null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		
		}
		
		
	}
	
	/** 전달받은 사원 List 모두 출력
	 * @param empList
	 */
	public void printAll(List<Employee> empList) {
		return;
	}
	
	
	/**
	 * 사번이 일치하는 사원 정보 조회
	 */
	public void selectEmpId() {
		Connection conn = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		try {
			
			System.out.print("사번 입력 : ");
			String inputempId = sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, PHONE, DEPT_CODE, JOB_CODE, SAL_LEVEL, SALARY, BONUS, MANAGER_ID, HIRE_DATE, ENT_DATE, ENT_YN"
					+ " FROM EMPLOYEE"
					+ " WHERE EMP_ID = '" + inputempId + "'";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName= rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String phone = rs.getString("PHONE");
				String deptcode = rs.getString("DEPT_CODE");
				String jobcode = rs.getString("JOB_CODE");
				String salLevel = rs.getString("SAL_LEVEL");
				int salary = rs.getInt("SALARY");
				int bonus = rs.getInt("BONUS");
				String managerId = rs.getString("MANAGER_ID");
				String hiredate = rs.getString("HIRE_DATE");
				String entdate = rs.getString("ENT_DATE");
				String entyn = rs.getString("ENT_YN");
				
				System.out.printf("사번 : %s / 이름 : %s / 주민번호 : %s / 전화번호 : %s / 부서코드 : %s / 직급 : %s / 급여단계 : %s /"
						+ " 급여 : %d / 보너스 : %d / 사수번호 : %s / 입사일 : %s / 퇴사일 : %s / 퇴직여부 : %s\n",
						empId, empName, empNo, phone, deptcode, jobcode, salLevel, salary, bonus, managerId, hiredate, entdate, entyn.toString() );
			
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs !=null) rs.close();
				if(stmt !=null) stmt.close();
				if(conn !=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/** 사번을 입력 받아 반환하는 메서드
	 * @return empId
	 */
	public int inputEmpId() {
		return 0;
	}
	
	
	/** 사원 1명 정보 출력
	 * @param emp
	 */
	public void printOne(Employee emp) {
		
	}
	
	
	/**
	 * 주민등록번호가 일치하는 사원 정보 조회
	 */
	public void selectEmpNo() {

		Connection conn = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		try {
			
			System.out.print("주민등록번호 입력 : ");
			String inputempNo = sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, PHONE, DEPT_CODE, JOB_CODE, SAL_LEVEL, SALARY, BONUS, MANAGER_ID, HIRE_DATE, ENT_DATE, ENT_YN"
					+ " FROM EMPLOYEE"
					+ " WHERE EMP_NO = '" + inputempNo + "'";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName= rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String phone = rs.getString("PHONE");
				String deptcode = rs.getString("DEPT_CODE");
				String jobcode = rs.getString("JOB_CODE");
				String salLevel = rs.getString("SAL_LEVEL");
				int salary = rs.getInt("SALARY");
				int bonus = rs.getInt("BONUS");
				String managerId = rs.getString("MANAGER_ID");
				String hiredate = rs.getString("HIRE_DATE");
				String entdate = rs.getString("ENT_DATE");
				String entyn = rs.getString("ENT_YN");
				
				System.out.printf("사번 : %s / 이름 : %s / 주민번호 : %s / 전화번호 : %s / 부서코드 : %s / 직급 : %s / 급여단계 : %s /"
						+ " 급여 : %d / 보너스 : %d / 사수번호 : %s / 입사일 : %s / 퇴사일 : %s / 퇴직여부 : %s\n",
						empId, empName, empNo, phone, deptcode, jobcode, salLevel, salary, bonus, managerId, hiredate, entdate, entyn.toString() );
			
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs !=null) rs.close();
				if(stmt !=null) stmt.close();
				if(conn !=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * 사원 정보 추가
	 */
	public void insertEmployee() {
		
	}
	
	
	/**
	 * 사번이 일치하는 사원 정보 수정(이메일, 전화번호, 급여)
	 */
	public void updateEmployee() {
		
	}
	
	/**
	 * 사번이 일치하는 사원 정보 삭제
	 */
	public void deleteEmployee() {
		
	}
	
	
	/**
	 * 입력 받은 부서와 일치하는 모든 사원 정보 조회
	 */
	public void selectDeptEmp() {

	}
	
	/**
	 * 입력 받은 급여 이상을 받는 모든 사원 정보 조회
	 */
	public void selectSalaryEmp() {
		
	}
	
	/**
	 * 부서별 급여 합 전체 조회
	 */
	public void selectDeptTotalSalary() {
		
	}
	
	/**
	 * 직급별 급여 평균 조회
	 */
	public void selectJobAvgSalary() {
		
		
	}
	
	
	
}