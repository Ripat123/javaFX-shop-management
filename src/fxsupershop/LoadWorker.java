package fxsupershop;

import javafx.animation.Timeline;
import javafx.concurrent.*;

/**
 *
 * @author Rifat Rabbi
 */
public class LoadWorker extends Service<Void> {

    Timeline t;
    public LoadWorker(Timeline t) {
        this.t = t;
    }
    

    @Override
    protected void succeeded() {
    }

    @Override
    protected void failed() {
    }

    @Override
    protected void cancelled() {
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                t.play();
                return null;
            }
        };
    }

}
