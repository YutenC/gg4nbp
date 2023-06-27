package gg.nbp.web.Act.dao;


import gg.nbp.web.Act.model.ActMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActMessageRepository extends JpaRepository<ActMessage, Integer> {
    List<ActMessage> findById(Long messageId);

    List<ActMessage> findByMemId(Long MemId);

    List<ActMessage> findByActId(Long ActId);


    @Modifying
    @Query("UPDATE ActMessage SET messageState = :messageState WHERE id = :id")
    void updateMessageStateById(@Param("id") Integer id, @Param("messageState") Byte messageState);
}