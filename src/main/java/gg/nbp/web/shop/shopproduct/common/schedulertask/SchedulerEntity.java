package gg.nbp.web.shop.shopproduct.common.schedulertask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.TimerTask;

@Getter
@Setter
@AllArgsConstructor
public class SchedulerEntity {
    private Date date;
//    private SchedulerTask schedulerTask;
    private TimerTask timerTask;


}
