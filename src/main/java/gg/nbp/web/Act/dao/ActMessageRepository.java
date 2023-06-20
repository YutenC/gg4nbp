package gg.nbp.web.Act.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gg.nbp.web.Act.model.ActMessage;

public interface ActMessageRepository extends JpaRepository<ActMessage, Integer> {
    List<ActMessage> findById(Long messageId);

    List<ActMessage> findByMemId(Long MemId);

    List<ActMessage> findByActId(Long ActId);


    @Modifying
    @Query("UPDATE ActMessage SET messageState = :messageState WHERE id = :id")
    void updateMessageStateById(@Param("id") int id, @Param("messageState") byte messageState);
}