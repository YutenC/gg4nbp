package gg.nbp.web.shop.followlist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.product.dao.ProductDao;
import com.shop.product.dao.ProductImageDao;
import com.shop.product.entity.Product;
import com.shop.product.entity.ProductImage;

import gg.nbp.web.shop.followlist.dao.FollowListDao;
import gg.nbp.web.shop.followlist.entity.FollowList;
import gg.nbp.web.shop.followlist.entity.PKFollowList;
import gg.nbp.web.shop.followlist.service.FollowListService;
import gg.nbp.web.shop.followlist.util.ResFollowList;
import jakarta.transaction.Transactional;

@Service
public class FollowListServiceImpl implements FollowListService{
	
	@Autowired
	private FollowListDao followProductDao;
	@Autowired
	private ProductDao productdao;
	@Autowired
	private ProductImageDao pdImgDao;
	
	@Transactional
	@Override
	public List<ResFollowList> getAllFollowProduct(Integer memberId) {
		try {

			List<ResFollowList> rsFlist = new ArrayList<ResFollowList>();
			
			List<FollowList> flist = followProductDao.selectByMemeberId(memberId);
			
			for (int i = 0; i < flist.size(); i++) {
				ResFollowList rf = new ResFollowList();
				Integer pdId = flist.get(i).getPkFollowList().getProductId();
				rf.setProductId(pdId);
				
				Product pd = productdao.selectById(pdId);
				rf.setProductName(pd.getProductName());
				rf.setProductPrice(pd.getPrice());
				rf.setProductAmount(pd.getAmount());
				
				List<ProductImage> pdimgs = pdImgDao.selectByProductId(pdId);
				// 往後設定取用封面圖(可能需新增表格欄位，或是固定存取為該商品照片們的第一張)
				if (!pdimgs.isEmpty()) {
					rf.setProductImgUrl(pdimgs.get(0).getImage());
				}
				rsFlist.add(rf);
			}
			
			return rsFlist;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public boolean deleteFollowList(Integer memberId, Integer productId) {
		try {
			PKFollowList pkflist = new PKFollowList(memberId, productId);
			FollowList flist = new FollowList(pkflist);
			followProductDao.deleteByCompositePK(flist);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean addFollowList(Integer memberId, Integer productId) {
		try {
			FollowList fl = new FollowList();
			PKFollowList pkfl = new PKFollowList(memberId, productId);
			fl.setPkFollowList(pkfl);
			followProductDao.insert(fl);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
}
