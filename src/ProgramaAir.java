
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;

import com.restfb.*;
import com.restfb.types.FacebookType;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.restfb.Version;
import com.restfb.DefaultFacebookClient;

public class ProgramaAir implements ActionListener{

	JLabel texto;
	DefaultCategoryDataset graphBanco;
	Distrito victoria;
	FileInputStream Chart;
	File Chart2;
	
	public ProgramaAir(Distrito victoria){
		
	//Cria interface botões e mensagens
	JFrame jfrm = new JFrame("Gerador de graficos com dados do bairro Victoria, BC, Canada");
	this.texto = new JLabel("");
	JLabel cabecalho = new JLabel("Escolha o tipo de gráfico a ser gerado.");
	JButton botaoGraphBarraPreco = new JButton("preco");
	JButton botaoGraphBarraDisponibilidade = new JButton("disponibilidade");
	JButton botaoGraphBarraEstabelecimentos = new JButton("estabelecimentos");
	JButton compartilharFace = new JButton("facebook");
	JButton compartilharTwitter = new JButton("twitter");
	graphBanco =null;
	Chart = null;
	Chart2 = null;
	this.victoria = victoria;
	
	//Configura interface e botões
	jfrm.setSize(300,400);
	jfrm.setLayout(new FlowLayout());
	jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	botaoGraphBarraPreco.addActionListener(this);
	botaoGraphBarraDisponibilidade.addActionListener(this);
	botaoGraphBarraEstabelecimentos.addActionListener(this);
	compartilharFace.addActionListener(this);
	compartilharTwitter.addActionListener(this);
	
	//Adiciona os botões e a mensagem a interface
	jfrm.add(cabecalho);
	jfrm.add(texto);
	jfrm.add(botaoGraphBarraPreco);
	jfrm.add(botaoGraphBarraDisponibilidade);
	jfrm.add(botaoGraphBarraEstabelecimentos);
	jfrm.add(compartilharFace);
	jfrm.add(compartilharTwitter);
	
	//Torna a interface Visivel
	jfrm.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("preco")) {
			this.graphBanco = gerarBancoBarraPreco(this.victoria);
			try {
				gerarGraphBarra3D(this.graphBanco,"Media de preços", "Preço CAD$");
				this.texto.setText("-- Gerado com sucesso --");
			}catch(Exception exc) {
				this.texto.setText("Erro" + exc);
			}
		}else if(ae.getActionCommand().equals("disponibilidade")) {
			this.graphBanco = gerarBancoBarraDispo(this.victoria);
			try {
				gerarGraphBarra3D(this.graphBanco,"Media da disponibilidade", "Disponibilidade");
				this.texto.setText("-- Gerado com sucesso --");
			}catch(Exception exc) {
				this.texto.setText("Erro" + exc);
			}
		}else if(ae.getActionCommand().equals("estabelecimentos")) {
			this.graphBanco = gerarBancoBarraEstab(this.victoria);
			try {
				gerarGraphBarra3D(this.graphBanco,"Quantidade de hospedagens","Quantidade");
				this.texto.setText("-- Gerado com sucesso --");
			}catch(Exception exc) {
				this.texto.setText("Erro" + exc);
			}
		}else if (ae.getActionCommand().equals("facebook")) {
			try {
				postFacebook();
				this.texto.setText("-- Postado com sucesso --");
			}catch(Exception exc) {
				this.texto.setText("Erro "+ exc);
			}
			
		}else {
			try {
				postTwitter();
				this.texto.setText("-- Postado com sucesso --");
			}catch(Exception exc) {
				this.texto.setText("Erro "+ exc);
			}
		}	
	}
	
	public static void main(String[] args) {
		String linha;
		String[] coluna;
		Distrito victoria = new Distrito("victoria");
		
		try(BufferedReader arquivo = new BufferedReader(new FileReader("./files/victoria.csv"))){
			
			linha = arquivo.readLine(); //Descarta a primeira linha
			
			//Carrega o Objeto Distrito com as informações
			do {
				linha = arquivo.readLine();
				if( linha != null) {
					coluna = linha.split(",");
					if(coluna.length == 16) {
					 	victoria.carregarDistrito(coluna);
					}
				}
			}while(linha!=null);
			
		}catch(Exception exc) {
			System.out.println("Error: " + exc);
		}

		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new ProgramaAir(victoria);
			}
		});
	}
	
	private DefaultCategoryDataset gerarBancoBarraEstab(Distrito victoria) {
		int infoI;
		String nomeBairro;
		DefaultCategoryDataset graphBarra = new DefaultCategoryDataset();
		
		for(int i = 0; i < victoria.getSizeBairro(); i++){
			infoI = victoria.getBairroOfIndex(i).getNumEstabelecimentos();
			nomeBairro = victoria.getBairroOfIndex(i).getNome();
			graphBarra.addValue(infoI,nomeBairro,"Estabelecimentos");
		}
		
		return graphBarra;
	}
	
	private DefaultCategoryDataset gerarBancoBarraDispo(Distrito victoria) {
		double infoI;
		String nomeBairro;
		DefaultCategoryDataset graphBarra = new DefaultCategoryDataset();
		
		for(int i = 0; i < victoria.getSizeBairro(); i++){
			nomeBairro = victoria.getBairroOfIndex(i).getNome();
			infoI = victoria.getBairroOfIndex(i).getMediaDisponibilidade();
			graphBarra.addValue(infoI,nomeBairro,"Média Disponibilidade");
		}
		
		return graphBarra;
	}
	
	private DefaultCategoryDataset gerarBancoBarraPreco(Distrito victoria) {
		double infoD;
		String nomeBairro;
		DefaultCategoryDataset graphBarra = new DefaultCategoryDataset();
		
		for(int i = 0; i < victoria.getSizeBairro(); i++){
			nomeBairro = victoria.getBairroOfIndex(i).getNome();
			infoD = victoria.getBairroOfIndex(i).getMediaPreco();
			graphBarra.addValue(infoD,nomeBairro,"Media Preços(CAD U$)");
		}
		
		return graphBarra;
	}
	
	private void gerarGraphBarra3D(DefaultCategoryDataset graphBarraEstab, String categoria, String medida) throws IOException{
		JFreeChart barChart = ChartFactory.createBarChart3D(
		         categoria + " por bairro, Victoria - BC, Canada",             
		         "Bairros",             
		         medida,             
		         graphBarraEstab,            
		         PlotOrientation.VERTICAL,             
		         true, true, false);
		         
		int width = 1280; /* Width of the image */              
		int height = 720; /* Height of the image */
		Chart2 = new File( "barChart3D.jpeg" );
		this.Chart = new FileInputStream(Chart2);
		ChartUtilities.saveChartAsJPEG(Chart2, barChart, width, height);
	}
	
	@SuppressWarnings("deprecation")
	private void postFacebook() throws Exception {
		String accessToken = "XXXXXXXXX";
		FacebookClient client = new DefaultFacebookClient(accessToken,Version.getVersionFromString("VERSION_2_10"));
		ArrayList<TagBody> tag = new ArrayList<TagBody>();
		tag.add(new TagBody("100001383083662","Plácido A. Souza Neto"));
		client.publish("me/photos", FacebookType.class, 
								BinaryAttachment.with("barChart3D.jpeg", Chart),
								Parameter.with("message","Desafio Complete de POO completo, criar um grafico e postar no facebook utilizando uma API"),
								Parameter.with("tags",tag));
	}
	
	private void postTwitter() throws Exception{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("XXXXXXXXXXXX");
		cb.setOAuthConsumerSecret("XXXXXXXXXXXXXXX");
		cb.setOAuthAccessToken("XXXXXXXXXXXXXXXXX");
		cb.setOAuthAccessTokenSecret("XXXXXXXXXXXXXXX");
		
		TwitterFactory inter = new TwitterFactory(cb.build());
		Twitter tt 	= inter.getInstance();
		StatusUpdate status = new StatusUpdate("Desafio Complete de POO, criar um grafico e postar no twitter utilizando uma API");
		status.setMedia(Chart2);
		tt.updateStatus(status);
	}
}
