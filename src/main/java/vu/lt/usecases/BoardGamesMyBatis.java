package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.myBatis.dao.BoardGameMapper;
import vu.lt.myBatis.model.BoardGame;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class BoardGamesMyBatis {

    private BoardGameMapper boardGameMapper;

    @Inject
    public BoardGamesMyBatis (BoardGameMapper boardGameMapper) {
        this.boardGameMapper = boardGameMapper;
    }

    @Getter
    private List<BoardGame> allBoardGames;

    @Getter @Setter
    private BoardGame boardGameToCreate = new BoardGame();

    @PostConstruct
    public void init() {
        this.loadAllBoardGames();
    }

    private void loadAllBoardGames() {
        this.allBoardGames = boardGameMapper.selectAll();
    }

    @Transactional
    public String createBoardGame() {
        boardGameMapper.insert(boardGameToCreate);
        return "/myBatis/boardGames?faces-redirect=true";
    }
}