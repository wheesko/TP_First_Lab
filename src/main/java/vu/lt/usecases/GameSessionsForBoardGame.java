package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.BoardGame;
import vu.lt.persistence.BoardGamesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Map;

@Model
public class GameSessionsForBoardGame {

    private BoardGamesDAO boardGamesDAO;

    @Inject
    public GameSessionsForBoardGame(
        BoardGamesDAO boardGamesDAO
    ) {
        this.boardGamesDAO = boardGamesDAO;
    }

    @Getter
    @Setter
    private BoardGame boardGame;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer teamId = Integer.parseInt(requestParameters.get("boardGameId"));
        this.boardGame = boardGamesDAO.findOne(teamId);
    }
}
