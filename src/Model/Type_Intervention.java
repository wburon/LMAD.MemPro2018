package Model;

public class Type_Intervention {

	private int id_type_intervention;
	private boolean imprevu;
	private boolean entretient;
	public int getId_type_intervention() {
		return id_type_intervention;
	}
	public void setId_type_intervention(int id_type_intervention) {
		this.id_type_intervention = id_type_intervention;
	}
	public boolean isImprevu() {
		return imprevu;
	}
	public void setImprevu(boolean imprevu) {
		this.imprevu = imprevu;
	}
	public boolean isEntretient() {
		return entretient;
	}
	public void setEntretient(boolean entretient) {
		this.entretient = entretient;
	}
	
}
