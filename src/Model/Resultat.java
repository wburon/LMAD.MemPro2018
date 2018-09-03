package Model;

public class Resultat {

	private int id;
	private double note;
	
	public Resultat(int id, double note){
		this.id=id;
		this.note=note;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}
	
}
