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

    public <T> TaskEntity(String name, Callable<T> callable) {
        this.name = name;
        this.callable = callable;
    }
}
