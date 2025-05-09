package org.jdbc;
import java.sql.Date;
//rishi su
import org.jdbc.AccountsDAOImp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.jdbc.TransactionsRowMapper;
import org.jdbc.Transactions;
import org.jdbc.TransactionsDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class TransactionsDAOImp implements TransactionsDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void entry(int from, String name1, int to, String name2, String pswd, float amt) {
		// TODO Auto-generated method stub
		LocalDate today = LocalDate.now();
		String sql="insert into transactions(`from`,`name1`,`to`, `name2`,`pswd`,`date`, amount) values(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, from, name1, to, name2, pswd, Date.valueOf(today), amt);
		return;
	}

	@Override
	public List<Map<String, Object>> retrieve(int acct_no, String pswd) {
		// TODO Auto-generated method stub
		
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	   	AccountsDAO dao=(AccountsDAO) ctx.getBean("AccountsDAO");
	   	
		if (dao.check(acct_no, pswd)) {
			String sql="select * from transactions where `from`=? or `to`=?";
			
			return jdbcTemplate.queryForList(sql, acct_no, acct_no);
		}
		else {
			return null;
		}
	}

}

class TransactionsRowMapper implements RowMapper<Transactions>{
	
	@Override
	public Transactions mapRow(ResultSet rs, int rowNum)throws SQLException{
		
		Transactions ob=new Transactions();
		ob.setFrom(rs.getInt("from"));
		ob.setName1(rs.getString("name1"));
		ob.setTo(rs.getInt("to"));
		ob.setName2(rs.getString("name2"));
		ob.setPswd(rs.getString("pswd"));
		ob.setDate(rs.getDate("date"));
		return ob;
	}
}
