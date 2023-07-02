package gg.nbp.web.SecondHand.sale.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import gg.nbp.web.SecondHand.sale.dao.SecondhandProductImageDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SecondhandProductImageDaoImpl implements SecondhandProductImageDao {

	@PersistenceContext
	private Session session;

    @Override
    public int insert(SecondhandProductImage shpImg) {
//        Session session = getSession();
//        Transaction transaction = session.beginTransaction();
        session.persist(shpImg);
//        transaction.commit();
        return 1;
    }

    // 圖片全部刪除
    @Override
    public int deleteById(Integer imageId) {

//        Session session = getSession();
//        Transaction transaction = session.beginTransaction();
        SecondhandProductImage shpImg = session.load(SecondhandProductImage.class, imageId);
        session.remove(shpImg);

//        transaction.commit();

        return 1;


    }


//    @Override
//    public int deleteById(Integer imageId) {
//        Session session = getSession();
//        Transaction transaction = session.beginTransaction();
//
//        try {
//            SecondhandProductImage shpImg = session.load(SecondhandProductImage.class, imageId);
//            session.remove(shpImg);
//            transaction.commit();
//            return 1;
//        } catch (Exception e) {
//            transaction.rollback();
//            throw e;
//        } finally {
//            session.close();
//        }
//    }




    @Override
    public int update(SecondhandProductImage shpImg) {
//        Session session = getSession();
        session.update("SecondhandProductImage", shpImg);
        return 1;
    }

    // 選取該筆商品單一圖片
    @Override
    public SecondhandProductImage selectById(Integer imageId) {
//        Session session = getSession();
        final String sql = "SELECT * FROM Secondhand_Product_Image where image_id = :id  ";
        return session.createNativeQuery(sql, SecondhandProductImage.class).setParameter("id", imageId).getSingleResult();
    }


    // 選擇全部商品的圖片
    @Override
    public List<SecondhandProductImage> selectAll() {
//        Session session = getSession();
        final String hql = "FROM SecondhandProductImage ORDER BY imageId";
        return session.createQuery(hql, SecondhandProductImage.class).getResultList();
    }


    // 選取該筆商品全部圖片
    @Override
    public List<SecondhandProductImage> selectByProId(Integer productId) {
//        Session session = getSession();
//        Transaction transaction = session.beginTransaction();

//        final String sql = "select image_id from secondhand_product_image where product_id = ?";


        final String hql = "SELECT img FROM SecondhandProductImage img where img.productId = :productId";
        List<SecondhandProductImage> result = session.createQuery(hql, SecondhandProductImage.class)
                .setParameter("productId", productId)
                .getResultList();



//        final String hql = "SELECT imageId FROM SecondhandProductImage where productId = :productId";
//        List<SecondhandProductImage> result = session.createQuery(hql, SecondhandProductImage.class).getResultList();
//        transaction.commit();
        return result;

    }
}
