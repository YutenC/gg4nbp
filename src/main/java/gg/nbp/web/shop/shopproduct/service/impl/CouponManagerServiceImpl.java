package gg.nbp.web.shop.shopproduct.service.impl;


import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundFactory;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundHandler;
import gg.nbp.web.shop.shopproduct.common.schedulertask.SchedulerEntity;
import gg.nbp.web.shop.shopproduct.common.schedulertask.SchedulerFactory;
import gg.nbp.web.shop.shopproduct.common.schedulertask.SchedulerTasks;
import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.entity.CouponActivity;
import gg.nbp.web.shop.shopproduct.pojo.CouponMember;
import gg.nbp.web.shop.shopproduct.pojo.DaoConditionSelect;
import gg.nbp.web.shop.shopproduct.pojo.ResponseMsg;
import gg.nbp.web.shop.shopproduct.redisdao.CouponActivityRedisDao;
import gg.nbp.web.shop.shopproduct.service.CouponManagerService;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import gg.nbp.web.shop.shopproduct.util.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

@Service
@Transactional
public class CouponManagerServiceImpl implements CouponManagerService {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponActivityRedisDao couponActivityRedisDao;

    @Autowired
    CouponDao couponDao;


    @Autowired
    MemberService memberService;
    public CouponManagerServiceImpl() {
    }

    @Override
//    @Transactional
    public void generateCouponActivity() {
        for (int i = 0; i < 10; i++) {
            CouponActivity couponActivity = new CouponActivity("Activity " + i, "MF");
            couponActivity.setCoupon(genFixCouponData(i));//genCouponData()
            addCouponActivity(couponActivity);
            setAutoCouponState(couponActivity.getCoupon());
        }
    }

    @Override
    public void addCouponActivity(CouponActivity couponActivity) {
        Coupon coupon = couponActivity.getCoupon();
        coupon.setState(CouponState.unPublish.getValue());
        couponService.addCoupon(coupon);

        RedisContent redisService = new RedisContent() {
            @Override
            public int run() {
                Coupon coupon_=couponService.getCouponByDiscountCode(coupon.getDiscountCode());
                couponActivity.setCoupon(coupon_);
                couponActivityRedisDao.addCouponActivity(couponActivity);
                return 0;
            }
        };
        RedisFactory.getRedisServiceInstance().registerRedisService(redisService);
    }

    @Override
//    @Transactional
    public List<CouponActivity> getAllCouponActivity() throws RuntimeException {
        return  couponActivityRedisDao.getAllCouponActivity();
    }

    @Override
    public List<CouponActivity> getCouponActivityByCondition(DaoConditionSelect daoConditionSelect) throws RuntimeException {
        List<Coupon> coupons= couponDao.selectByCondition(daoConditionSelect);
        List<CouponActivity> couponActivities= couponActivityRedisDao.getCouponActivitiesByCoupons(coupons);
        return couponActivities;
    }



    @Override
//    @Transactional
    public CouponActivity getCouponActivityByCouponId(Integer couponId) {
        return couponActivityRedisDao.getCouponActivityByCouponId(couponId);
    }

    @Override
    public List<CouponActivity> searchCouponActivity() {
        return null;
    }

    @Override
//    @Transactional
    public void updateCouponActivity(CouponActivity couponActivity) {
        Coupon coupon = couponActivity.getCoupon();
        couponDao.update(coupon);

        RedisContent redisService = new RedisContent() {
            @Override
            public int run() {
                Coupon coupon_=couponService.getCouponByDiscountCode(coupon.getDiscountCode());
                couponActivity.setCoupon(coupon_);
                couponActivityRedisDao.updateCouponActivity(couponActivity);
                return 0;
            }
        };
        RedisFactory.getRedisServiceInstance().registerRedisService(redisService);
    }

    @Override
    public boolean deleteCoupon(Integer couponId) {
        if(couponService.getCouponById(couponId)!=null){
            couponService.deleteCoupon(couponId);
        }

        RedisContent redisService = new RedisContent() {
            @Override
            public int run() {
                couponActivityRedisDao.deleteCouponActivity(couponId);
                return 0;
            }
        };
        RedisFactory.getRedisServiceInstance().registerRedisService(redisService);

        return true;
    }

    @Override
    public List<CouponMember> getCouponMemberInfo() {
        List<Member> members= memberService.findAll();

        List<CouponMember> couponMembers=new ArrayList<>();

        for(Member member : members){
            couponMembers.add(new CouponMember(member.getAccount(),member.getEmail()));
        }

        return couponMembers;
    }

    @Override
//    @Transactional
    public void publishCouponActivity(Integer couponId) {
        Coupon coupon= couponService.getCouponById(couponId);
        CouponActivity couponActivity=couponActivityRedisDao.getCouponActivityByCouponId(couponId);
        coupon.setState(CouponState.publish.getValue());
        couponActivity.setCoupon(coupon);
        updateCouponActivity(couponActivity);

        setAutoCouponState(coupon);
    }

    @Override
    public ResponseMsg sendEmail(int action, List<CouponMember> couponMembers) {
        switch (action){
            case 0://立即發送
                BackgroundHandler backgroundHandler = BackgroundFactory.getBackgroundHandler("shopProductBackground");

                Callable<String> callable=new Callable() {
                    @Override
                    public Object call() throws Exception {
                        List<CouponMember> couponMembers_=couponMembers;
                        for(int i=0;i<couponMembers_.size();i++){
                            System.out.println("send email"+couponMembers_.get(i).getEmail());
                        }

                        Thread.sleep(5000);

                        return "ok";
                    }
                };

                backgroundHandler.addTask("sendEmail",callable);

                return new ResponseMsg("longTime","sendEmail","");

            case 1://指定時間

                Date time=null;
                for (CouponMember couponMember:couponMembers ) {
                    if(couponMember.isCheck()){
                        time=couponMember.getSendEmailTime();
                        break;
                    }
                }

                if(time==null){
                    return new ResponseMsg("ok","no need send","");
                }

                TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {
                        List<CouponMember> couponMembers_=couponMembers;
                        for(int i=0;i<couponMembers_.size();i++){
                            if(couponMembers_.get(i).isCheck()){
                                System.out.println("send email"+couponMembers_.get(i).getEmail());
                            }
                        }

                        cancel();
                        SchedulerTasks schedulerTasks= SchedulerFactory.getSchedulerTasks("sendEmail");
                        schedulerTasks.removeTimerTask( "coupon"+"sendEmail");
                        schedulerTasks.clear();
                    }
                };



                SchedulerTasks schedulerTasks= SchedulerFactory.getSchedulerTasks("sendEmail");
                schedulerTasks.addTimerTask("coupon"+"sendEmail",new SchedulerEntity(time,timerTask));


                return new ResponseMsg("ok","","");

        }
        return new ResponseMsg("error","","");
    }


    public void setAutoCouponState(Coupon coupon){
        List<Coupon> coupons= couponDao.selectAll();

        SchedulerTasks schedulerTasks=  SchedulerFactory.getSchedulerTasks("autoCouponState");

        TimerTask timerTask= new TimerTask() {
            @Override
//            @Transactional
            public void run() {
                Coupon coupon_=coupon;
                CouponActivity couponActivity=couponActivityRedisDao.getCouponActivityByCouponId(coupon_.getId());
                coupon_.setState(CouponState.inValid.getValue());
                couponActivity.setCoupon(coupon_);
                updateCouponActivity(couponActivity);
                System.out.println("setAutoCouponState: "+coupon_.getId()+"failed");

                cancel();
                SchedulerTasks schedulerTasks= SchedulerFactory.getSchedulerTasks("autoCouponState");
                schedulerTasks.removeTimerTask( "coupon"+coupon.getId());
                schedulerTasks.clear();
            }
        };

        SchedulerEntity schedulerEntity=new SchedulerEntity(coupon.getDeadline(),timerTask);
        schedulerTasks.addTimerTask("coupon"+coupon.getId(),schedulerEntity);
    }

    private Coupon genFixCouponData(Integer index){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(cal.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        String date = simpleDateFormat.format(cal.getTime());
        java.sql.Date deadline = java.sql.Date.valueOf(date);

        cal.setTimeInMillis(cal.getTimeInMillis() - 10 * 24 * 60 * 60 * 1000);
        date = simpleDateFormat.format(cal.getTime());
        java.sql.Date failedDeadline=java.sql.Date.valueOf(date);
        Coupon coupon;
        switch (index){
            case 0:
                coupon = new Coupon(100, 1000, failedDeadline, "Qb12BJZO22",0);
                break;
            case 1:
                coupon = new Coupon(100, 2000, failedDeadline, "Qb34FFFO11",0);
                break;
            case 2:
                coupon = new Coupon(50, 500, deadline, "Qb55ASZ678",0);
                break;
            case 3:
                coupon = new Coupon(150, 2000, failedDeadline, "Qb56BGFO90",0);
                break;
            case 4:
                coupon = new Coupon(100, 500, deadline, "Qb77XCVO22",0);
                break;
            case 5:
                coupon = new Coupon(100, 1000, deadline, "Qb345XZO67",0);
                break;
            case 6:
                coupon = new Coupon(200, 1500, deadline, "Qb875IRO93",0);
                break;
            case 7:
                coupon = new Coupon(300, 3000, deadline, "Qb345VBO67",0);
                break;
            case 8:
                coupon = new Coupon(100, 600, deadline, "Qb245DYO88",0);
                break;
            default:
                coupon = new Coupon(100, 1000, deadline, "Qb345X3367",0);
                break;

        }
        return coupon;

    }

    private Coupon genCouponData() {
        Integer discount = ((int) (Math.random() * 50 + 51));
        Integer condition_price = ((int) (Math.random() * 500 + 501));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(cal.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        String date = simpleDateFormat.format(cal.getTime());
        java.sql.Date deadline = java.sql.Date.valueOf(date);

        String discountCode = genCouponCode();
        System.out.println("discountCode: " + discountCode);
        Coupon coupon = new Coupon(discount, condition_price, deadline, discountCode,0);

        return coupon;
    }

    private String genCouponCode() {
        final int RANDOM_NUM = 8;
        int rangeNum = 62;
        char[] randomChar = new char[RANDOM_NUM];
        String str = "";
        for (int i = 0; i < RANDOM_NUM; i++) {
            int random = (int) (Math.random() * rangeNum);
            if (random < 10) {
                randomChar[i] = (char) ('0' + random);
            } else if (random < 36) {
                randomChar[i] = (char) ('A' + random - 10);
            } else if (random >= 36) {
                randomChar[i] = (char) ('a' + random - 36);
            } else {

            }
            str += randomChar[i];
        }
        return str;
    }


}
