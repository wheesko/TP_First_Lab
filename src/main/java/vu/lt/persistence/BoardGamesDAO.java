package vu.lt.persistence;

import vu.lt.entities.BoardGame;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class BoardGamesDAO {
    @Inject
    private EntityManager em;

    public List<BoardGame> loadAll() {
        return em.createNamedQuery("BoardGame.findAll", BoardGame.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(BoardGame boardGame){
        this.em.persist(boardGame);
    }

    public BoardGame findOne(Integer id) {
        return em.find(BoardGame.class, id);
    }
}
