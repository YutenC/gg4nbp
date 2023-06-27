package gg.nbp.web.Act.dao;


import gg.nbp.web.Act.model.Act;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActRepository extends JpaRepository<Act, Integer> {
}