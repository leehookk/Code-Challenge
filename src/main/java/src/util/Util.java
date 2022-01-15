package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.pojo.InventoryProduct;

public class Util {

	public static void main(String[] args) {
		setUpDB();
	}
	
	private static void setUpDB() {

		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:inventory.db");
			Statement st = conn.createStatement();
			st.setQueryTimeout(30); 
			st.executeUpdate("DROP TABLE IF EXISTS product");
			st.executeUpdate("create table product (productId INTEGER PRIMARY KEY, productName string, quantity integer)");
			st.executeUpdate("insert into product values(1, 'Bottle', 6)");
			st.executeUpdate("insert into product values(2, 'Mouse', 2)");
			st.executeUpdate("insert into product values(3, 'Keyboard', 3)");
			st.executeUpdate("insert into product values(4, 'Wire', 7)  ");
			st.executeUpdate("insert into product values(5, 'Charger', 4)");
			st.executeUpdate("insert into product values(6, 'Phone', 2)");
			st.executeUpdate("insert into product values(7, 'Spring', 7)");
			st.executeUpdate("insert into product values(8, 'Brush', 100)");
			st.executeUpdate("insert into product values(9, 'Lamp', 10)");
			st.executeUpdate("insert into product values(10,'Table', 20)");
			ResultSet rs = st.executeQuery("select * from product");
			while (rs.next()) {
				System.out.println(new InventoryProduct(rs.getInt("productId"), rs.getString("productName"), rs.getInt("quantity")));
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	
	}

	private Util() {
		// UTIL class, private constructor
	}

	public static List<InventoryProduct> hue() {
		List<InventoryProduct> list = new ArrayList<>(10);
		list.add(new InventoryProduct(1, "Bottle", 6));
		list.add(new InventoryProduct(2, "Mouse", 2));
		list.add(new InventoryProduct(3, "Keyboard", 3));
		list.add(new InventoryProduct(4, "Wire", 7));
		list.add(new InventoryProduct(5, "Charger", 4));
		list.add(new InventoryProduct(6, "Phone", 2));
		list.add(new InventoryProduct(7, "Spring", 7));
		list.add(new InventoryProduct(8, "Brush", 100));
		list.add(new InventoryProduct(9, "Lamp", 10));
		list.add(new InventoryProduct(10, "Table", 20));

		return list;
	}
}
