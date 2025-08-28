import java.io.*;
import java.util.UUID;

public class Uuid {
    public static final String arquivoNome = ".uuid";

    public static String buscarUuid() {
        File arquivo = new File(arquivoNome);

        try {
            if(arquivo.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                    return reader.readLine();
                }
            } else {
                return criarUuid();
            }
        } catch(IOException e) {
            throw new RuntimeException("Erro ao acessar o arquivo de uuid da máquina");
        }
    }

    public static String criarUuid() {
        File arquivo = new File(arquivoNome);

        String uuid = UUID.randomUUID().toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write(uuid);
            return uuid;
        } catch(IOException e) {
            throw new RuntimeException("Falha ao salvar o UUID no arquivo");
        }
    }
}
