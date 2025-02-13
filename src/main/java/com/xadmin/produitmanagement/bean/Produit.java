package com.xadmin.produitmanagement.bean;

public class Produit {
	
	private int id;
	private String nomProduit;
	private String description;
	private int quantite ;
	private double prix ;
	private String categorie;
	
	
	public Produit(int id, String nomProduit, String description, int quantite, double prix, String categorie) {
		super();
		this.id = id;
		this.nomProduit = nomProduit;
		this.description = description;
		this.quantite = quantite;
		this.prix =  prix;
		this.categorie = categorie;
	}
	
	
	public Produit(String nomProduit, String description, int quantite, int prix, String categorie) {
		super();
		this.nomProduit = nomProduit;
		this.description = description;
		this.quantite = quantite;
		this.prix = prix;
		this.categorie = categorie;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomProduit() {
		return nomProduit;
	}
	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	
	
}
