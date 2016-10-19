// Figura 4.18 DrawPanel.java
// Utilizando DrawLine para conectar os cantos de um painel
import java.awt.Graphics;
import java.text.AttributedCharacterIterator;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	private Calendar cal = Calendar.getInstance();
	private int cont;

	public void setCont(int cont) {
		this.cont = cont;
	}

	private String mensagem1, mensagem2, mensagem3, mensagem4;

	// desenha um X a partir dos cantos do painel
	public void paintComponent(Graphics g) {
		// chama paintComponent para assegurar que o painel seja exibido
		// corretament
		super.paintComponent(g);

		g.drawString(mensagem1, 30, 30);
		g.drawString(mensagem2, 30, 70);
		g.drawString(mensagem3 + mensagem4, 30, 110);

	} // fim do m√©todo paintComponent

	public void setMensagem4(String mensagem, JFrame frame) {

		// chama paintComponent para assegurar que o painel seja exibido
		// corretament
		this.mensagem4 = mensagem;
		frame.repaint();

	}

	public void setMensagem3(String mensagem) {

		// chama paintComponent para assegurar que o painel seja exibido
		// corretament
		this.mensagem3 = mensagem;

	}

	public void setMensagem2(String mensagem) {
		// chama paintComponent para assegurar que o painel seja exibido
		// corretament
		this.mensagem2 = mensagem;
	}

	public void setMensagem1(String mensagem) {

		// chama paintComponent para assegurar que o painel seja exibido
		// corretament
		this.mensagem1 = mensagem;

	}
	
	public void msgAnalisaEspelho(DrawPanel panel, JFrame frame) {
		String m1 = "AGUARDAR - ARQUIVO PREPARADO ";
		String m2 = "PERCORRENDO FITA-DETALHE";
		String m3 = "COO VERIFICADO: ";

		defineMensagem(frame, panel, m1, m2, m3, "");
	}

	public void msgCopiaArquivo(DrawPanel panel, JFrame frame) {
		String m1 = "AGUARDAR - VERIFICANDO PERSISTENCIA";
		String m2 = "ESTE PROCESSO COSTUMA SER RAPIDO";
		String m3 = "PREPARANDO ARQUIVO PARA AN¡LISE";

		defineMensagem(frame, panel, m1, m2, m3, "");
	}

	public void defineMensagem(JFrame frame, DrawPanel panel, String m1,
			String m2, String m3, String m4) {
		// DRAW
		panel.setMensagem1(m1);
		panel.setMensagem2(m2);
		panel.setMensagem3(m3);
		panel.setMensagem4(m4, frame);

	}
	

	public void configuraJanelaParaExibicao(DrawPanel panel, JFrame frame) {
		// configura o frame para ser encerrado quando ele
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(panel);
		frame.setTitle("An·lise de cupom");
		// adiciona o painel ao frame
		frame.setSize(320, 180); // configura o tamanho do frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		// fim DRAW
	}

} // fim da classe DrawPanel