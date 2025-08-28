import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ApiClient {
    public static HttpResponse<String> autenticarUsuario(String emailUsuario, String senhaUsuario) {
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

//    public static HttpResponse<String> adicionarServidor(String json) {
//        try {
//            HttpClient client = HttpClient.newHttpClient();
//
//
//
//        } catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
