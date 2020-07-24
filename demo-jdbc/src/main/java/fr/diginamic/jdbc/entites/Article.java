package fr.diginamic.jdbc.entites;


public class Article {
	private Integer id;
	private String ref;
	private String designation;
	private double prix;
	private Integer id_fou;
	
	

	public Article(Integer id, String ref, String designation, double prix, Integer id_fou) {
		super();
		this.id = id;
		this.ref = ref;
		this.designation = designation;
		this.prix = prix;
		this.id_fou = id_fou;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getRef() {
		return ref;
	}



	public void setRef(String ref) {
		this.ref = ref;
	}



	public String getDesignation() {
		return designation;
	}



	public void setDesignation(String designation) {
		this.designation = designation;
	}



	public double getPrix() {
		return prix;
	}



	public void setPrix(double prix) {
		this.prix = prix;
	}



	public Integer getId_fou() {
		return id_fou;
	}



	public void setId_fou(Integer id_fou) {
		this.id_fou = id_fou;
	}



	@Override
	public String toString() {
		return this.id + this.ref + this.designation + 
				this.prix + this.id_fou;
	}





}
