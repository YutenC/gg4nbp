package gg.nbp.web.shop.shopproduct.common.schedulertask;

import java.util.HashMap;
import java.util.Map;

public class SchedulerFactory {
    //SchedulerTasks
    static Map<String, SchedulerTasks> schedulerTasks = new HashMap<>();

    public static SchedulerTasks getSchedulerTasks(String key){

        if(!schedulerTasks.containsKey(key)){
            synchronized (SchedulerFactory.class){
                if(!schedulerTasks.containsKey(key)){
                    schedulerTasks.put(key,new SchedulerTasks());
                }
            }
        }

        return schedulerTasks.get(key);
    }

}
