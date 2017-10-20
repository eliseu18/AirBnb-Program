

public class Estabelecimento {
	private int id;
	private double preco;
	private int disponibilidade;
	private int quantHospedeirosDiferentes;
	private int numeroMinNoites;
	private String tipoQuarto;
	private String nome;
	private Bairro bairro;
	private Anfitriao dono;
	
	public Estabelecimento(int id, String nome){
		this.nome = nome;
		this.id = id;
		this.preco = 0;
		this.disponibilidade = 0;
		this.quantHospedeirosDiferentes = 0;
		this.numeroMinNoites = 0;
		this.tipoQuarto = null;
		this.bairro = null;
	}
	
	public int getID() {
		return this.id;
	}
	
	public double getPreco() {
		return this.preco;
	}
	
	public int getDisponibilidade() {
		return this.disponibilidade;
	}
	
	public int getQuantHospedeirosDiferentes() {
		return this.quantHospedeirosDiferentes;
	}
	
	public int getNumeroMinNoites() {
		return this.numeroMinNoites;
	}
	
	public String getTipoQuarto() {
		return this.tipoQuarto;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public Bairro getBairro() {
		return this.bairro;
	}
	
	public Anfitriao getAnfitriao() {
		return dono;
	}
	
	public void setAnfitriao(Anfitriao dono) {
		this.dono = dono;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public void setDisponibilidade(int disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	
	public void setQuantHospedeirosDiferentes(int quant) {
		this.quantHospedeirosDiferentes = quant;
	}
	
	public void setNumeroMinNoites(int numero) {
		this.numeroMinNoites = numero;
	}
	
	public void setTipoQuarto(String tipo) {
		this.tipoQuarto = tipo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	public void carregarEstabelecimento(String[] coluna, Bairro bairro, Anfitriao dono) {
		this.setTipoQuarto(coluna[8]);
		this.setPreco(Double.parseDouble(coluna[9]));
		this.setNumeroMinNoites(Integer.parseInt(coluna[10]));
		this.setQuantHospedeirosDiferentes(Integer.parseInt(coluna[14]));
		this.setDisponibilidade(Integer.parseInt(coluna[15]));
		this.setBairro(bairro);
		this.setAnfitriao(dono);
	}
}