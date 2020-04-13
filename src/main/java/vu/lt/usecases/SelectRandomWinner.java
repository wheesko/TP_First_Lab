package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.interceptors.LoggedInvocation;
import vu.lt.services.RandomWinnerChooser;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SessionScoped
@Named
public class SelectRandomWinner implements Serializable {
    @Inject
    RandomWinnerChooser randomWinnerChooser;

    private Future<String> randomWinnerGenerationTask = null;

    @Getter @Setter
    private String winnersList = null;

    @LoggedInvocation
    public String generateNewRandomWinner() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        randomWinnerGenerationTask = randomWinnerChooser.generateRandomWinner(Arrays.asList(winnersList.split(",")));
        return  "/gameSessionDetails.xhtml?faces-redirect=true&gameSessionId=" + requestParameters.get("gameSessionId");
    }

    public boolean isWinnerGenerationRunning() {
        return randomWinnerGenerationTask != null && !randomWinnerGenerationTask.isDone();
    }

    public String getRandomWinnerChoosingStatus() throws ExecutionException, InterruptedException {
        if (randomWinnerGenerationTask == null) {
            return null;
        } else if (isWinnerGenerationRunning()) {
            return "Choosing in progress...";
        }
        return "Suggested winner: " + randomWinnerGenerationTask.get();
    }
}