import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
public class Main {

    // Deixar insercão de novo servidor com o minimo de dados possível
    private static final Scanner scanner = new Scanner(System.in);


    public static boolean adicionarMais(boolean continuar){
        Scanner leitor = new Scanner(System.in);
        System.out.println("Deseja adicionar mais um servidor?s/n");
        String adicionar = leitor.nextLine();
        if(adicionar.equals("S") || adicionar.equals("s")){
            continuar = true;
        }else if(adicionar.equals("N") || adicionar.equals("n")){
            continuar = false;
        }else {
            System.out.println("Digite uma resposta válida!");
            adicionarMais(continuar);
        }
        return continuar;
    }
    public static void adicionarServidor() {
        boolean continuar = true;
        while(continuar) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite o id do DataCenter:");
            int fkDataCenter = Integer.parseInt(scanner.nextLine());
            String nome = "Servidor a ser editado";

            Servidor servidor = new Servidor(nome, fkDataCenter);
            String json = new Gson().toJson(servidor);
            System.out.println(json);
            HttpResponse<String> response = ApiClient.adicionarServidor(json);
            System.out.println("\n" + response.body());
            continuar = adicionarMais(continuar);
        }
        System.out.println("Desligando...");
        return;

    }

    private static final String BASE_URL = "http://localhost:3333";

    public static HttpResponse<String> autenticarUsuario(String email, String senha) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = String.format("%s/usuarios/autenticar/%s/%s", BASE_URL, email, senha);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


// Funções para limite de tentativas igual a 3
    static int tentativas;
    public static int getTentativas() {
        return tentativas;
    }

    public static int adicionarTentativa(){
        return tentativas++;
    }
    public static boolean verificaTentativas(){
        if(getTentativas() >= 3){
            tentativas = 0;
            return true;
        }
        return false;
    }
    public static void login() {
        boolean continuar = true;
        while (continuar) {
            System.out.print("\nEmail: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            HttpResponse<String> response = autenticarUsuario(email, senha);
            String body = response.body();


            if (response == null || body == null || body.contains("inválido")) {
                System.out.println("\n⚠ Email e/ou senha inválido(s)");
                adicionarTentativa();
                if (verificaTentativas()) {
                    System.out.println("Limite de tentativas alcançado. Reinicie o programa para tentar novamente!");
                    return;
                }
            } else {
                try {
                    System.out.println("\n✅ Login realizado com sucesso!");
                    continuar = false;
                } catch (Exception e) {
                    System.out.println("\n❌ Erro ao processar dados do login (JSON inválido ou chave ausente).");
                }
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("""
            ====================================
            Sistema de Monitoramento - MONITORA
            ====================================
            """);
        login();
        adicionarServidor();
    }
}



