package gg.nbp.web.fonpay.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gg.nbp.web.fonpay.entity.Order;
import gg.nbp.web.fonpay.pojo.Body;
import gg.nbp.web.fonpay.service.FonPayService;

@Service
public class FonPayServiceImpl implements FonPayService {

	@Override
	public Body paymentCreateOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body paymentCancelOrder(String TransactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body paymentRefundOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body paymentQueryOrder(String TransactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body paymentRefundQueryOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body addPayment(Integer key,String secret,String merchantCode) {
		String url = "https://test-api.fonpay.tw/api/payment/paymentAccountSummary";
		 HttpHeaders headers = new HttpHeaders();
		 headers.set("X-ignore", "true");
		 
		 /* 這個欄位不確定要填入什麼，待釐清來源之後考慮要不要加入新參數*/
		 headers.set("User-Agent", "aapp123");
		 
		 headers.set("key", key.toString());
		 headers.set("secret", secret);
		 headers.set("merchantCode", merchantCode);
		 HttpEntity<String> entity = new HttpEntity<>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		Body body = restTemplate.postForObject(url,entity, Body.class);
		
		/******************************************
		 * 		施工告示牌 (施工中)
		 * **************************************
		 * 呼叫DAO檢查資料庫有沒有這個金流
		 * 有的話，丟出例外或false
		 * 沒有的話則把金流資料塞入body
		 * */
		return body;
	}

}
