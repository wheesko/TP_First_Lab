package vu.lt.rest.contracts;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GameSessionDTO {
    private String winnerName;

    private Date datePlayed;

    private Integer score;
}
