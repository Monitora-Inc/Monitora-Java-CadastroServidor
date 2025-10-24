import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static int tentativas = 0;

    public static void main(String[] args) {
        System.out.println("""
            ====================================
            Sistema de Monitoramento - MONITORA
            ====================================
            """);

        JsonObject usuario = login();
        if (usuario != null) {
            int idEmpresa = usuario.get("empresaId").getAsInt();
            adicionarServidorAutomaticamente(idEmpresa);
        } else {
            System.out.println("Não foi possível autenticar o usuário.");
        }
    }

    public static JsonObject login() {
        while (true) {
            System.out.println("Email: ");
            String email = scanner.nextLine();

            System.out.println("Senha: ");
            String senha = scanner.nextLine();

            HttpResponse<String> response = ApiClient.autenticarUsuario(email, senha);

            if (response == null) {
                System.out.println("Erro de conexão com o servidor.");
                return null;
            }

            String body = response.body();
            if (body.contains("inválido") || body.contains("erro")) {
                tentativas++;
                System.out.println("Email e/ou senha inválido(s)");
                if (tentativas >= 3) {
                    System.out.println("Limite de tentativas alcançado. Reinicie o programa.");
                    return null;
                }
            } else {
                try {
                    JsonObject jsonLogin = JsonParser.parseString(body).getAsJsonObject();
                    System.out.println("Login realizado com sucesso!");
                    return jsonLogin;
                } catch (Exception e) {
                    System.out.println("Erro ao processar resposta do servidor.");
                    return null;
                }
            }
        }
    }

    public static void adicionarServidorAutomaticamente(int idEmpresa) {
        HttpResponse<String> responseDataCenters = ApiClient.buscarDataCenters(idEmpresa);

        if (responseDataCenters == null || responseDataCenters.body() == null) {
            System.out.println("Erro ao buscar Data Centers!");
            return;
        }

        Gson gson = new Gson();
        JsonObject[] dataCenters = gson.fromJson(responseDataCenters.body(), JsonObject[].class);

        if (dataCenters.length == 0) {
            System.out.println("Nenhum Data Center encontrado para essa empresa.");
            return;
        }

        int fkDataCenter = dataCenters[0].get("idDataCenter").getAsInt();
        String nomeServidor = "Servidor-" + processId.id();

        Servidor servidor = new Servidor(processId.id(), nomeServidor, fkDataCenter);
        String json = gson.toJson(servidor);

        System.out.println("Enviando servidor para cadastro...");
        HttpResponse<String> response = ApiClient.adicionarServidor(json);

        if (response != null && response.statusCode() == 200) {
            System.out.println("Servidor cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar servidor: " +
                    (response != null ? response.body() : "Erro desconhecido"));
        }
    }
}
