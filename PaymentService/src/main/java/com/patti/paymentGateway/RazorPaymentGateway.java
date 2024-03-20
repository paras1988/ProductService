package com.patti.paymentGateway;

import com.razorpay.PaymentLink;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RazorPaymentGateway implements PaymentGateway{
    @Override
    public String generatePaymentLink(String orderId, Long amount) {

        RazorpayClient razorpay = null;
        try {
            razorpay = new RazorpayClient("rzp_test_bkur6wBqbag2m8", "mqkogWaRud90MRcLqxnv5SMB");
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",true);
        paymentLinkRequest.put("first_min_partial_amount",100);

            Date date1 = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.add(Calendar.MINUTE, 20); //adds 20 minute to the calendar's date

        paymentLinkRequest.put("expire_by",c.getTime().getTime());
        paymentLinkRequest.put("reference_id",orderId);
        paymentLinkRequest.put("description","Test payment for order no #23456");

        JSONObject customer = new JSONObject();
        customer.put("name","+919130006036");
        customer.put("contact","Paras agarwal");
        customer.put("email","paras.grwl@gmail.com");

        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Jeevan Bima");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","http://google.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
        return payment.toString();
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }


    }


    class Solution {
        public long maximumSubarraySum(int[] nums, int k) {
            int count = 0;
            Map<Integer,Integer> map = new HashMap<>();
            int max=0;

            for(int i=0;i<k;i++){
                count += nums[i];
                map.put(nums[i],map.getOrDefault(nums[i]+1,1));
            }
            boolean ignore=fun(map,0,k,nums);
            if(!ignore){
                max = count;
            }
            for(int i=k;i<nums.length;i++){
                count += nums[i];
                count -= nums[i-k];
                map.put(nums[i],map.getOrDefault(nums[i]+1,1));
                map.put(nums[i-k],map.getOrDefault(nums[i-k]-1,0));

                boolean ignore2=fun(map,0,k,nums);
                if(!ignore2){
                    max = Math.max(max,count);
                }
            }
            return max;
        }

        boolean fun(Map<Integer,Integer> map, int s, int e, int[] nums){
            for(int i=s;i<e;i++){
                if(map.get(i)>1){
                    return true;
                }
            }
            return false;
        }
    }
}
