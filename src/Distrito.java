
import java.util.ArrayList;

public class Distrito {
	private String nome;
	private ArrayList<Bairro> bairros;
	
	public Distrito(String nome) {
		this.nome = nome;
		this.bairros = new ArrayList<Bairro>();
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public Bairro getBairroNome(String nome){
		
		for(int i = 0;i < bairros.size();i++) {
			if (this.bairros.get(i).getNome().equals(nome))
				return this.bairros.get(i);
		}
		
		return null;
	}
	
	public Bairro getBairroOfIndex(int index) {
		if(0 <= index && index < bairros.size())
			return bairros.get(index);
		return null;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setBairro(Bairro bairro) {
		this.bairros.add(bairro);
	}
	
	public void carregarDistrito(String[] coluna) {
		Bairro atual = getBairroNome(coluna[5]);
		
		if(atual == null) {
			atual = new Bairro(coluna[5], this);
			atual.carregarBairro(coluna);
			this.setBairro(atual);
		}else {
			atual.carregarBairro(coluna);
		}	
	}
	
	public int getSizeBairro(){
		return this.bairros.size();
	}
}
