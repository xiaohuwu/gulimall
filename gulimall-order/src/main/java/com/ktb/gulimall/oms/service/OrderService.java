package com.ktb.gulimall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktb.common.utils.PageUtils;
import com.ktb.gulimall.oms.entity.OrderEntity;

import java.util.Map;

/**
 * ????
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-11 15:15:49
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

