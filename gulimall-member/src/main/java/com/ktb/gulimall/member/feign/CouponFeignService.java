package com.ktb.gulimall.member.feign;

import com.ktb.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("coupon")
public interface CouponFeignService {
    @RequestMapping("coupon/coupon/member/list")
    public R membercoupons();
}
