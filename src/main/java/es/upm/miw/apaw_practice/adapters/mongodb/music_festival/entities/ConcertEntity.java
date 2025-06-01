package es.upm.miw.apaw_practice.adapters.mongodb.music_festival.entities;

import es.upm.miw.apaw_practice.domain.models.music_festival.Concert;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "concerts")
public class ConcertEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String code;
    private LocalDate date;
    private BigDecimal ticketPrice;
    private boolean isSoldOut;

    @DBRef(lazy = true)
    private StageEntity stage;

    @DBRef(lazy = true)
    private List<ConcertArtistEntity> artists;

    public ConcertEntity() {
        //empty from framework
    }

    public ConcertEntity(String code, LocalDate date, BigDecimal ticketPrice, boolean isSoldOut,
                         StageEntity stageEntity, List<ConcertArtistEntity> artists) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.isSoldOut = isSoldOut;
        this.stage = stageEntity;
        this.artists = artists;
    }

    public Concert toDomain() {
        Concert concert = new Concert();
        BeanUtils.copyProperties(this, concert, "stage", "artists");
        concert.setStage(this.stage != null ? this.stage.toDomain(): null);
        concert.setArtists( this.artists != null ?
                this.artists.stream()
                        .map(ConcertArtistEntity::toDomain)
                        .collect(Collectors.toList()) : null
        );
        return concert;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public StageEntity getStage() {
        return stage;
    }

    public void setStage(StageEntity stage) {
        this.stage = stage;
    }

    public List<ConcertArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(List<ConcertArtistEntity> artists) {
        this.artists = artists;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ConcertEntity that = (ConcertEntity) object;
        return Objects.equals(this.code, that.code);
    }

    @Override
    public String toString() {
        return "ConcertEntity{" +
                "id='" + id + '\'' +
                ", concertCode='" + code + '\'' +
                ", date=" + date +
                ", ticketPrice=" + ticketPrice +
                ", isSoldOut=" + isSoldOut +
                ", stage=" + stage +
                ", artists=" + artists +
                '}';
    }
}
