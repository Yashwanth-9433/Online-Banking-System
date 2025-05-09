package org.jdbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {

	public static void main(String[] args) {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		AccountsDAO dao=(AccountsDAO) ctx.getBean("AccountsDAO");
		// TODO Auto-generated method stub
		Accounts account=new Accounts();
		account.setId(002);
		account.setName("Khun");
		account.setEmail("AA@gmail.com");
		account.setPassword("baam");
		account.setAmount(100);
//		dao.register(account);
		System.out.println(dao.balance(001));
		System.out.println(dao.balance(002));

//		System.out.println(dao.deposit(002, "baam", 100));
//		System.out.println(dao.balance(002));
//		
//		System.out.println(dao.withdraw(002, "baam", 50));
//		System.out.println(dao.balance(002));
//		
////		System.out.println(dao.update_pswd(001, "camp", "camp1"));
//		
//		System.out.println(dao.transfer(001, "camp", 002, 100));
		
		
		((ClassPathXmlApplicationContext) ctx).close();
	}

}
