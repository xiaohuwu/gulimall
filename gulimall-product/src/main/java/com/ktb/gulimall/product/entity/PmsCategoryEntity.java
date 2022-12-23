package com.ktb.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ??Ʒ???????
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-09 22:47:45
 */
@Data
@TableName("pms_category")
public class PmsCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ????id
     */
    @TableId
    private Long catId;
    /**
     * ?????
     */
    private String name;
    /**
     * ??????id
     */
    private Long parentCid;
    /**
     * ?㼶
     */
    private Integer catLevel;
    /**
     * ?Ƿ???ʾ[0-????ʾ??1??ʾ]
     */
    @TableLogic(value = "1",delval = "0")
    private Integer showStatus;
    /**
     * ???
     */
    private Integer sort;
    /**
     * ͼ????ַ
     */
    private String icon;
    /**
     * ??????λ
     */
    private String productUnit;
    /**
     * ??Ʒ?
     */
    private Integer productCount;
    @TableField(exist = false)
    private List<PmsCategoryEntity> childCategoryEntity;

    public void setChildCategoryEntity(List<PmsCategoryEntity> childCategoryEntity) {

        this.childCategoryEntity = childCategoryEntity;
    }

    public List<PmsCategoryEntity> getChildCategoryEntity() {
        return childCategoryEntity;
    }
}
