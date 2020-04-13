package vu.lt.persistence;

import vu.lt.entities.GameSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class GameSessionsDAO {
    @Inject
    private EntityManager em;

    public void persist(GameSession gameSession){
        this.em.persist(gameSession);
    }

    public GameSession findOne(Integer id) {
        return em.find(GameSession.class, id);
    }

    public GameSession update(GameSession gameSession) {
        return em.merge(gameSession);
    }
}