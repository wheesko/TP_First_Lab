package vu.lt.services;

import org.apache.deltaspike.core.api.future.Futureable;

import javax.ejb.AsyncResult;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.concurrent.Future;
import java.util.Random;
import java.util.List;

@ApplicationScoped
public class RandomWinnerChooser implements Serializable {

    @Futureable
    public Future<String> generateRandomWinner(List<String> names) {
        try {
            Thread.sleep(3000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        int rnd = new Random().nextInt(names.size());
        return new AsyncResult<>(names.get(rnd));
    }
}