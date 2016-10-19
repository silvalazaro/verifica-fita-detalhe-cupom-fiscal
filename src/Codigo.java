import java.util.ArrayList;


public class Codigo {
	String hash;	
	ArrayList<Integer> coos = new ArrayList<Integer>();
	
	public Codigo(String hash, int id){
		this.hash = hash;
		this.id = id;
	}

	public Codigo(){
		
	}
	public ArrayList<Integer> getCoos() {
		return coos;
	}

	public void setCoos(ArrayList<Integer> coos) {
		this.coos = coos;
	}

	private int id;	

	
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	} 
	
	
}
