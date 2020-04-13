package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.BoardGame;
import vu.lt.entities.GameSession;
import vu.lt.persistence.BoardGamesDAO;
import vu.lt.persistence.GameSessionsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Map;

@Model
public class GameSessionsForBoardGame {

    private BoardGamesDAO boardGamesDAO;
    private GameSessionsDAO gameSessionsDAO;

    @Inject
    public GameSessionsForBoardGame(
        BoardGamesDAO boardGamesDAO,
        GameSessionsDAO gameSessionsDAO
    ) {
        this.boardGamesDAO = boardGamesDAO;
        this.gameSessionsDAO = gameSessionsDAO;
    }

    @Getter
    @Setter
    private BoardGame boardGame;

    @Getter @Setter
    private GameSession gameSessionToCreate = new GameSession();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer boardGameId = Integer.parseInt(requestParameters.get("boardGameId"));
        this.boardGame = boardGamesDAO.findOne(boardGameId);
    }

    @Transactional
    public String createGameSession() {
        gameSessionToCreate.setBoardGame(this.boardGame);
        gameSessionsDAO.persist(gameSessionToCreate);
        return "gameSessions?faces-redirect=true&boardGameId=" + this.boardGame.getId();
    }
}
