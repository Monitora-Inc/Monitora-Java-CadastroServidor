import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static String login() {
        Scanner scanner = new Scanner(System.in);

        String body = "";

        while(true) {
            System.out.print("\nInforme seu email: ");
            String emailUsuario = scanner.nextLine();

            System.out.print("Informe sua senha: ");
            String senhaUsuario = scanner.nextLine();

            HttpResponse<String> response = ApiClient.autenticarUsuario(emailUsuario, senhaUsuario);
            body = response.body();

            if(body.equals("Email e/ou senha inválido(s)")) {
                System.out.println("\n⚠\uFE0F " + response.body());
            } else {
                System.out.println("\nCadastro realizado com sucesso!");
                break;
            }
        }

        return body;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("""
                Este software tem como objetivo cadastrar seu servidor ao nosso sistema para que futuramente possamos monitorá-lo.
                Para utilizá-lo:
                    - É necessária uma conexão com a internet;
                    - Será solicitado um login, utilize as mesmas credencias usadas em nosso website.
                
                Para iniciar, pressione ENTER:""");
        String iniciar = scanner.nextLine();

        String informacoesUsuarios = login();



    }
}
