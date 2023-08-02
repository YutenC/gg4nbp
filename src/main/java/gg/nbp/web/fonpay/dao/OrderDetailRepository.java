package gg.nbp.web.fonpay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gg.nbp.web.fonpay.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
