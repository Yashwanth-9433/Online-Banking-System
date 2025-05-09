package org.jdbc;

import java.util.List;
import java.util.Map;

public interface AccountsDAO {
	void register (Accounts account);
	float balance(int acct_no);//view balance
	String update_pswd(int acct_no, String old_pswd, String new_pswd);
	String deposit(int acct_no, String pswd, float amount);
	String withdraw(int acct_no, String pswd, float amount);
	String transfer(int acct_no1, String pswd, int acct_no2, float amount);
	float close(int acct_no);
	boolean check(int acct_no, String pswd);
	String getName(int acct_no);
	
	List<Map<String, Object>> retrieve(int acct_no, String pswd);
}
