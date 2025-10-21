public class Servidor {
    private String nome;
    private int fkDataCenter;

    public Servidor(String nome, int fkDataCenter) {
        this.nome = nome;
        this.fkDataCenter = fkDataCenter;
    }

    public String getNome() { return nome; }
    public int getFkDataCenter() { return fkDataCenter; }

}
