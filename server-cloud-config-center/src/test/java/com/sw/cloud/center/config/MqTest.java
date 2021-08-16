package com.sw.cloud.center.config;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Date;
import java.util.List;

/**
 * @className: Mqtest
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/7/7
 **/
public class MqTest {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("MXSAProd");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        producer.setSendMsgTimeout(50000);
        OrderInfoCustVo orderInfoCustVo=new OrderInfoCustVo();
        orderInfoCustVo.setShopId(1000L);
        orderInfoCustVo.setOrderId(2000L);
        System.out.println(producer.getProducerGroup());
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("ORDER_MXBC_TOPIC" /* Topic */,
                    "OrderShopStats" /* Tag */, JSON.toJSONString(orderInfoCustVo).getBytes()
                 /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);

        //Shut down once the producer instance is not longer in use.
        producer.shutdown();

        // Specify name server addresses.

    }


}
class  OrderInfoCustVo{

    private Long orderId;
    private Long orderCode;
    private Long shopId;
    private String shopCode;
    private String shopName;
    private Integer productCount;
    private Integer originPrice;
    private Integer discountPrice;
    private Integer marketingDiscount;
    private Integer couponDiscount;
    private Integer price;
    private Date orderTime;
    private Integer payType;
    private Date payTime;
    private Integer orderChannel;
    private Integer appType;
    private Integer miniProgType;
    private Integer orderType;
    private Integer takeType;
    private Date takeTime;
    private Integer takeNo;
    private Integer printStatus;
    private Integer orderStatus;
    private Date finishTime;
    private Integer cancelType;
    private Date cancelTime;
    private Long couponId;
    private String couponCode;
    private String couponName;
    private Long marketingId;
    private String marketingName;
    private Integer marketingType;
    private Integer isRefund;
    private Integer refundStatus;
    private Integer refundFailType;
    private Date refundTime;
    private Integer refundReasonType;
    private Integer refundReason;
    private String refundRemark;
    private Long refundOperatorId;
    private String refundOperatorName;
    private Date refundMerchantTime;
    private Date refundPayTime;
    private String remark;

    private Long timestamp;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Long orderCode) {
        this.orderCode = orderCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getMarketingDiscount() {
        return marketingDiscount;
    }

    public void setMarketingDiscount(Integer marketingDiscount) {
        this.marketingDiscount = marketingDiscount;
    }

    public Integer getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(Integer couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getMiniProgType() {
        return miniProgType;
    }

    public void setMiniProgType(Integer miniProgType) {
        this.miniProgType = miniProgType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getTakeType() {
        return takeType;
    }

    public void setTakeType(Integer takeType) {
        this.takeType = takeType;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Integer getTakeNo() {
        return takeNo;
    }

    public void setTakeNo(Integer takeNo) {
        this.takeNo = takeNo;
    }

    public Integer getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(Integer printStatus) {
        this.printStatus = printStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getCancelType() {
        return cancelType;
    }

    public void setCancelType(Integer cancelType) {
        this.cancelType = cancelType;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

    public Integer getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(Integer marketingType) {
        this.marketingType = marketingType;
    }

    public Integer getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Integer isRefund) {
        this.isRefund = isRefund;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getRefundFailType() {
        return refundFailType;
    }

    public void setRefundFailType(Integer refundFailType) {
        this.refundFailType = refundFailType;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getRefundReasonType() {
        return refundReasonType;
    }

    public void setRefundReasonType(Integer refundReasonType) {
        this.refundReasonType = refundReasonType;
    }

    public Integer getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(Integer refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public Long getRefundOperatorId() {
        return refundOperatorId;
    }

    public void setRefundOperatorId(Long refundOperatorId) {
        this.refundOperatorId = refundOperatorId;
    }

    public String getRefundOperatorName() {
        return refundOperatorName;
    }

    public void setRefundOperatorName(String refundOperatorName) {
        this.refundOperatorName = refundOperatorName;
    }

    public Date getRefundMerchantTime() {
        return refundMerchantTime;
    }

    public void setRefundMerchantTime(Date refundMerchantTime) {
        this.refundMerchantTime = refundMerchantTime;
    }

    public Date getRefundPayTime() {
        return refundPayTime;
    }

    public void setRefundPayTime(Date refundPayTime) {
        this.refundPayTime = refundPayTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
