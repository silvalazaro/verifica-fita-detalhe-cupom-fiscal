import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;

public class AnalisaFitaDetalhe {
	ArrayList<String> datahash = new ArrayList<String>();
	String file;	
	ArrayList<String> hashes = new ArrayList<String>();
	
	public AnalisaFitaDetalhe(String source) {
		this.file = source;
	}

	public BufferedReader getFitaDetalhe() {
		try {
			System.out.println("criando buffered reader");			
			return new BufferedReader(new FileReader(new File(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String relatorioCompleto() {
		String relatorio = "";
		Empresa emp = null;
		ArrayList<CodigoPeriodo> cp = null;
		
		try {
			emp = dadosEmpresa();
			System.out.println("dados da empresa gerados");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			cp = RelatorioObjeto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (emp != null) {
			for (int i = emp.getRazaoSocial().size() - 1; i >= 0; i--) {
				relatorio += String.format("%s" + System.getProperty("line.separator"),
						emp.getRazaoSocial().get(i));
			}
			
			relatorio += String.format("%s" + System.getProperty("line.separator"), emp.getCnpj());
			relatorio += String.format("%s" + System.getProperty("line.separator"),
					emp.getInscricaoEstadual());
			relatorio += String.format("%s" + System.getProperty("line.separator"), emp.getFab());
			relatorio += String.format("%s" + System.getProperty("line.separator"), emp.getEcf());

		}

		relatorio += String.format(System.getProperty("line.separator"));

		// relatorio
		if (cp.size() > 2) {
			for (int i = 2; i < cp.size(); i++) {
				relatorio += String.format(
						"%3d) MD5: %s" + System.getProperty("line.separator")
								+ "     COO N� %s"
								+ System.getProperty("line.separator"), i - 1,
						cp.get(i).getHash(), cp.get(i).getPeriodo());
				relatorio += String.format(System.getProperty("line.separator"));
			}
			relatorio += String.format(System.getProperty("line.separator"));
			relatorio += String.format("Obs: Nao foi encontrado MD5 nos cupons fiscais:"
					+ System.getProperty("line.separator") + "     COO N� %s ",
					cp.get(1).getPeriodo());
			relatorio += String.format(System.getProperty("line.separator"));
			relatorio += String.format(System.getProperty("line.separator"));
		}
		
		return relatorio;
	}

	public ArrayList<String> removeCOOCancelados(ArrayList<String> lista) {
		ArrayList<String> novaLista = new ArrayList<String>();
		
		// exclui cancelados
		for (int i = 0; i < lista.size(); i++) {
			if (!lista.get(i).contains("cancelado")) {
				novaLista.add(lista.get(i));				
			}
		}

		return novaLista;
	}

	public ArrayList<String> relatorioHashDatas(ArrayList<String> lista) {
		ArrayList<String> datas = new ArrayList<String>();

		for (String string : lista) {
			datas.add(string.substring(string.length() - 10));
		}
		return datas;
	}
	public ArrayList<String> relatorioHashCodigo(ArrayList<String> lista) {
		ArrayList<String> hashes= new ArrayList<String>();

		for (String string : lista) {						
			hashes.add(string.substring(0, string.length() - 13));
		}
		return hashes;
	}
	
	public ArrayList<CodigoPeriodo> RelatorioObjeto() throws IOException {
		ArrayList<String> hashesTotal = listaDeTodosHash();
		ArrayList<String> listaSemCancelados = removeCOOCancelados(hashesTotal);
		ArrayList<String> datas, lista, hashes;
		ArrayList<CodigoPeriodo> cp = new ArrayList<CodigoPeriodo>();
		
		// ordena coos
		Collections.sort(listaSemCancelados);
		datas = relatorioHashDatas(listaSemCancelados);
		lista = relatorioHashCodigo(listaSemCancelados);

		if (lista.size() > 1) {
			String uMd = lista.get(0).substring(lista.get(0).indexOf(" "))
					.trim();
			boolean p = false; // periodo desativado
			String pI = onlyNumbers(lista.get(0).substring(0,
					lista.get(0).indexOf(" "))); // periodo inicial
			String pF = onlyNumbers(lista.get(0).substring(0,
					lista.get(0).indexOf(" "))); // periodo final
			String per = null; // per
			int id = 2; // 0 - cancelado; 1 - sem md5
			boolean todosIguais = true;
			String ultMd = lista.get(0).substring(lista.get(0).indexOf(" "))
					.trim();
			cp.add(new CodigoPeriodo("Cancelado"));
			cp.add(new CodigoPeriodo("semhash"));
			int indc = lista.size() - 2;
			boolean periodo = false;
			for (int i = 0; i < lista.size() - 1; i++) {
				int iprox = 1 + i;
				String md = lista.get(i).substring(lista.get(i).indexOf(" "))
						.trim();				
				String mdProx = lista.get(iprox)
						.substring(lista.get(i).indexOf(" ")).trim();
				// incrementa cp
				if ((cp.size() - 1) < id) {
					cp.add(new CodigoPeriodo(uMd + " - " + datas.get(i)));
				}

				// proximo md5
				if (!md.equals(mdProx)) {
					pF = onlyNumbers(lista.get(i).substring(0,
							lista.get(i).indexOf(" ")));
					periodo = true;
				}

				if (i == indc) {
					if (ultMd.equalsIgnoreCase(mdProx)) {
						pF = onlyNumbers(lista.get(iprox).substring(0,
								lista.get(iprox).indexOf(" ")));
						periodo = true;
					} else {
						pF = onlyNumbers(lista.get(iprox).substring(0,
								lista.get(iprox).indexOf(" ")));
						if (mdProx.equalsIgnoreCase("cancelado")) {
							if (md.equalsIgnoreCase("cancelado"))
								cp.get(0).setPeriodo(pI, pF);
							else {
								pI = pF = onlyNumbers(lista.get(iprox)
										.substring(0,
												lista.get(iprox).indexOf(" ")));
								cp.get(0).setPeriodo(pI, pF);
							}
						} else if (mdProx.equalsIgnoreCase("semhash")) {
							if (md.equalsIgnoreCase("semhash"))
								cp.get(0).setPeriodo(pI, pF);
							else {
								pI = pF = onlyNumbers(lista.get(iprox)
										.substring(0,
												lista.get(iprox).indexOf(" ")));
								cp.get(0).setPeriodo(pI, pF);
							}

						} else {
							cp.add(new CodigoPeriodo(mdProx + " - "
									+ datas.get(i)));
							id++;
							cp.get(id).setPeriodo(pI, pF);
						}

					}

				}
				// fim perido
				if (periodo || i == indc) {
					if (mdProx.equalsIgnoreCase("cancelado")) {
						if (md.equalsIgnoreCase("semhash"))
							cp.get(1).setPeriodo(pI, pF);
						else {
							if (ultMd.equalsIgnoreCase(md))
								cp.get(id).setPeriodo(pI, pF);
							else {
								cp.add(new CodigoPeriodo(mdProx));
								id++;
								ultMd = cp.get(id).getHash();
							}

						}
					} else if (mdProx.equalsIgnoreCase("semhash")) {
						if (md.equalsIgnoreCase("cancelado"))
							cp.get(0).setPeriodo(pI, pF);
						else {
							if (ultMd.equalsIgnoreCase(md))
								cp.get(id).setPeriodo(pI, pF);
							else {
								cp.add(new CodigoPeriodo(mdProx));
								id++;
								ultMd = cp.get(id).getHash();
							}
						}
					} else if (md.equalsIgnoreCase("cancelado")) {

						cp.get(0).setPeriodo(pI, pF);
						if (!ultMd.contains(mdProx)) {
							cp.add(new CodigoPeriodo(mdProx));
							id++;
							ultMd = cp.get(id).getHash();
						}

					} else if (md.equalsIgnoreCase("semhash")) {

						cp.get(1).setPeriodo(pI, pF);
						if (!ultMd.contains(mdProx)) {
							cp.add(new CodigoPeriodo(mdProx));
							id++;
							ultMd = cp.get(id).getHash();
						}
					} else {

						if (md.equalsIgnoreCase(mdProx)) {
							cp.get(id).setPeriodo(pI, pF);
						} else {
							cp.get(id).setPeriodo(pI, pF);
							cp.add(new CodigoPeriodo(mdProx));
							id++;
							ultMd = cp.get(id).getHash();
						}

					}
					pI = pF = onlyNumbers(lista.get(iprox).substring(0,
							lista.get(iprox).indexOf(" ")));
					periodo = false;
				}
			}
		}
		return cp;

	}

	public Empresa cabecalhoDadosEmpresa(ArrayList<String> parteDoEspelho) {
		Empresa empresa = new Empresa();
		ArrayList<String> razaoSocial = new ArrayList<String>();

		boolean ecf = true, fab = true, cnpj = true, ie = true;
		int iee = 0;

		// PERCORRE PARTE ESPELHO
		for (int i = parteDoEspelho.size() - 1; i >= 0; i--) {
			String string = parteDoEspelho.get(i);
			// versao ecf
			if (ecf) {
				if (string
						.matches("(.)*[Vv][eE][rR][sS](.)+[oO](.)+[eE][cC][fF](.)*")) {
					empresa.setEcf(string);
					ecf = false;
				}
			}

			// fab
			if (fab) {
				if (string.matches("(.)*[fF][aA][bB](.)*")) {
					empresa.setFab(string);
					fab = false;
				}
			}

			if (cnpj) {
				if (string.matches("(.)*[cC][nN][pP][jJ](.)*[0123456789]+")) {
					empresa.setCnpj(string);
					cnpj = false;
				}
			}

			// ie
			if (ie) {
				if (string.matches("(.)*[iI][eE](.)*[0123456789]+")) {
					empresa.setInscricaoEstadual(string);
					ie = false;
					iee = i;
				}
			}
		}

		// razao social
		int s = 1;
		while (s < 6 & iee < parteDoEspelho.size()) {
			iee -= 1;
			if (!parteDoEspelho.get(iee).matches("(.)*[cC][nN][pP][jJ](.)+"))
				razaoSocial.add(parteDoEspelho.get(iee));
			s++;
		}

		empresa.setRazaoSocial(razaoSocial);

		return empresa;
	}

	public ArrayList<String> parteDoEspelhoDadosDaEmpresa() {
		BufferedReader file = getFitaDetalhe();
		String linha;
		ArrayList<String> parteDoEspelho = new ArrayList<String>();
		boolean dadosECF = false;
		boolean coo = true;

		try {
			while ((linha = file.readLine()) != null & coo == true) {

				if (dadosECF == false)
					if (linha
							.matches("(.)*[Vv][eE][rR][sS](.)+[oO](.)+[eE][cC][fF](.)+")) {
						dadosECF = true;
					}
				if (dadosECF) {
					parteDoEspelho.add(linha);
				}

				if (linha
						.matches("[\\s]*[0-3][0-9][-/][01][0-9][-/][0-9]+(.)+[cC][oO][oO](.)*[0-9]+")
						& dadosECF == true) {
					coo = false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// fechar arquivo
		if (file!= null)
			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return parteDoEspelho;
	}

	public Empresa dadosEmpresa() throws IOException {
		return cabecalhoDadosEmpresa(parteDoEspelhoDadosDaEmpresa());
	}

	private String getDataRelatorio(String line) {		
		return line.substring(0, 10);
	}

	public ArrayList<String> listaDeTodosHash() throws IOException {
		// DRAW
		DrawPanel panel = new DrawPanel();
		// cria um novo quadro para armazenar o painel
		JFrame frame = new JFrame();		
		panel.configuraJanelaParaExibicao(panel, frame);
		panel.msgAnalisaEspelho(panel, frame);
		
		BufferedReader file = getFitaDetalhe();
		boolean cupomEncontrado = false;
		boolean cupomCancelado = false;
		int cooAdicionado = 0;
		int contaHash = -1;
		String coo;
		String cooCupom = null;
		String periodoHash;
		String ultimoMd5 = "";
		String linha = new String();
		String data = null;

		// ler linha
		while ((linha = file.readLine()) != null) {
			if (linha
					.matches("[\\s]*[0-3][0-9][-/][01][0-9][-/][0-9]+(.)+[cC][oO][oO](.)*[0-9]+")) {
				coo = searchCOO(linha); // exibir na GUI
				data = " @ " + getDataRelatorio(linha);
				panel.setMensagem4(String.format("%s", coo), frame);
				cupomEncontrado = false;
				if (linha.matches("(.)*[cC][cC][fF](.)*")) {
					if (cooAdicionado == 2) {
						if (cupomCancelado) {
							adicionarHash(cooCupom, "cancelado" + data);
							cupomCancelado = false;
						} else
							adicionarHash(cooCupom, "semhash" + data);
					}

					cupomEncontrado = true;
					cooCupom = searchCOO(linha);
					cooAdicionado = 2;
				}
			}

			// cupom encontrado
			if (cupomEncontrado) {
				StringTokenizer nova = new StringTokenizer(linha);
				while (nova.hasMoreElements()) {
					String md5 = nova.nextToken();
					if (md5.contains("cancelado")
							|| md5.contains("cancelamento"))
						cupomCancelado = true;
					// encontrou MD5
					if (md5.length() == 32 & md5.matches("[a-zA-Z_0-9]*")) {
						cooAdicionado = 1;
						adicionarHash(cooCupom, md5 + data);

					}

				}
			}

		}

		if (cooAdicionado == 2) {
			if (cupomCancelado) {
				adicionarHash(cooCupom, "cancelado" + data);
				cupomCancelado = false;
			} else
				adicionarHash(cooCupom, "semhash" + data);
		}
		
		if (file!= null)
			file.close();
		frame.setVisible(false);		
		return hashes;
	}

	private void adicionarHash(String coo, String hash) {
		this.hashes.add(coo + " " + hash);
	}

	public String searchCOO(String line) {
		line = line.toUpperCase();
		String coo = null;
		if (line != null) {
			coo = line.substring(line.indexOf("COO"));
			coo = onlyNumbers(coo);
		}
		return coo;
	}

	public String onlyNumbers(String str) {
		if (str != null) {
			return str.replaceAll("[^0123456789]", "");
		} else {
			return "";
		}
	}

}
