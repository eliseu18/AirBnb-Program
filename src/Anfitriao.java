
import java.util.ArrayList;

public class Anfitriao {
	private int id;
	private String nome;
	private ArrayList<Bairro> bairros;
	private ArrayList<Estabelecimento> estabelecimentos;
	
	public Anfitriao(int id, String nome){
		this.id = id;
		this.nome = nome;
		this.bairros = new ArrayList<Bairro>(1);
		this.estabelecimentos = new ArrayList<Estabelecimento>(2);
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public Bairro getBairroNome(String nome){
		for (int i = 0; i < bairros.size(); i++) {
			if(bairros.get(i).getNome() == nome)
				return bairros.get(i);
		}
		
		return null;
	}
	
	public Bairro getBairroOfIndex(int index) {
		if( 0 <= index && index < bairros.size())
			return bairros.get(index);
		return null;
	}
	
	public Estabelecimento getEstabelecimentoNome(String nome) {
		for (int i = 0; i < estabelecimentos.size(); i++) {
			if(estabelecimentos.get(i).getNome() == nome)
				return estabelecimentos.get(i);
		}
		
		return null;
	}
	
	public Estabelecimento getEstabelecimentoID(int id) {
		for (int i = 0; i < estabelecimentos.size(); i++) {
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
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setBairro(Bairro bairro) {
		bairros.add(bairro);
	}
	
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentos.add(estabelecimento);
	}
	
	public void carregarAnfitriao(Bairro bairro, Estabelecimento estabelecimento ) {
		Bairro atualBairro = this.getBairroNome(bairro.getNome());
		Estabelecimento atualEstab = this.getEstabelecimentoID(estabelecimento.getID());
		if(atualBairro == null) {
			this.setBairro(bairro);
		}
		if(atualEstab == null) {
			this.setEstabelecimento(estabelecimento);
		}
	}
}
