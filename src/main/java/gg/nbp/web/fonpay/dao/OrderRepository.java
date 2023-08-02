package gg.nbp.web.fonpay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gg.nbp.web.fonpay.entity.Order;

public interface OrderRepository  extends JpaRepository<Order, Integer>{

}
