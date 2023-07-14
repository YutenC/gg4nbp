package gg.nbp.web.shop.shopproduct.common.backgroundtask;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class BackgroundHandler implements Runnable {

    private BackgroundTasks backgroundTasks;
    boolean flag = true;
    Queue<TaskEntity> queue = new ConcurrentLinkedQueue<TaskEntity>();

    public BackgroundHandler() {
        backgroundTasks = new BackgroundTasks();
    }

    @Override
    public void run() {
        while (flag) {
            try {
                synchronized (this) {
                    wait();
                }

                while (!queue.isEmpty()) {
                    TaskEntity t = queue.remove();
                    backgroundTasks.runTask(t);
                }

            } catch (NoSuchElementException e) {

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public <T> void addTask(String name, Callable<T> callable) {
        queue.add(new TaskEntity(name, callable));
        synchronized (this) {
            notify();
        }
    }

    public <T> T getTaskResult(String name) {
        TaskEntity taskEntity = backgroundTasks.getTask(name);
        Future<?> future = taskEntity.getResult();

        if (future.isDone()) {
            try {
                Type[] types = future.getClass().getGenericInterfaces();
                ParameterizedType parameterizedType = (ParameterizedType) types[0];
                Type[] parameterizedTypes = parameterizedType.getActualTypeArguments();

                T t = (T) future.get();

                return (T) future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public void close() {
        flag = false;
        notify();
    }
}
