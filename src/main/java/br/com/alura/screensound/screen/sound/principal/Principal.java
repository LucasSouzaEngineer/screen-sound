package br.com.alura.screensound.screen.sound.principal;

import br.com.alura.screensound.screen.sound.model.Artista;
import br.com.alura.screensound.screen.sound.model.Musica;
import br.com.alura.screensound.screen.sound.repository.ArtistaRepository;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ArtistaRepository repositorio;
    private List<Artista> artistas;

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibirMenu(){
        var menu = """
                *** SCREEN SOUND MUSICAS ***
                
                1 - Cadastrar Artistas
                2 - Cadastrar Musicas
                3 - Listar Musicas
                4 - Buscar Musicas por Artista
                
                0 - Sair
                """;
        var opcao = -1;
        while(opcao != 0){
            System.out.println(menu);
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao){
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 0:
                    System.out.println("Saindo da aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void cadastrarArtista() {
        System.out.println("Digite o nome do artista: ");
        var nome = teclado.nextLine();
        System.out.println("Digite a categoria do artista (solo, dupla ou banda): ");
        var categoria = teclado.nextLine();
        Artista artista = new Artista(nome, categoria);
        repositorio.save(artista);
    }

    private void cadastrarMusica() {
        artistas = repositorio.findAll();
        artistas.forEach(System.out::println);
        System.out.println("Digite o nome de um dos Artistas para cadastrar as musicas: ");
        var artistaBusca = teclado.nextLine();
        Optional<Artista> artistaEncontrado = repositorio.findByNomeContainingIgnoreCase(artistaBusca);

        if (artistaEncontrado.isPresent()){
            Artista artista = artistaEncontrado.get();
            List<Musica> musicas = new ArrayList<>();

            while (true){
                System.out.println("Digite o nome da musica para cadastrar (0 para sair): ");
                var nomeMusica = teclado.nextLine();
                if (nomeMusica.equals("0")){
                    break;
                }
                Musica musica = new Musica(nomeMusica);
                musicas.add(musica);
            }
            artista.setMusicas(musicas);
            repositorio.save(artista);
        }
    }

    private void listarMusicas() {
        List<Musica> musicas = repositorio.musicasGeral();
        musicas.forEach(System.out::println);
    }

    private void buscarMusicasPorArtista() {
        buscarArtistas();
        System.out.println("Digite o nome do artista para buscar as musicas: ");
        var artistaBusca = teclado.nextLine();
        Optional<Artista> artistaEncontrado = repositorio.findByNomeContainingIgnoreCase(artistaBusca);
        if(artistaEncontrado.isPresent()){
            List<Musica> musicas = repositorio.musicasPorArtista(artistaEncontrado.get());
            musicas.forEach(System.out::println);
        }else {
            System.out.println("Artista não encontrado");
        }
    }

    private void buscarArtistas(){
        List<Artista> artistas = repositorio.findAll();
        artistas.stream()
                .sorted(Comparator.comparing(Artista::getNome))
                .forEach(System.out::println);
    }
}
