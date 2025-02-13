package com.xadmin.produitmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xadmin.produitmanagement.bean.Produit;

public class ProduitDao {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/product?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "admin";

	private static final String INSERT_PRODUCTS_SQL = "INSERT INTO product" + " (name, description, quantite) VALUES "
			+ " (?, ?, ?);";

	private static final String SELECT_PRODUCT_BY_ID = "select id,name,description,prix,quantite,categorie from product where id =?";
	private static final String SELECT_ALL_PRODUCTS  = "select * from product";
	private static final String DELETE_PRODUCTS_SQL  = "delete from product where id = ?;";
	private static final String UPDATE_PRODUCTS_SQL  = "update product set name = ?,email= ?, country =? where id = ?;";
	
	public ProduitDao() {
		
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	
}
