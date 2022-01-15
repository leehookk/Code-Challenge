package src.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import src.exception.InventorySystemRuntimeException;
import src.pojo.InventoryProduct;

@Repository
public class InventoryProductRepository {

	private static final Logger logger = LoggerFactory.getLogger(InventoryProductRepository.class);

	Connection connection = null;
	private static final String productTableName = "product";

	public InventoryProductRepository() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
		} catch (ClassNotFoundException | SQLException e) {
			throw new InventorySystemRuntimeException("Exception while constructing ProductRepo", e);
		}

	}

	public boolean createNewInventoryProduct(InventoryProduct obj) {
		try (PreparedStatement ps = connection.prepareStatement("insert into "+productTableName+" values( null,?,?)")) {
			ps.setString(1, obj.getProductName());
			ps.setInt(2, obj.getQuantity());
			ps.execute();
			System.out.println("saved: " + obj);

			return true;
		} catch (SQLException e) {
			logger.error("SQL Exception: ", e);
			throw new InventorySystemRuntimeException(e);
		}
	}

	public List<InventoryProduct> getAll() {
		try (Statement st = connection.createStatement()) {
			List<InventoryProduct> list = new ArrayList<>();
			ResultSet rs = st.executeQuery("select * from "+productTableName);
			while (rs.next()) {
				list.add(new InventoryProduct(rs.getInt("productId"), rs.getString("productName"),
						rs.getInt("quantity")));
			}
			logger.info("Get All count: {}", list.size());
			return list;
		} catch (SQLException e) {
			logger.error("SQL Exception: ", e);
			throw new InventorySystemRuntimeException(e);
		}
	}

	public int updateExistingById(InventoryProduct obj) {
		try (PreparedStatement ps = connection
				.prepareStatement("UPDATE "+productTableName+" SET productName = ?, quantity= ? WHERE productId = ?;")) {
			ps.setInt(3, obj.getProductId());
			ps.setString(1, obj.getProductName());
			ps.setInt(2, obj.getQuantity());
			ps.execute();
			int ans = ps.getUpdateCount();
			logger.info("Update count: {}", ans);
			return ans;
		} catch (SQLException e) {
			logger.error("SQL Exception: ", e);
			throw new InventorySystemRuntimeException(e);
		}
	}

	public boolean deleteById(Integer productId) {
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM "+productTableName+" WHERE productId = ?;")) {
			ps.setInt(1, productId);
			ps.execute();
			int ans = ps.getUpdateCount();
			logger.info("Delete by Id: {} count: {}", productId, ans);
			if (ans == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			logger.error("SQL Exception: ", e);
			throw new InventorySystemRuntimeException(e);
		}
	}
}
