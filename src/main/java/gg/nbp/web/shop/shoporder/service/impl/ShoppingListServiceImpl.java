package gg.nbp.web.shop.shoporder.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.nbp.web.shop.shoporder.dao.JedisShoppingListDao;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shoporder.service.ShoppingListService;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.dao.ProductImageDao;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import jakarta.transaction.Transactional;

@Service
public class ShoppingListServiceImpl implements ShoppingListService{

	@Autowired
	private JedisShoppingListDao jediShdao;
	@Autowired
	private ProductDao pdao;
	@Autowired
	private ProductService pService;
	
	@Transactional
	@Override
	public List<TransOrderProduct> getAllShoppingList(Integer memberId) {
		try {
			List<TransOrderProduct> result = new ArrayList<>();
			List<ShoppingList> listOfsplist = jediShdao.selectByMemberId(memberId);
			for (ShoppingList splist : listOfsplist) {
				Product pd = pdao.selectById(splist.getPkShoppingList().getProductId());
				if (pd == null) {
					continue;
				}
				TransOrderProduct trspd = new TransOrderProduct();
				trspd.setProductId(pd.getId());
				if (pService.getProductIndexImg(pd.getId()) == null) {
					trspd.setProductImgUrl(null);
				} else {
					trspd.setProductImgUrl(pService.getProductIndexImg(pd.getId()).getImage());
				}
				trspd.setProductName(pd.getProductName());
				trspd.setBrand(pd.getBrand());
				trspd.setBuyAmount(splist.getQuantity());
				trspd.setPrice(pd.getPrice());
				trspd.setStockAmount(pd.getAmount());
				trspd.setChecked(true);
				result.add(trspd);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Transactional
	@Override
	public boolean addOneShoppingList(TransOrderProduct trpd, Integer memberId) {
		if (memberId == null) {
			return false;
		}
		try {
			ShoppingList slist = new ShoppingList();
			PKShoppingList pkslist = new PKShoppingList();
			pkslist.setMemmberId(memberId);
			pkslist.setProductId(trpd.getProductId());
			slist.setPkShoppingList(pkslist);
			slist.setQuantity(trpd.getBuyAmount());
			jediShdao.update(slist);
			jediShdao.renewExpireDate(memberId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean removeItem(List<ShoppingList> spLists) {
		try {
			for (ShoppingList splist : spLists) {
				jediShdao.delete(splist);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
