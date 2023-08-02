package gg.nbp.web.fonpay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gg.nbp.web.fonpay.entity.PaymentDetail;

public interface PaymentDetailRepositry extends JpaRepository<PaymentDetail, Integer>{

}
