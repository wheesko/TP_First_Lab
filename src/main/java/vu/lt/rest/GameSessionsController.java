package vu.lt.rest;

import lombok.*;
import vu.lt.entities.GameSession;
import vu.lt.persistence.GameSessionsDAO;
import vu.lt.rest.contracts.GameSessionDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/gameSessions")
public class GameSessionsController {

    @Inject
    @Setter @Getter
    private GameSessionsDAO gameSessionsDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        GameSession gameSession = gameSessionsDAO.findOne(id);
        if (gameSession == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GameSessionDTO gameSessionDTO = new GameSessionDTO();
        gameSessionDTO.setWinnerName(gameSession.getWinnerName());
        gameSessionDTO.setDatePlayed(gameSession.getDatePlayed());
        gameSessionDTO.setScore(gameSession.getScore());

        return Response.ok(gameSessionDTO).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer gameSessionId,
            GameSessionDTO gameSessionData) {
        try {
            GameSession existingGameSession = gameSessionsDAO.findOne(gameSessionId);
            if (existingGameSession == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingGameSession.setWinnerName(gameSessionData.getWinnerName());
            existingGameSession.setScore(gameSessionData.getScore());
            gameSessionsDAO.update(existingGameSession);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}