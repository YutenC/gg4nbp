package gg.nbp.web.Act.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gg.nbp.web.Act.model.Act;

public interface ActRepository extends JpaRepository<Act, Integer> {
}