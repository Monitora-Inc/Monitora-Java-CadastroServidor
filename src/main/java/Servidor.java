public class Servidor {
    private String id;
    private String nome;
    private int fkDataCenter;

    public Servidor(String id, String nome, int fkDataCenter) {
        this.id = id;
        this.nome = nome;
        this.fkDataCenter = fkDataCenter;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public int getFkDataCenter() { return fkDataCenter; }
}
