import java.awt.Button;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainTeste {	
	private static Cursor container;	

	public static void main(String[] args) throws IOException {
	
		boolean execucaoPrograma = true;

		while (execucaoPrograma) {
			
			String diretoriofile = new String();
			String padraoMFD = new String();

			// solicita arquivo
			diretoriofile = carregarArquivo();

			if (diretoriofile != null) {
				removerDiretorio(new File("c:\\Temp_Fita_Detalhe\\"));
				if (!diretoriofile.equals("invalido")) {
					
					Arquivo novo = new Arquivo(diretoriofile);					
					processarArquivo(diretoriofile);
					
				} else {
					JOptionPane
							.showMessageDialog(null,
									"VOCE DEVE SELECIONAR UM ARQUIVO\nDE TEXTO \".txt\"");
				}
			} else {
				execucaoPrograma = false;
			}			
			

		}

		removerDiretorio(new File("c:\\Temp_Fita_Detalhe\\"));
		// finaliza programa
		System.exit(0);

	}

	public static void processarArquivo(String diretoriofile) throws IOException {

		long inicio = 0, fim = 0;
		// cria pasta temp
		criarPastaTemp(); // faz backup para temp
		try {
			copyFile(diretoriofile, "c:\\Temp_Fita_Detalhe\\fitadetalhe.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		Arquivo novo = new Arquivo("c:\\Temp_Fita_Detalhe\\fitadetalhe.txt"); 
		novo.arquivoAbrirOuCriar(); // abre saída.txt
		novo.arquivoGravar();
		novo.arquivoFechar();		
			

		// abrir arquivo programa padrão
		try {
			java.awt.Desktop.getDesktop().open(
					new File("c:\\Temp_Fita_Detalhe\\relatorio.rtf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	public static void removerDiretorio(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; ++i) {
				removerDiretorio(files[i]);
			}
		}
		f.delete();
	}

	public static String carregarArquivo() {
		String file = null;
		JFileChooser abrir = new JFileChooser();
		int retorno = abrir.showOpenDialog(null);

		if (retorno == JFileChooser.APPROVE_OPTION) {
			file = abrir.getSelectedFile().getAbsolutePath();

			if (file != null)
				if (file.length() > 4) {
					if (file.substring(file.length() - 4).equalsIgnoreCase(
							".txt"))
						return file;
					return "invalido";
				}
		}

		return null;
	}

	public static void copyFile(String ssource, String sdestination)
			throws IOException {
		DrawPanel panel = new DrawPanel();
		JFrame frame = new JFrame();		
		panel.configuraJanelaParaExibicao(panel, frame);
		panel.msgCopiaArquivo(panel, frame);
		frame.setVisible(true);
		
		File source = new File(ssource);
		File destination = new File(sdestination);
		

		if (destination.exists())
			destination.delete();
		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;
		
		try {

			sourceChannel = new FileInputStream(source).getChannel();
			destinationChannel = new FileOutputStream(destination).getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(),
					destinationChannel);
		} finally {
			if (sourceChannel != null && sourceChannel.isOpen())
				sourceChannel.close();
			if (destinationChannel != null && destinationChannel.isOpen())
				destinationChannel.close();
			frame.setVisible(false);
		}
		frame.setVisible(false);
	}

	public static void criarPastaTemp() {
		try {
			File diretorio = new File("C:\\Temp_Fita_Detalhe");
			diretorio.mkdir();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro ao criar o diretorio");
			System.out.println(ex);
		}
	}
}
