package Model;

public class ResBrut {

	private int id;
	private String champs;
	private int note;
	
	public ResBrut(int id,String champs, int note){
		this.id=id;
		this.champs=champs;
		this.note=note;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChamps() {
		return champs;
	}

	public void setChamps(String champs) {
		this.champs = champs;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}
	
}
