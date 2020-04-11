package vu.lt.usecases;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;

import vu.lt.entities.BoardGame;
import vu.lt.persistence.BoardGameDAO;

import java.util.List;

@Model
public class BoardGames implements Serializable {
    @Inject
    private BoardGameDAO boardGameDAO;

    private BoardGame entityToCreate = new BoardGame();

    private List<BoardGame> allEntities;

    @PostConstruct
    public void init() {
        loadAllEntities();
    }
    public void loadAllEntities() {
        this.allEntities = boardGameDAO.loadAll();
    }

    @Transactional
    public String createEntity(){
        this.boardGameDAO.persist(entityToCreate);
        return "success";
    }

    public BoardGame getBoardGameToCreate() {
        return entityToCreate;
    }

    public void setBoardGameToCreate(BoardGame entityToCreate) {
        this.entityToCreate = entityToCreate;
    }

    public List<BoardGame> getAllEntities() {
        return allEntities;
    }

    public void setAllEntities(List<BoardGame> allEntities) {
        this.allEntities = allEntities;
    }
}
