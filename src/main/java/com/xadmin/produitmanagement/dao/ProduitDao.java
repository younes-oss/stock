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
	
	private static String jdbcURL = "jdbc:mysql://localhost:3306/product?useSSL=false";
	private static String jdbcUsername = "root";
	private static String jdbcPassword = "admin";

	private static final String INSERT_PRODUCTS_SQL = "INSERT INTO product" + " (name, description, quantite,prix,categorie) VALUES "
			+ " (?, ?, ?, ?, ?);";

	private static final String SELECT_PRODUCT_BY_ID = "select id,name,description,prix,quantite,categorie from product where id =?";
	private static final String SELECT_ALL_PRODUCTS  = "select * from product";
	private static final String DELETE_PRODUCTS_SQL  = "delete from product where id = ?;";
	private static final String UPDATE_PRODUCTS_SQL  = "update product set name = ?,email= ?, country =? where id = ?;";
	
	public ProduitDao() {
		
	}

	protected static Connection getConnection() {
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
	
	public static void insertProduit(Produit prod) throws SQLException {
	    System.out.println(INSERT_PRODUCTS_SQL);
	    
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL)) {
	        
	       
	        preparedStatement.setString(1, prod.getNomProduit());
	        preparedStatement.setString(2, prod.getDescription());
	        preparedStatement.setDouble(3, prod.getPrix()); 
	        preparedStatement.setInt(4, prod.getQuantite()); 
	        preparedStatement.setString(5, prod.getCategorie());

	        System.out.println("Requête SQL exécutée : " + preparedStatement);
	        
	        
	        preparedStatement.executeUpdate();

	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage()); 
	        printSQLException(e); 
	    }
	}

	
	public static Produit selectProduit(int id) {
	    Produit produit = null;
	    
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
	        
	        preparedStatement.setInt(1, id);
	        System.out.println(preparedStatement);
	        
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        while (rs.next()) {
	            String name = rs.getString("name");
	            String description = rs.getString("description");
	            int quantite = rs.getInt("quantite");
	            double prix = rs.getDouble("prix");
	            String categorie = rs.getString("categorie");
	            
	            produit = new Produit(id, name, description, quantite, prix, categorie);
	        }
	        
	    } catch (SQLException e) {
	        printSQLException(e);
	    }
	    
	    return produit;
	}

	public static List<Produit> selectAllProduits() {
	    List<Produit> produits = new ArrayList<>();
	    
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
	        
	        System.out.println(preparedStatement);
	        
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            String description = rs.getString("description");
	            int quantite = rs.getInt("quantite");
	            double prix = rs.getDouble("prix");
	            String categorie = rs.getString("categorie");

	            produits.add(new Produit(id, name, description, quantite, prix, categorie));
	        }
	        
	    } catch (SQLException e) {
	        printSQLException(e);
	    }
	    
	    return produits;
	}
	
	public static boolean deleteProduit(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	
	public boolean updateProduit(Produit produit) throws SQLException {
	    boolean rowUpdated;
	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_SQL)) {
	        
	        System.out.println("Mise à jour du produit : " + statement);
	        
	        // Mise à jour des valeurs
	        statement.setString(1, produit.getNomProduit());
	        statement.setString(2, produit.getDescription());
	        statement.setInt(3, produit.getQuantite());
	        statement.setDouble(4, produit.getPrix()); 
	        statement.setInt(5, produit.getId()); 

	        // Exécution de la mise à jour
	        rowUpdated = statement.executeUpdate() > 0;
	    }
	    return rowUpdated;
	}

	

	private static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
	
}
