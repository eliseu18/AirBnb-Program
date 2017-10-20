
import java.util.ArrayList;

public class Bairro {
	private String nome;
	private Distrito distrito;
	private ArrayList<Anfitriao> anfitrioes;
	private ArrayList<Estabelecimento> estabelecimentos;

	public Bairro(String nome ,Distrito distrito){
		this.nome = nome;
		this.distrito = distrito;
		this.anfitrioes = new ArrayList<Anfitriao>();
		this.estabelecimentos = new ArrayList<Estabelecimento>();
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public Distrito getDistrito() {
		return this.distrito;
	}
	
	public Anfitriao getAnfitriaoNome(String nome) {
		for(int i = 0; i < anfitrioes.size(); i++) {
			if (anfitrioes.get(i).getNome().equals(nome))
				return anfitrioes.get(i);
		}
		
		return null;
	}
	
	public Anfitriao getAnfitriaoID(int id){
		for(int i = 0; i < this.anfitrioes.size(); i++) {
			if(this.anfitrioes.get(i).getID() == id)
				return this.anfitrioes.get(i);
		}
		return null;
	}
	
	public Anfitriao getAnfitriaoOfIndex(int index) {
		if(0 <= index && index < anfitrioes.size())
			return anfitrioes.get(index);
		return null;
	}
	
	public Estabelecimento getEstabelecimentoNome(String nome) {
		for(int i = 0; i < estabelecimentos.size(); i++) {
			if(estabelecimentos.get(i).getNome().equals(nome))
				return estabelecimentos.get(i);
		}
		return null;
	}
	
	public Estabelecimento getEstabelecimentoID(int id) {
		for(int i = 0; i < estabelecimentos.size(); i++) {
			if(estabelecimentos.get(i).getID() == id)
				return estabelecimentos.get(i);
		}
		return null;
	}
	
	public Estabelecimento getEstabelecimentoOfIndex(int index) {
		if( 0 <= index && index <= estabelecimentos.size())
			return estabelecimentos.get(index);
		return null;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
	public void setAnfitriao(Anfitriao anfitriao) {
		this.anfitrioes.add(anfitriao);
	}
	
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimentos.add(estabelecimento);
	}
	
	public void carregarBairro(String[] coluna) {
		Anfitriao atualAnf = this.getAnfitriaoID(Integer.parseInt(coluna[2]));
		Estabelecimento atualEstab = this.getEstabelecimentoID(Integer.parseInt(coluna[0]));
		
		if (atualAnf == null) {
			atualAnf = new Anfitriao(Integer.parseInt(coluna[2]), coluna[3]);
			
			atualEstab = new Estabelecimento(Integer.parseInt(coluna[0]), coluna[1]);
			atualEstab.carregarEstabelecimento(coluna, this, atualAnf);
			
			atualAnf.carregarAnfitriao(this, atualEstab);
			this.setAnfitriao(atualAnf);
			this.setEstabelecimento(atualEstab);
		}else {
			if (atualEstab == null) {
				atualEstab = new Estabelecimento(Integer.parseInt(coluna[0]), coluna[1]);
				atualEstab.carregarEstabelecimento(coluna, this, atualAnf);
				this.setEstabelecimento(atualEstab);
				atualAnf.carregarAnfitriao(this, atualEstab);
			}
		}
	}
	
	public int getNumEstabelecimentos() {
		return estabelecimentos.size();
	}
	
	public int getMediaDisponibilidade() {
		int media = 0;
		for(int i = 0; i < this.estabelecimentos.size(); i++) {
			media += this.estabelecimentos.get(i).getDisponibilidade();
		}
		
		return (media/estabelecimentos.size());
	}
	
	public double getMediaPreco() {
		double media = 0;
		for(int i = 0; i < this.estabelecimentos.size(); i++) {
			media+=this.estabelecimentos.get(i).getPreco();
		}
		
		return media/this.estabelecimentos.size();
	}
	public Estabelecimento getMenorPreco() {
		Estabelecimento menorPreco = estabelecimentos.get(0);
		for(int i = 0; i < estabelecimentos.size();i++) {
			if(menorPreco.getPreco() > estabelecimentos.get(i).getPreco())
				menorPreco = estabelecimentos.get(i);
		}
		return menorPreco;
	}
}
