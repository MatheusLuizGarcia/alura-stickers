import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        /* DESAFIOS:
          1: Consumir o endpoint de filmes mais populares da API do IMDB.
         Procure também, na documentação da API do IMDB, o endpoint
         que retorna as melhores séries e o que retorna as séries mais populares.
         ❌IMPOSSIBILITADO DEVIDO AO BLOQUEIO DA API DO IMDB.
         
          2: Usar sua criatividade para deixar a saída dos dados mais bonitinha: 
         usar emojis com código UTF-8, mostrar a nota do filme como estrelinhas, 
         decorar o terminal com cores, negrito e itálico usando códigos ANSI, e mais!
         ✅ FEITO!

          3: Colocar a chave da API do IMDB em algum lugar fora do código
          como um arquivo de configuração (p. ex, um arquivo .properties)
          ou uma variável de ambiente.
         ❌IMPOSSIBILITADO DEVIDO AO BLOQUEIO DA API DO IMDB.

          4: Mudar o JsonParser para usar uma biblioteca de parsing
          de JSON como Jackson ou GSON.
          ❕ EM ESPERA.

          5: Desafio supremo: criar alguma maneira para você dar uma avaliação
          ao filme, puxando de algum arquivo de configuração OU pedindo a avaliação
          para o usuário digitar no terminal.
          ❕ EM ESPERA.
         */

        //definição das cores para o terminal
        String corDefault = "\u001b[0m";                 //cor padrão do console
        String corTitulo = corDefault + "\u001b[37;1m"; //cor da fonte para os títulos
        String corLink = corDefault + "\u001b[34;1m"; //cor da fonte para os links de imagem
        String corNota = corDefault + "\u001b[32;1m";    //cor da fonte para as notas
        String corBody = corDefault + "\u001b[30;1m";      //cor de destaque do texto em geral

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alexfelipe/imersao-java/json/top250.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        // exibir e manipular os dados
        
        for (Map<String,String> filme : listaDeFilmes) {

            //formatar cores de saída do terminal

            String titulo = "Título..: " + corTitulo + filme.get("title");
            String imagem = "Imagem..: " + corLink + filme.get("image");
            String classificacao = "Nota....: " +  corNota + filme.get("imDbRating");

            
            titulo = corBody + titulo + corDefault; 
            imagem = corBody + imagem + corDefault;
            classificacao = corBody + classificacao + corDefault;

            // notas em emoji de estrela

            String notaEstrela = "";
            String unicodeEstrela = "\u2B50";
            double estrela = Double.valueOf(filme.get("imDbRating"));

            //printar informações

            int i;

            System.out.println("=========================================================================");
            notaEstrela = "";
            System.out.println(titulo);
            System.out.println(imagem);
            System.out.println(classificacao);
            for( i = 0; i < estrela; i++) {
                notaEstrela += unicodeEstrela;    
            }
            System.out.println(notaEstrela);

	
        }
    }
}