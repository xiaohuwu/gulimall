package com.ktb.gulimall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktb.common.utils.PageUtils;
import com.ktb.gulimall.oms.entity.PaymentInfoEntity;

import java.util.Map;

/**
 * ֧????Ϣ?
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-11 15:15:49
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

