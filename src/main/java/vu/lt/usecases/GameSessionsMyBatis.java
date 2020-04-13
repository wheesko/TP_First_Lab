package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.myBatis.model.BoardGame;
import vu.lt.myBatis.model.GameSession;
import vu.lt.myBatis.dao.BoardGameMapper;
import vu.lt.myBatis.dao.GameSessionMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class GameSessionsMyBatis {

    private GameSessionMapper gameSessionMapper;
    private BoardGameMapper boardGameMapper;

    @Inject
    public GameSessionsMyBatis(
            GameSessionMapper gameSessionMapper,
            BoardGameMapper boardGameMapper
    ) {
        this.gameSessionMapper = gameSessionMapper;
        this.boardGameMapper = boardGameMapper;
    }

    @Getter
    @Setter
    private BoardGame boardGame;

    @Getter @Setter
    private GameSession gameSessionToCreate = new GameSession();

    @Getter @Setter
    private List<GameSession> gameSessionList = new ArrayList<>();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer boardGameId = Integer.parseInt(requestParameters.get("boardGameId"));
        this.boardGame = boardGameMapper.selectByPrimaryKey(boardGameId);
        this.gameSessionList = gameSessionMapper.selectGameSessionsForBoardGame(boardGameId);
    }

    @Transactional
    public String createGameSession() {
        gameSessionToCreate.setBoardgameId(this.boardGame.getId());
        gameSessionMapper.insert(gameSessionToCreate);
        return "gameSessions?faces-redirect=true&boardGameId=" + this.boardGame.getId();
    }
}