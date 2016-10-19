import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Arquivo {
	private Formatter output;
	String source;	
	AnalisaFitaDetalhe fitaDetalhe;

	public Arquivo(String source) {
		this.source = source;
		fitaDetalhe = new AnalisaFitaDetalhe(source);
	}

	public void arquivoAbrirOuCriar() throws UnsupportedEncodingException {
		try {
			output = new Formatter("c:\\Temp_Fita_Detalhe\\relatorio.rtf",
					"ISO-8859-1"); // saída padrão

		} catch (SecurityException securityException) {
			System.err.println("Nao foi possivel abrir o arquivo.");
			System.exit(1);
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Erro ao abrir ou criar arquivo.");
			System.exit(1);
		}
	}

	public void arquivoGravar() throws IOException {
		output.format(fitaDetalhe.relatorioCompleto());
	}

	public void arquivoFechar() {
		if (output != null)
			output.close();
	}

}
