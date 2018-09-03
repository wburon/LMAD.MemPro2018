package Model;

import java.util.Date;

public class Rendez_Vous {
	
	private int id_rdv;
	private Intervention intervention;
	private Date dateDeb, dateFin;
	public int getId_rdv() {
		return id_rdv;
	}
	public void setId_rdv(int id_rdv) {
		this.id_rdv = id_rdv;
	}
	public Intervention getIntervention() {
		return intervention;
	}
	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}
	public Date getDateDeb() {
		return dateDeb;
	}
	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	public String toString() {
		String horaire = Methode.toStringHourOfDate(this.dateDeb) + " à " + Methode.toStringHourOfDate(this.dateFin);
		String clientName = this.intervention.getMateriel().getClient().getNom() + " " + this.intervention.getMateriel().getClient().getPrenom();
		String adresse = this.intervention.getMateriel().getClient().getAdresse() + " " + this.intervention.getMateriel().getClient().getVille();
		String tel = Integer.toString(this.intervention.getMateriel().getClient().getTel());
		return "<html>" + horaire + "<br>" + clientName + "<br>" + adresse + "<br>" + tel + "</html>";
	}
	
	public String fullToSting() {
		String horaire = Methode.toStringHourOfDate(this.dateDeb) + " à " + Methode.toStringHourOfDate(this.dateFin);
		String clientName = this.intervention.getMateriel().getClient().getNom() + " " + this.intervention.getMateriel().getClient().getPrenom();
		String adresse = this.intervention.getMateriel().getClient().getAdresse() + " " + this.intervention.getMateriel().getClient().getVille();
		String tel = Integer.toString(this.intervention.getMateriel().getClient().getTel());
		String commentaire = this.intervention.getCommentaire();
		String materiel = this.intervention.getMateriel().toString();
		
		return "<html>" + horaire + "<br>" + clientName + "<br>" + adresse + "<br>" + tel + "<br>" + materiel +"<br><br>" + commentaire + "</html>";
	}
	

}
