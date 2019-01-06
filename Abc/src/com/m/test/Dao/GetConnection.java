package com.m.test.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.m.test.pojo.Book;
import com.m.test.pojo.BooksBean;


public class GetConnection {
	
	private static Gson gson = new Gson();
	private static Connection connection = null;
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/laoliu", "debian-sys-maint", "IJZiPVqqwd9BoHQD");
	}
	
	private static void getClose(Connection connection, Statement st, PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (st != null) {
			st.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println(Query());
	}
	
	public static String Query() throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		List<Book> booksList = new ArrayList<>();
		getConnection();
		
		String sql = "SELECT * FROM book;";
		System.out.println(sql);
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			// 声明一本书的类，并且往里添加数据，一一对应
		Book book = new Book();
			book.setbkno(rs.getInt("bkno"));
			book.setbkname(rs.getString("bkname"));
			book.setbkmoney(rs.getString("bkmoney"));
			// 最后把这本书添加到书的集合列表 booksList
//			booksList.add(book);
			System.out.println(book.getbkno()+" "+book.getbkname()
			+" "+book.getbkmoney()+" ");
		}
		/*
		 * n 本书的集合列表 BooksBean
		 * 声明 BooksBean 的 POJO 实体类
		 * 实际参见 top.gendseo.books.pojo.BooksBean
		 */
		BooksBean booksBean = new BooksBean();
		// 图书的列表
		booksBean.setRows(booksList);
		// 图书的总数
		booksBean.setTotal(String.valueOf(booksList.size()));
		
		getClose(connection, null, ps, rs);
		return gson.toJson(booksBean);
	}
	public static String DELETE(String bkno) throws ClassNotFoundException, SQLException {
		Statement st = null;
		getConnection();
		
		st = connection.createStatement();
		String sql = "DELETE FROM book WHERE \"bkno\" in (" + bkno + ");";
		System.out.println(sql);
		// executeUpdate 不同于 executeQuery
		// executeUpdate 执行更新操作，不返回任何结果
		st.executeUpdate(sql);
		
		getClose(connection, st, null, null);
		return "true";
	}
	public static String UPDATE(String json) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		getConnection();
		
		// 使用 Gson 将 JSON 转换成 POJO 实体类 Book
		Book book = gson.fromJson(json, Book.class);
		String sql = "UPDATE book SET \"bkname\" = ?,\"bkmoney\" = ?,\"bkno\" = ?;";
		System.out.println(sql);
		
		ps = connection.prepareStatement(sql);
		ps.setString(1, book.getbkname());
		ps.setString(2, book.getbkmoney());
		ps.setInt(3, book.getbkno());
		ps.executeUpdate();
		
		getClose(connection, null, ps, null);
		return "true";
	}
	public static String INSERT(String json) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		getConnection();

		// 使用 Gson 将 JSON 转换成 POJO 实体类 Book
		Book book = gson.fromJson(json, Book.class);
		String sql = "INSERT INTO book (\"bkno\", \"bkname\", \"bkmoney\") VALUES (?, ?, ?);";
		 System.out.println(sql);

			
		ps = connection.prepareStatement(sql);
		ps.setInt(1, book.getbkno());
		ps.setString(2, book.getbkname());
		ps.setString(3, book.getbkmoney());
		ps.executeUpdate();
		
		getClose(connection, null, ps, null);
		return "true";
	}
}
