package com.ktb.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktb.common.utils.PageUtils;
import com.ktb.gulimall.product.entity.PmsSpuImagesEntity;

import java.util.Map;

/**
 * spuͼƬ
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-09 22:47:45
 */
public interface PmsSpuImagesService extends IService<PmsSpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

