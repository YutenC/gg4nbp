package gg.nbp.web.ban.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.ban.entity.Ban;

public interface BanService extends CoreService{
	//	ban部分：
	// 	登錄停權單
	// 	findAll
	
	//	會員部分：
	//	更改停權時間為null
	//	更改驗證狀態 修改或添加停權結束時間
	//	
	
	//	在送停權單的servlet當中 要包含登錄停權單和改動會員停權狀態兩種service	
	
	
	Ban createBanCase(Ban ban);
	List<Ban> findAll();
	
	
	
	
//	提交停權單
//		前端會抓：當前動作的管理員(by HTTPsession)
//		動作提交的會員ID(by ajax，會員列表或檢舉單處理、或在停權區塊有手動登打的新增停權單)
//		ajax停權理由：手key	
//	設定停權結束時間
//		ajax回傳前端登寫的停權時長
//		停權結束時間= 停權開始時間+停權時長
//		同時修改member表格中的停權結束時間
	
//	findAll
//		抓出所有banList 送到前端做列表呈現
//		剩餘停權時間：用js的setInterval來抓每秒停權結束時間和new date的相減
	
//	修改停權時間-1 傳前端
//		和提交停權單一樣抓member manager
//		ajax持續回傳勝於停權時間(一樣用setInterval方法)
//		在手動輸入之後停掉setInterval方法	
//		寫死的停權理由
//	修改停權時間-2 傳後端
//		傳後端一樣傳member manager 停權時長 理由
	
//	解除停權
//		confirm(是/否)
//		傳後端停權單ID
//		結束時間改成null

}
