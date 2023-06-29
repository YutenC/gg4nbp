package gg.nbp.web.Act.model;

import lombok.Data;

@Data
public class Message {
//這邊會導致畫面的實體出現undefined: undefined
    private String username;
    private String time;
    private String message;
}
