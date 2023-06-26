package gg.nbp.web.shop.shopproduct.common.backgroundtask;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Getter
@Setter
public class TaskEntity {

    String name;
    String sessionId;
    Runnable runnable;

    Callable<?> callable;
    Future<?> result;



//    Map<String,Runnable> taskRunnable=new ConcurrentHashMap<>();
//    Map<String, Future<?>> taskResult=new ConcurrentHashMap<>();
//    Map<String,TaskState> taskInfo=new ConcurrentHashMap<>();



//    public TaskEntity(String name,Runnable runnable){
//
//    }


    public TaskEntity(String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
    }

    public <T> TaskEntity(String name, Callable<T> callable) {
        this.name = name;
        this.callable = callable;
    }
}
