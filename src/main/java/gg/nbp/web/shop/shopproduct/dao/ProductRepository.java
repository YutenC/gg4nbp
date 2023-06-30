package gg.nbp.web.shop.shopproduct.dao;

import gg.nbp.web.Act.model.Act;
import gg.nbp.web.shop.shopproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


//    @Modifying
//    @Query("from Product where type = :type and amount = :amount and brand= :brand")
//    List<Product> selectBySpecified(@Param("type") Integer type, @Param("amount") Integer amount, @Param("brand") String brand);

    @Query("from Product where buyTimes = :buyTimes and amount = :amount and brand= :brand")
    List<Product> selectBySpecified(@Param("buyTimes") Integer buyTimes, @Param("amount") Integer amount, @Param("brand") String brand);
}
