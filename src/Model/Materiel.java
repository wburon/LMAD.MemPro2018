package Model;

public class Materiel {
	
	private int id_materiel;
	private String nom;
	private String type;
	private String numSerie;
	public int getId_materiel() {
		return id_materiel;
	}
	public void setId_materiel(int id_materiel) {
		this.id_materiel = id_materiel;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

}
