package br.com.alura.screensound.screen.sound.repository;

import br.com.alura.screensound.screen.sound.model.Artista;
import br.com.alura.screensound.screen.sound.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    @Query("SELECT m FROM Musica m")
    List<Musica> musicasGeral();

    @Query("SELECT m FROM Artista a Join a.musicas m WHERE a = :artista")
    List<Musica> musicasPorArtista(Artista artista);

    Optional<Artista> findByNomeContainingIgnoreCase(String artistaBusca);
}
