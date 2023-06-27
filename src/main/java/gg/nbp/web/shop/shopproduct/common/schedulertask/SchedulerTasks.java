package gg.nbp.web.shop.shopproduct.common.schedulertask;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class SchedulerTasks {
    private Map<String, SchedulerEntity> map = new ConcurrentHashMap<>();
    private Timer timer;

    public SchedulerTasks() {
        timer = new Timer();
    }

    public void addTimerTask(String key, SchedulerEntity schedulerEntity){
        map.put(key,schedulerEntity);

        synchronized (this){
            timer.schedule(schedulerEntity.getTimerTask(), schedulerEntity.getDate());
            timer.purge();
        }
    }

    public SchedulerEntity getTimerTask(String key){
        return map.get(key);
    }

    public void removeTimerTask(String key){
        map.remove(key);
    }

    public void clear(){
        synchronized (this){
            timer.purge();
        }
    }

}
