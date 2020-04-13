package vu.lt.usecases;


import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.GameSession;
import vu.lt.interceptors.LoggedInvocation;
import vu.lt.persistence.GameSessionsDAO;

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
public class UpdateGameSessionDetails implements Serializable {

    private GameSession gameSession;

    @Inject
    private GameSessionsDAO gameSessionsDAO;

    @PostConstruct
    private void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer gameSessionId = Integer.parseInt(requestParameters.get("gameSessionId"));
        this.gameSession = gameSessionsDAO.findOne(gameSessionId);
    }

    @Transactional
    @LoggedInvocation
    public String updateGameSessionWinner() {
        try{
            gameSessionsDAO.update(this.gameSession);
        } catch (OptimisticLockException e) {
            return "/gameSessionDetails.xhtml?faces-redirect=true&gameSessionId=" + this.gameSession.getId() + "&error=optimistic-lock-exception";
        }
        return "gameSessions.xhtml?boardGameId=" + this.gameSession.getBoardGame().getId() + "&faces-redirect=true";
    }
}