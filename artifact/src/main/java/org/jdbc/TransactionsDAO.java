package org.jdbc;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TransactionsDAO {
	void entry (int from, String name1, int to, String name2, String pswd, float amt);
	List<Map<String, Object>> retrieve(int acct_no, String pswd);
}
