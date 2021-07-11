package com.ktb.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktb.common.utils.PageUtils;
import com.ktb.gulimall.product.entity.PmsSpuCommentEntity;

import java.util.Map;

/**
 * ??Ʒ???
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-09 22:47:45
 */
public interface PmsSpuCommentService extends IService<PmsSpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

