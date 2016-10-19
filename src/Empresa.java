import java.util.ArrayList;


public class Empresa {
	
	private String inscricaoEstadual;
	private String cnpj;
	private String ecf;
	private String fab;	
	private ArrayList<String> razaoSocial;
	
	
	public ArrayList<String> getRazaoSocial() {
		return razaoSocial;
	}
	
	public void setRazaoSocial(ArrayList<String> razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	
	
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEcf() {
		return ecf;
	}
	
	public void setEcf(String ecf) {
		this.ecf = ecf;
	}
	
	public String getFab() {
		return fab;
	}
	
	public void setFab(String fab) {
		this.fab = fab;
	}
	
	
}
