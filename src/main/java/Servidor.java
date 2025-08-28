import com.google.gson.Gson;
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

    public static String capturarInformacoesComputador() {
        SystemInfo si = new SystemInfo();

        String uuid = Uuid.buscarUuid();
        String modeloCPU = si.getHardware().getProcessor().getProcessorIdentifier().getName();
        long qtdRam = si.getHardware().getMemory().getTotal() / (1024 * 1024 * 1024);
        String sistemaOperacional = si.getOperatingSystem().toString();
        long qtdDisco = si.getHardware().getDiskStores().getFirst().getSize() / (1024 * 1024 * 1024);

        Servidor servidor = new Servidor(uuid, modeloCPU, qtdRam, qtdDisco, sistemaOperacional);
        String json = new Gson().toJson(servidor);
        System.out.println(json);

        return json;
    }
}
