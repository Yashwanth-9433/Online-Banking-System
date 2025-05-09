package org.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jdbc.AccountsRowMapper;
import org.jdbc.Accounts;
import org.jdbc.AccountsDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class AccountsDAOImp implements AccountsDAO{
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void register(Accounts account) {
		// TODO Auto-generated method stub
		String sql="insert into accounts(acct_no, name, email, password, amount) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, account.getId(), account.getName(), account.getEmail(), account.getPassword(), account.getAmount());
	}

	@Override
	public float balance(int acct_no) {
		// TODO Auto-generated method stub
		String sql="select * from accounts where acct_no=?";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {acct_no}, new AccountsRowMapper()).getAmount();
	}

	@Override
	public String update_pswd(int acct_no, String old_pswd, String new_pswd) {
		// TODO Auto-generated method stub
		if (check(acct_no, old_pswd)) {
			String sql="UPDATE accounts SET password=? WHERE acct_no=?";
			jdbcTemplate.update(sql, new_pswd, acct_no);
			return "Password Updated Successfully";
		}
		else {
			return "Wrong Password";
		}
	}

	@Override
	public boolean check(int acct_no, String pswd) {
		// TODO Auto-generated method stub
		Accounts acct = null;
		try {
		    String sql = "select * from accounts where acct_no=?";
		    acct = jdbcTemplate.queryForObject(sql, new Object[] {acct_no}, new AccountsRowMapper());
		    if (acct.getPassword().equals(pswd)) {
				return true;
			}
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
		    return false;
		}
		
		
	}

	@Override
	public String deposit(int acct_no, String pswd, float amount) {
		// TODO Auto-generated method stub
		if (check(acct_no, pswd)) {
			float bal=balance(acct_no);
			String sql="UPDATE accounts SET amount=? WHERE acct_no=?";
			jdbcTemplate.update(sql, bal+amount, acct_no);
			return "Deposit was Successful";
		}
		else {
			return "Wrong Password";
		}
		
	}

	@Override
	public String withdraw(int acct_no, String pswd, float amount) {
		// TODO Auto-generated method stub
		if (check(acct_no, pswd)) {
			float bal=balance(acct_no);
			if (bal>=amount) {
				String sql="UPDATE accounts SET amount=? WHERE acct_no=?";
				jdbcTemplate.update(sql, bal-amount, acct_no);
				return "Withdrawal was Successful";
			}
			return "Not enough balance in account";
		}
		else {
			return "Wrong Password";
		}
		
	}

	@Override
	public String transfer(int acct_no1, String pswd, int acct_no2, float amount) {
		// TODO Auto-generated method stub
		String res1=withdraw(acct_no1, pswd, amount);
		if (res1=="Wrong Password") {
			return "Wrong Acct_no and/or Password";
		}
		else if (res1=="Not enough balance in account") {
			return res1;
		}
		else {
			float bal=balance(acct_no2);
			String sql="UPDATE accounts SET amount=? WHERE acct_no=?";
			jdbcTemplate.update(sql, bal+amount, acct_no2);
			return "Money transfer made successful";
		}
	}

	@Override
	public float close(int acct_no) {
		// TODO Auto-generated method stub
		float bal=balance(acct_no);
		String sql="DELETE FROM accounts WHERE acct_no=?";
		jdbcTemplate.update(sql, acct_no);
		return bal;
	}
	
	
	@Override
	public String getName(int acct_no) {
		// TODO Auto-generated method stub
		String sql="select * from accounts where acct_no=?";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {acct_no}, new AccountsRowMapper()).getName();
		
	}

	@Override
	public List<Map<String, Object>> retrieve(int acct_no, String pswd) {
		// TODO Auto-generated method stub
		if (check(acct_no, pswd)) {
			String sql="select * from transactions where acct_no=?";
			
			return jdbcTemplate.queryForList(sql, new Object[] {acct_no}, new AccountsRowMapper());
		}
		else {
			return null;
		}
	}

	
}

class AccountsRowMapper implements RowMapper<Accounts>{
	
	@Override
	public Accounts mapRow(ResultSet rs, int rowNum)throws SQLException{
		
		Accounts acc=new Accounts();
		acc.setId(rs.getInt("acct_no"));
		acc.setName(rs.getString("name"));
		acc.setEmail(rs.getString("email"));
		acc.setPassword(rs.getString("password"));
		acc.setAmount(rs.getFloat("amount"));
		return acc;
	}
}


