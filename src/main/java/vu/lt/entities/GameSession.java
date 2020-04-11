package vu.lt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@javax.persistence.Entity
@NamedQueries({
        @NamedQuery(name = "GameSession.findAll", query = "select g from GameSession as g")
})
@Table(name = "GAME_SESSION")
@Getter
@Setter
public class GameSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="BOARDGAME_ID")
    private BoardGame boardGame;

    @Size(max = 50)
    @Column(name = "NAME")
    private String winnerName;

    @Size(max = 50)
    @Min(0)
    @Column(name = "SCORE")
    private Integer score;

    public GameSession() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSession entity = (GameSession) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(winnerName, entity.winnerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, winnerName);
    }
}
