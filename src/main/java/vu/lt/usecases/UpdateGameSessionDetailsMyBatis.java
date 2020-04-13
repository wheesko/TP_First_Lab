package vu.lt.usecases;


import lombok.Getter;
import lombok.Setter;
import vu.lt.myBatis.dao.BoardGameMapper;
import vu.lt.myBatis.dao.GameSessionMapper;
import vu.lt.myBatis.model.GameSession;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdateGameSessionDetailsMyBatis implements Serializable {

    private GameSession gameSession;

    @Inject
    private GameSessionMapper gameSessionMapper;
    @Inject
    private BoardGameMapper boardGameMapper;

    @PostConstruct
    private void init() {
        System.out.println("UpdateGameSessionMyBatis INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer gameSessionId = Integer.parseInt(requestParameters.get("gameSessionId"));
        this.gameSession = gameSessionMapper.selectByPrimaryKey(gameSessionId);
        this.gameSession.setBoardGame(boardGameMapper.selectByPrimaryKey(this.gameSession.getBoardgameId()));
    }

    @Transactional
    public String updateGameSessionWinner() {
        try{
            gameSessionMapper.updateByPrimaryKey(this.gameSession);
        } catch (OptimisticLockException e) {
            return "/myBatis/gameSessionDetails.xhtml?faces-redirect=true&gameSessionId=" + this.gameSession.getId() + "&error=optimistic-lock-exception";
        }
        return "/myBatis/gameSessions.xhtml?boardGameId=" + this.gameSession.getBoardgameId() + "&faces-redirect=true";
    }
}