package edu.kh.emp.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.emp.model.vo.Employee;

// DAO(Data Access Object, 데이터 접근 객체)
// -> 데이터베이스에 접근(연결)하는 객체
// --> JDBC 코드 작성
public class EmployeeDAO {


	private Scanner sc = new Scanner(System.in);
	
	
	// JDBC 객체 참조 변수 선언 필드 선언(class 내부에 공통 사용)
	// 필드에서 생성해주면 heap영역으로 저장된다
	
	
	// 지역변수인 stack에 저장되면 따로 null로 초기화를 해주어야 하지만
	// haep 영역은 비어있을수 없으므로 null이 기본값으로 초기화 해준다
	// 별도로 null로 초기화 해주지 않아도 된다
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;
	// Statement의 자식으로 좀 더 향상된 기능을 제공
	//-> ? 기호(placeholder / 위치홀더)
	// sql에 작성되어지는 리터럴을 동적으로 제어함
	
	// SQL ? 에 추가되는 값은
	// 숫자인경우에는 '' 없이 대입
	// 문자열인 경우에는 ''가 자동으로 추가되어 대입
	
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "kh";
	private String pw = "kh1234";
	
	
	
	
	
	/** 전체사원 정보 조회 DAO
	 * @return empList
	 */
	public List<Employee> selectAll() {
		// 1. 결과 저장용 변수 선언
		List<Employee> empList = new ArrayList<>();
		
		try {
			// 2. JDBC 참조 변수에  객체 대입
			//-> conn, stmt, rs에 객체 대입
			Class.forName(driver); // 오라클 jdbc 드라이버 객체 메모리 로드
			
			conn = DriverManager.getConnection(url, user, pw);
			// 오라클 jdbc 드라이버 객체를 이용하여 db접속
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,"
					+ " NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE,"
					+ " JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)"
					+ " JOIN JOB USING(JOB_CODE)";
			
			stmt = conn.createStatement(); // STATEmet 객체 생성
			
			rs = stmt.executeQuery(sql); // sql 수행후 결과를 받음
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID"); // EMP_ID 컬럼은 문자열 컬럼이지만 저장된 값들이 모두 숫자이므로 int자료형을 사용
				String empName= rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
				empList.add(emp); // List담기
				
			} //while 종료
			
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			//4. JDBC 객체 자원반환 
			try {
				if(rs !=null) rs.close();
				if(stmt !=null) stmt.close();
				if(conn !=null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// 5. 결과 반환
		return empList;
	}
	
	
	
	/**사번으로 사원 조회
	 * @param empId
	 * @return empList
	 */
	public Employee selectEmpId(int empId)  {
	
		Employee emp = null;
		
			try {
			
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,"
					+ " NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE,"
					+ " JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)"
					+ " JOIN JOB USING(JOB_CODE)"
					+ " WHERE EMP_ID = ?";
			
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, empId);
			
			
			rs = pstmt.executeQuery();

			
			if(rs.next()) {
				
				
				String empName= rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return emp;
	}
	
	/**
	 * @param empNo 주민번호가 같은 사원 조회
	 * @return emp
	 */
	public Employee selectEmpNo(String empNo) {
		
		//결과 저장용 변수 선언
		Employee emp = null;
		
		try {
			
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,"
					+ " NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE,"
					+ " JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)"
					+ " JOIN JOB USING(JOB_CODE)"
					+ " WHERE EMP_NO = ?"; // ? : placeholder 나중에 어떤 값이 입력될 자리
			
			// Statement 객체 사용 시 순거
			// SQL작성 -> Statement 생성 -> SQL 수행 후 결과 반환
			
			// PreparedStatement 객체 사용시 순서
			// SQL작성
			// -> PreparedStatement 객체 생성( ? 가 초함된 SQL을 매개변수로 사용)
			// ->  ?에 알맞은 결과 대입
			// SQL 수행 후 결과 반환
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값 대입
			pstmt.setString(1, empNo); // ?에 empNo를 대입 한다
			
			// SQL 수행 후 결과 반환
			rs = pstmt.executeQuery();
			// PreparedStatment는
			// 객체 생성시 이미 SQL이 담겨져 있는 상태이므로
			// SQL 수행 pstmt.executeQuery(); 시 매개변수로 전달할 피요가 없다
			
			// pstmt.executeQuery();
			// -> ? 에 작성 되어있던 값이 모두 사라져 수행시 오류발생
			
			if(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName= rs.getString("EMP_NAME");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				//EMP_NO는 파라미터와 같은 값이므로 불필요
				emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return emp;
	}
	
	/**
	 * 사원 정보 수정
	 * @param emp
	 * @return result(INSERT 성공한 행의 개수 반환)
	 */
	public int insertEmployee(Employee emp) {
		
		//결과 저장용 변수 선언
		int result = 0;
		
		try {
			
			//커넥션 생성
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			
			// **DML 수행할 예정 **
			// 트랙잭션에 DML 구문이 임시 저장
			// -> 정상적인 DML인지를 판별해서 개발자가 직접 commit, rollback을 수행
			
			conn.setAutoCommit(false); // auto commit 비활성화
									   // Connection 객체 생성시 커밋이 자동으로 되어있다 rollback을 수행하기 위해 꺼준다
			
			// AutoCommit을 비활성화 해도 conn.close(); 구문이 수행되면 Commit이 자동으로 수행된다
			// --> clsoe(0 수행전에 트랜잭션 제어 코드를 작성해야 한다
			
			//SQL 작성
			String sql = "INSERT INTO EMPLOYEE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , SYSDATE, NULL, DEFAULT)";
					
			//preparedSteatment 객체 생성( 매개 변수에 SQL 추가
			pstmt = conn.prepareStatement(sql);
				
			//?에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			// SQL 수행 후 결과 반환 받기
			result = pstmt.executeUpdate();
			// executeQuery() : SELET 후 resultset 반환
			// .executeUpdate() : DML(INSERT, UPDATE, DELETE) 수행 후 결과 행 개수 반환
			
			
			
			//트랜잭션 제어 처리
			// ->DML 성공 여부에 따라서 commit rollback 제어
			if(result > 0) conn.commit(); // DML 성공시 commit
			else	conn.rollback(); // DML 실패시 rollback
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(pstmt !=null) pstmt.close();
				if(conn != null) conn.close();
				//값이 바로 수정값에 들어가서 resultSet이 필요 없다
				
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		return 0;
	}
	
	
	/** 사번이 일치하는 사원 정보 수정
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Employee emp) {
		int result = 0;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			conn.setAutoCommit(false);
			
			String sql = "UPDATE EMPLOYEE SET"
					+ " EMAIL = ?, PHONE = ?, SALARY = ?"
					+ " WHERE EMP_ID = ?";
			
			//pstmt 생성
			pstmt = conn.prepareStatement(sql);
			
			//? 에 알맞는 값 세팅
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate();;
			
			
			
			//트랜젝션 제어 처리
			if(result == 0) conn.rollback();
			else conn.commit();
			
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			 try {
				 if(pstmt !=null) pstmt.close();
				 if(conn !=null) conn.close();
				 
			 } catch (SQLException e) {
				 e.printStackTrace();
			 }
		}
		
		
		
		
	return result;
	}
	
	
	/** 사번이 일치하는 사원 정보 삭제
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(int empId) {
		
		int result = 0;// 결과 저장용
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			conn.setAutoCommit(false);
			
			
			String sql = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			
			result = pstmt.executeUpdate();
			
			
			//트랜젝션 제어 처리
			if(result == 0) conn.rollback();
			else conn.commit();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	
	/** 입력받은 부서의 모든 사원 조회
	 * @param deptTitle
	 * @return empList
	 */
	public List<Employee> selectDeptEmp(String departmentTitle) {
		
		List<Employee> empList = new ArrayList<>();
		
		try { 
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,"
					+ " NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE,"
					+ " JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)"
					+ " JOIN JOB USING(JOB_CODE)"
					+ " WHERE DEPT_TITLE = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, departmentTitle); 
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID"); // EMP_ID 컬럼은 문자열 컬럼이지만 저장된 값들이 모두 숫자이므로 int자료형을 사용
				String empName= rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
				empList.add(emp);
			
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return empList;
	}

	/**입력한 금액 이상으로 받는 사원 조회
	 * @param salary
	 * @return empList
	 */
	public List<Employee> selectSalaryEmp(int salary) {
		
		List<Employee> empList = new ArrayList<>();
		
		try { 
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,"
					+ " NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE,"
					+ " JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)"
					+ " JOIN JOB USING(JOB_CODE)"
					+ " WHERE SALARY >= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, salary); 
			
			
			rs = pstmt.executeQuery();
			
				while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID"); // EMP_ID 컬럼은 문자열 컬럼이지만 저장된 값들이 모두 숫자이므로 int자료형을 사용
				String empName= rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
				empList.add(emp);
			
				}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return empList;
	}


}









