package vu.lt.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@javax.persistence.Entity
@NamedQueries({
        @NamedQuery(name = "BoardGame.findAll", query = "select b from BoardGame as b")
})
@Table(name = "BOARD_GAME")
@Getter
@Setter
public class BoardGame implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "boardGame")
    private List<GameSession> gameSessions;

    public BoardGame() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGame boardGame = (BoardGame) o;
        return Objects.equals(id, boardGame.id) &&
                Objects.equals(name, boardGame.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
