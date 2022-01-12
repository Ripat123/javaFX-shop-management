package fxsupershop.Services;

import javafx.concurrent.*;

/**
 *
 * @author Rifat Rabbi
 */
public class BackgroundService extends Service<Void> {

    static Object ob;
    static String methodName;

    public BackgroundService(Object ob, String methodName) {
        this.ob = ob;
        this.methodName = methodName;
    }

    @Override
    protected void succeeded() {
        cancel();
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
                try {
                    ob.getClass().getMethod(methodName, null).invoke(ob);
                } catch (Exception e) {
                }
                return null;
            }
        };
    }

}
