package Model;

import java.util.Date;

public class Intervention {
	
	private int id_intervention;
	private Materiel materiel;
	private String type_intervention;
	private int numFacture;
	private int numBL;
	private int refPiece;
	private String commentaire;
	private Date date;
	
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId_intervention() {
		return id_intervention;
	}
	public void setId_intervention(int id_intervention) {
		this.id_intervention = id_intervention;
	}
	public Materiel getMateriel() {
		return materiel;
	}
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	public String getType_intervention() {
		return type_intervention;
	}
	public void setType_intervention(String type_intervention) {
		this.type_intervention = type_intervention;
	}
	public int getNumFacture() {
		return numFacture;
	}
	public void setNumFacture(int numFacture) {
		this.numFacture = numFacture;
	}
	public int getNumBL() {
		return numBL;
	}
	public void setNumBL(int numBL) {
		this.numBL = numBL;
	}
	public int getRefPiece() {
		return refPiece;
	}
	public void setRefPiece(int refPiece) {
		this.refPiece = refPiece;
	}

}
