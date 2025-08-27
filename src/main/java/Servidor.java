import oshi.SystemInfo;

public class Servidor {
    String uuid;
    String modeloCPU;
    long qtdRam;
    long qtdDisco;
    String sistemaOperacional;

    public Servidor(String uuid, String modeloCPU, long qtdRam, long qtdDisco, String sistemaOperacional) {
        this.uuid = uuid;
        this.modeloCPU = modeloCPU;
        this.qtdRam = qtdRam;
        this.qtdDisco = qtdDisco;
        this.sistemaOperacional = sistemaOperacional;
    }

    public static Servidor capturarInformacoesComputador() {
        SystemInfo si = new SystemInfo();

        String modeloCPU = si.getHardware().getProcessor().getProcessorIdentifier().getName();
        long qtdRam = si.getHardware().getMemory().getTotal() / (1024 * 1024 * 1024);
        String sistemaOperacional = si.getOperatingSystem().toString();
        long qtdDisco = si.getHardware().getDiskStores().getFirst().getSize() / (1024 * 1024 * 1024);

        Servidor servidor = new Servidor("aa", modeloCPU, qtdRam, qtdDisco, sistemaOperacional);

        return servidor;
    }

    public static void main(String[] args) {
        capturarInformacoesComputador();
    }
}
