/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysLogDao;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>().or(StringUtils.isNotBlank(key), wrapper -> {
                wrapper.like("username",key);
            }).or(StringUtils.isNotBlank(key), wrapper -> {
                wrapper.like("operation",key);
            })
        );

        QueryWrapper<SysLogEntity> wrapper = new QueryWrapper<>();
        wrapper.or(i -> i.eq("username", "李白").ne("operation", "活着"));
        List<SysLogEntity> sysLogEntities = this.getBaseMapper().selectList(wrapper);
        new Gson().toJson(sysLogEntities);

        return new PageUtils(page);
    }
}
