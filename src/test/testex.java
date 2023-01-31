package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testex {
	public static void main(String[] args) {
		
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
}
		
