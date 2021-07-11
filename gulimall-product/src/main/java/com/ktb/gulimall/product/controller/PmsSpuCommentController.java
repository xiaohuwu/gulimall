package com.ktb.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.gulimall.product.entity.PmsSpuCommentEntity;
import com.ktb.gulimall.product.service.PmsSpuCommentService;
import com.ktb.common.utils.PageUtils;
import com.ktb.common.utils.R;



/**
 * ??Ʒ???
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-09 22:47:45
 */
@RestController
@RequestMapping("product/pmsspucomment")
public class PmsSpuCommentController {
    @Autowired
    private PmsSpuCommentService pmsSpuCommentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsSpuCommentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PmsSpuCommentEntity pmsSpuComment = pmsSpuCommentService.getById(id);

        return R.ok().put("pmsSpuComment", pmsSpuComment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PmsSpuCommentEntity pmsSpuComment){
		pmsSpuCommentService.save(pmsSpuComment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PmsSpuCommentEntity pmsSpuComment){
		pmsSpuCommentService.updateById(pmsSpuComment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		pmsSpuCommentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
