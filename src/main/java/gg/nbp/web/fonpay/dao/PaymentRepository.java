package gg.nbp.web.fonpay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gg.nbp.web.fonpay.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
