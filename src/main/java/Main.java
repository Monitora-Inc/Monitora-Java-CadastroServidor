import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);




        private static int idEmpresaLogada = -1; // -1 indica que ninguém está logado

        //Getter para retornar
        public static int getIdEmpresaLogada() {
            return idEmpresaLogada;
        }

        // Metodo auxiliar para extrair o ID da empresa do JSON
        public static int getIdEmpresaFromJson(String jsonBody) throws Exception {
            JsonObject usuarioJson = JsonParser.parseString(jsonBody).getAsJsonObject();
            return usuarioJson.get("empresaId").getAsInt();
        }


    public static void login() {
        while (true) {
            System.out.print("\nEmail: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            HttpResponse<String> response = ApiClient.autenticarUsuario(email, senha);
            String body = response.body();


            if (response == null || body == null || body.contains("inválido")) {
                System.out.println("\n⚠ Email e/ou senha inválido(s)");
            } else {
                try {
                    // Extrai e salca o ID na variável estática
                    idEmpresaLogada = getIdEmpresaFromJson(body);

                    System.out.println("\n✅ Login realizado com sucesso!");
                    return;
                } catch (Exception e) {
                    System.out.println("\n❌ Erro ao processar dados do login (JSON inválido ou chave ausente).");
                }
            }
        }
    }


    public static void adicionarServidor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nNome do servidor: ");
        String nome = scanner.nextLine();

        System.out.print("ID do Data Center: ");
        int fkDataCenter = Integer.parseInt(scanner.nextLine());

        System.out.print("ID do componente (ex: 1 = CPU): ");
        int nomeComponenteId = Integer.parseInt(scanner.nextLine());

        System.out.print("ID da medida (ex: 1 = %): ");
        int medidaId = Integer.parseInt(scanner.nextLine());

        System.out.print("Limite de alerta (%): ");
        int limite = Integer.parseInt(scanner.nextLine());

        Servidor servidor = new Servidor(nome, fkDataCenter, limite, nomeComponenteId, medidaId);
        String json = new Gson().toJson(servidor);

        HttpResponse<String> response = ApiClient.adicionarServidor(json);
        System.out.println("\n" + response.body());
    }

    public static void atualizarServidor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nID do servidor a atualizar: ");
        int idServidor = Integer.parseInt(scanner.nextLine());

        System.out.print("Novo nome do servidor: ");
        String nome = scanner.nextLine();

        System.out.print("Novo ID do Data Center: ");
        int fkDataCenter = Integer.parseInt(scanner.nextLine());

        System.out.print("Novo limite de alerta (%): ");
        int limite = Integer.parseInt(scanner.nextLine());

        System.out.print("ID do componente (ex: 1 = CPU): ");
        int nomeComponenteId = Integer.parseInt(scanner.nextLine());

        System.out.print("ID da medida (ex: 1 = %): ");
        int medidaId = Integer.parseInt(scanner.nextLine());

        Servidor servidor = new Servidor(nome, fkDataCenter, limite, nomeComponenteId, medidaId);
        String json = new Gson().toJson(servidor);

        HttpResponse<String> response = ApiClient.atualizarServidor(idServidor, json);
        System.out.println("\n" + response.body());
    }


    public static void excluirServidor() {
        System.out.print("\nID do servidor a excluir: ");
        int id = Integer.parseInt(scanner.nextLine());

        HttpResponse<String> response = ApiClient.excluirServidor(id);
        System.out.println("\n" + response.body());
    }

    public static void listarServidores() {
        // Pega o id da empresa logada
        int idEmpresa = getIdEmpresaLogada();

        if (idEmpresa == -1) {
            System.out.println("\n❌ Erro: Usuário não logado ou ID da empresa inválido.");
            return;
        }

        HttpResponse<String> response = ApiClient.listarServidores(idEmpresa);

        if (response != null && response.body() != null) {
            System.out.println("\nServidores da Empresa ID " + idEmpresa + ":\n" + response.body());
        } else {
            System.out.println("\nErro ao listar servidores ou resposta vazia.");
        }
    }

    public static void main(String[] args) {

        System.out.println("""
            ================================
            Sistema de Monitoramento - MONITORA
            ================================
            """);

        login();

        while (true) {
            System.out.println("""
                \nEscolha uma opção:
                1 - Listar servidores
                2 - Adicionar servidor
                3 - Atualizar servidor
                4 - Excluir servidor
                0 - Sair
                """);

            System.out.print("Opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> listarServidores();
                case "2" -> adicionarServidor();
                case "3" -> atualizarServidor();
                case "4" -> excluirServidor();
                case "0" -> { System.out.println("Encerrando..."); return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}

