package gg.nbp.web.Act.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "act_message", schema = "five")
public class ActMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Integer id;

    @Column(name = "message_state")
    private Byte messageState;

    @Column(name = "Act_id")
    private Integer actId;

    @Column(name = "mem_id")
    private Integer memId;

}