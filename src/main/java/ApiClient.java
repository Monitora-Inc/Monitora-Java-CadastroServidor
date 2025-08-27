import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ApiClient {
    public static HttpResponse<String> autenticarUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Faça o login com o mesmo usuário de nosso sistema.\n");

        System.out.print("Informe seu email: ");
        String emailUsuario = scanner.nextLine();

        System.out.print("Informe sua senha: ");
        String senhaUsuario = scanner.nextLine();

        try {
            HttpClient client = HttpClient.newHttpClient();

            String url = String.format("http://localhost:3333/usuarios/autenticar/%s/%s", emailUsuario, senhaUsuario);
            URI uri = URI.create(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse<String> buscarServidorUUID(String uuid) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String url = String.format("http://localhost:3333/servidores/buscarServidorUUID/%s", uuid);
            URI uri = URI.create(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static HttpResponse<String> adicionarServidor() {
//        try {
//            HttpClient client = HttpClient.newHttpClient();
//
//        } catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static void main(String[] args) {
        autenticarUsuario();
    }
}
