package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.BoardGame;
import vu.lt.persistence.BoardGamesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class BoardGames {

    @Inject
    private BoardGamesDAO boardGamesDAO;

    @Getter
    @Setter
    private BoardGame boardGameToCreate = new BoardGame();

    @Getter
    private List<BoardGame> allBoardGames;

    @PostConstruct
    public void init(){
        loadAllBoardGames();
    }

    @Transactional
    public String createBoardGame(){
        this.boardGamesDAO.persist(boardGameToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllBoardGames(){
        this.allBoardGames = boardGamesDAO.loadAll();
    }
}