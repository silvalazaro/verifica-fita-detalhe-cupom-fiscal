
public class CodigoPeriodo {
	private String periodo = "(";
	private String hash;
	
	public String getPeriodo() {
		if(!this.periodo.equals("("))
			return this.periodo = periodo.substring(0, periodo.length() - 2) + ")";
		return "";
	}

	public void setPeriodo(String pI, String pF) {
		String p = null;
		if(pI.equals(pF))
			p = pI;
		else
			p = pI + "-" + pF;		
		this.periodo += p +", ";
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public CodigoPeriodo(String hash){
		this.hash = hash;
	}
	
}
