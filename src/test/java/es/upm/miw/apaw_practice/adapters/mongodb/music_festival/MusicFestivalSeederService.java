package es.upm.miw.apaw_practice.adapters.mongodb.music_festival;

import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.daos.ConcertArtistRepository;
import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.daos.ConcertRepository;
import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.daos.MusicFestivalRepository;
import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.daos.StageRepository;
import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.entities.ConcertArtistEntity;
import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.entities.ConcertEntity;
import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.entities.MusicFestivalEntity;
import es.upm.miw.apaw_practice.adapters.mongodb.music_festival.entities.StageEntity;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class MusicFestivalSeederService {
    @Autowired
    private MusicFestivalRepository musicFestivalRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private ConcertArtistRepository concertArtistRepository;

    public void seedDatabase() {
        LogManager.getLogger(this.getClass()).warn("------- Music Festival Initial Load -----------");

        StageEntity[] stageEntities = {
                new StageEntity("MainStage", "Parque Central", 10000, LocalDateTime.of(2025, 5, 10, 14, 0)),
                new StageEntity("SecondStage", "Teatro Abierto", 5000, LocalDateTime.of(2025, 5, 11, 15, 0)),
                new StageEntity("DanceFloor", "Estadio Norte", 8000, LocalDateTime.of(2025, 5, 12, 16, 30)),
                new StageEntity("AcousticCorner", "Sala Pequeña", 2000, LocalDateTime.of(2025, 5, 13, 18, 0))
        };
        List<StageEntity> stageEntitiesSaved = this.stageRepository.saveAll(Arrays.asList(stageEntities));

        ConcertArtistEntity[] artistEntities = {
                new ConcertArtistEntity("Los Fabulosos", "Mexicana", 4.5),
                new ConcertArtistEntity("DJ Luna", "Española", 4.2),
                new ConcertArtistEntity("ElectroBand", "Argentina", 4.7),
                new ConcertArtistEntity("FolkSingers", "Colombiana", 4.1),
                new ConcertArtistEntity("RockPower", "Chilena", 4.6),
                new ConcertArtistEntity("PopStar", "USA", 4.0),
                new ConcertArtistEntity("ClassicalDuo", "Frances", 4.9),
                new ConcertArtistEntity("IndieWave", "Ingles", 4.3)
        };
        List<ConcertArtistEntity> artistEntitiesSaved = this.concertArtistRepository.saveAll(Arrays.asList(artistEntities));

        ConcertEntity[] concertEntities = {
                new ConcertEntity("CON001", LocalDate.of(2025, 5, 15), BigDecimal.valueOf(60.50), false, stageEntitiesSaved.get(0),
                        Arrays.asList(artistEntitiesSaved.get(0), artistEntitiesSaved.get(1), artistEntitiesSaved.get(2))),
                new ConcertEntity("CON002", LocalDate.of(2025, 5, 16), BigDecimal.valueOf(55.00), false, stageEntitiesSaved.get(1),
                        Arrays.asList(artistEntitiesSaved.get(0), artistEntitiesSaved.get(3), artistEntitiesSaved.get(7))),
                new ConcertEntity("CON003", LocalDate.of(2025, 5, 17), BigDecimal.valueOf(65.00), false,stageEntitiesSaved.get(2),
                        Arrays.asList(artistEntitiesSaved.get(4), artistEntitiesSaved.get(5), artistEntitiesSaved.get(6))),
                new ConcertEntity("CON004", LocalDate.of(2025, 5, 18), BigDecimal.valueOf(70.00), true, stageEntitiesSaved.get(3),
                        Arrays.asList(artistEntitiesSaved.get(2), artistEntitiesSaved.get(5), artistEntitiesSaved.get(7)))
        };


        List<ConcertEntity> concertEntitiesSaved = this.concertRepository.saveAll(Arrays.asList(concertEntities));


        MusicFestivalEntity[] musicFestivalEntities = {
                new MusicFestivalEntity("SpringFest", LocalDateTime.of(2025, 5, 1, 10, 0), BigDecimal.valueOf(200000),
                        Arrays.asList(concertEntitiesSaved.get(0))),// Festival 1: Usa Concierto 1
                new MusicFestivalEntity("SummerBeat", LocalDateTime.of(2025, 6, 1, 9, 0), BigDecimal.valueOf(180000),
                        Arrays.asList(concertEntitiesSaved.get(0), concertEntitiesSaved.get(1))), // Festival 2: Usa Concierto 1, 2
                new MusicFestivalEntity("AutumnRock", LocalDateTime.of(2025, 9, 1, 11, 0),
                        BigDecimal.valueOf(150000), Arrays.asList(concertEntitiesSaved.get(1), concertEntitiesSaved.get(2))), // Festival 3: Usa Concierto 2, 3
                new MusicFestivalEntity("WinterAcoustic", LocalDateTime.of(2025, 12, 5, 12, 30), BigDecimal.valueOf(100000),
                        Arrays.asList(concertEntitiesSaved.get(3))), //Festival 4: Usa Concierto 4
                new MusicFestivalEntity("MultiGenreFest", LocalDateTime.of(2025, 8, 12, 14, 0), BigDecimal.valueOf(300000),
                        Arrays.asList(concertEntitiesSaved.get(0), concertEntitiesSaved.get(2), concertEntitiesSaved.get(3))), // Festival 5: Usa los 4 Conciertos
                new MusicFestivalEntity("MegaFestival", LocalDateTime.of(2025, 7, 22, 16, 0), BigDecimal.valueOf(500000),
                        Arrays.asList(concertEntitiesSaved.get(0), concertEntitiesSaved.get(1), concertEntitiesSaved.get(2), concertEntitiesSaved.get(3))) // Festival 6: Usa los 4 Conciertos
        };

        this.musicFestivalRepository.saveAll(Arrays.asList(musicFestivalEntities));

    }

    public void deleteAll() {
        this.musicFestivalRepository.deleteAll();
        this.concertRepository.deleteAll();
        this.stageRepository.deleteAll();
        this.concertArtistRepository.deleteAll();
    }
}
