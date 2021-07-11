package com.ktb.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktb.common.utils.PageUtils;
import com.ktb.gulimall.product.entity.PmsSkuInfoEntity;

import java.util.Map;

/**
 * sku??Ϣ
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-09 22:47:45
 */
public interface PmsSkuInfoService extends IService<PmsSkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

