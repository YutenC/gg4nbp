package gg.nbp.web.SecondHand.sale.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.SecondHand.sale.dao.SecondhandProductDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;

@Transactional
@Service
public class SecondhandProductServiceImpl implements SecondhandProductService {

	@Autowired
    private SecondhandProductDao spdao ;


    @Override
    public SecondhandProduct addshp(SecondhandProduct secondhandproduct) {
        if ((secondhandproduct.getName().trim().isEmpty())) {
            secondhandproduct.setMessage("請輸入二手商品名稱");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        if ((secondhandproduct.getType().isEmpty())) {
            secondhandproduct.setMessage("請選擇二手商品欄位");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        if (secondhandproduct.getPrice() == null) {
            secondhandproduct.setMessage("請輸入二手商品價格");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        } else if (secondhandproduct.getPrice() <= 0) {
            secondhandproduct.setMessage("價格輸入錯誤");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        if ((secondhandproduct.getContent().trim().isEmpty())) {
            secondhandproduct.setMessage("請輸入二手商品內容");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        final int resultCount = spdao.insert(secondhandproduct);
        if (resultCount < 1) {
            secondhandproduct.setMessage("商品上架失敗");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        } else {
            secondhandproduct.setMessage("商品上架成功");
            secondhandproduct.setSuccessful(true);
            return secondhandproduct;
        }
    }


//    @Override
//    public SecondhandProduct editshp(SecondhandProduct secondhandproduct) {
//        final SecondhandProduct oshproduct = spdao.selectById(secondhandproduct.getProductId());
//        secondhandproduct.setName(oshproduct.getName());
//        secondhandproduct.setType(oshproduct.getType());
//        secondhandproduct.setPrice(oshproduct.getPrice());
//        secondhandproduct.setContent(oshproduct.getContent());
//        secondhandproduct.setLaunchTime(oshproduct.getLaunchTime());
//
//        final int resultCount = spdao.update(secondhandproduct);
//        secondhandproduct.setSuccessful(resultCount > 0);
//        secondhandproduct.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
//        return secondhandproduct;
//    }


    @Override
    public SecondhandProduct editshp(SecondhandProduct secondhandproduct) {
        final SecondhandProduct oshproduct = spdao.selectById(secondhandproduct.getProductId());

        if (secondhandproduct.getName() != null) {
            oshproduct.setName(secondhandproduct.getName());
        }
        if (secondhandproduct.getType() != null) {
            oshproduct.setType(secondhandproduct.getType());
        }
        if (secondhandproduct.getPrice() != null) {
            oshproduct.setPrice(secondhandproduct.getPrice());
        }
        if (secondhandproduct.getContent() != null) {
            oshproduct.setContent(secondhandproduct.getContent());
        }



//        System.out.println("有沒有圖傳進來");
//        System.out.println(memberData.getMemberPic4json());
//        System.out.println(memberData.getMemberPic4json().equals("有傳圖進來"));
//        if (memberData.getMemberPic4json().equals("有傳圖進來")) {
//            oMember.setMemberPic(memberData.getMemberPic());
//            oMember.setMemberPic4json("有圖");
//        } else {
//            oMember.setMemberPic(oMember.getMemberPic());
//            oMember.setMemberPic4json("用舊圖");
//        }
//        if (memberData.getMemberCard() != null) {
//            oMember.setMemberCard(memberData.getMemberCard());
//        }

        final int resultCount = spdao.update(oshproduct);
        oshproduct.setSuccessful(resultCount > 0);
        oshproduct.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
        return oshproduct;
    }





    @Override
    public boolean delete(Integer productID) {
        final int resultCount = spdao.deleteById(productID);
        return true;
    }

    @Override
    public SecondhandProduct selectOne(Integer productID) {
        return spdao.selectById(productID);
    }

    @Override
    public List<SecondhandProduct> searchAll() {

        return spdao.selectAll();
    }



}// 關鍵字搜尋?
