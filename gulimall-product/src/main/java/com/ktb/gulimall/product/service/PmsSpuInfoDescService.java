package com.ktb.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktb.common.utils.PageUtils;
import com.ktb.gulimall.product.entity.PmsSpuInfoDescEntity;

import java.util.Map;

/**
 * spu??Ϣ???
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-09 22:47:45
 */
public interface PmsSpuInfoDescService extends IService<PmsSpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

