package com.ktb.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ktb.common.utils.PageUtils;
import com.ktb.gulimall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * ???ֱ仯??ʷ??¼
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-11 15:05:39
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

