package com.xadmin.produitmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.produitmanagement.bean.Produit;
import com.xadmin.produitmanagement.dao.ProduitDao;

/**
 * Servlet implementation class ProduitServlet
 */
@WebServlet("/")
public class ProduitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProduitDao produitDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
   
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		produitDao = new ProduitDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertProduit(request, response);
				break;
			case "/delete":
				deleteProduit(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateProduit(request, response);
				break;
			default:
				listProduit(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	private void listProduit(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Produit> listProduit = ProduitDao.selectAllProduits();
		request.setAttribute("listProduit", listProduit);
		RequestDispatcher dispatcher = request.getRequestDispatcher("produit-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("produit-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Produit existingUser = ProduitDao.selectProduit(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("produit-form.jsp");
		request.setAttribute("produit", existingUser);
		dispatcher.forward(request, response);

	}
	
	private void insertProduit(HttpServletRequest request, HttpServletResponse response) 
	        throws SQLException, IOException {
	    
	    // Récupération des paramètres depuis la requête HTTP
	    String name = request.getParameter("name");
	    String description = request.getParameter("description");
	    int quantite = Integer.parseInt(request.getParameter("quantite")); // Conversion en int
	    double prix = Double.parseDouble(request.getParameter("prix")); // Conversion en double
	    String categorie = request.getParameter("categorie");

	    // Création d'un nouvel objet Produit avec tous les paramètres
	    Produit newProduit = new Produit(name, description, quantite, prix, categorie);
	    
	    // Insertion du produit dans la base de données
	    ProduitDao.insertProduit(newProduit);
	    
	    // Redirection après l'insertion
	    response.sendRedirect("list");
	}

	private void deleteProduit(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ProduitDao.deleteProduit(id);
		response.sendRedirect("list");

	}
	
	private void updateProduit(HttpServletRequest request, HttpServletResponse response) 
	        throws SQLException, IOException {
	    
	    int id = Integer.parseInt(request.getParameter("id"));
	    String name = request.getParameter("name");
	    String description = request.getParameter("description");
	    
	    // Conversion des paramètres en entiers et en double
	    int quantite = Integer.parseInt(request.getParameter("quantite"));
	    double prix = Double.parseDouble(request.getParameter("prix"));
	    
	    String categorie = request.getParameter("categorie");

	    // Création de l'objet Produit avec les bons types
	    Produit produit = new Produit(id, name, description, quantite, prix, categorie);
	    
	    // Mise à jour du produit
	    produitDao.updateProduit(produit);
	    
	    // Redirection vers la liste des produits
	    response.sendRedirect("list");
	}

	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
