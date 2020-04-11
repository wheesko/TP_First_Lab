package vu.lt.persistence;

import vu.lt.entities.BoardGame;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class BoardGameDAO {
    @PersistenceContext
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
}
