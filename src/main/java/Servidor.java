public class Servidor {
    private String nome;
    private int fkDataCenter;
    private int limite;
    private int nomeComponenteId;
    private int medidaId;

    public Servidor(String nome, int fkDataCenter, int limite, int nomeComponenteId, int medidaId) {
        this.nome = nome;
        this.fkDataCenter = fkDataCenter;
        this.limite = limite;
        this.nomeComponenteId = nomeComponenteId;
        this.medidaId = medidaId;
    }

    public String getNome() { return nome; }
    public int getFkDataCenter() { return fkDataCenter; }
    public int getLimite() { return limite; }
    public int getNomeComponenteId() { return nomeComponenteId; }
    public int getMedidaId() { return medidaId; }
}
