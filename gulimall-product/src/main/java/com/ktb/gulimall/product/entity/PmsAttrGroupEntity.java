package com.ktb.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ???Է??
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-07-09 22:47:45
 */
@Data
@TableName("pms_attr_group")
public class PmsAttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ????id
	 */
	@TableId
	private Long attrGroupId;
	/**
	 * ???
	 */
	private String attrGroupName;
	/**
	 * ???
	 */
	private Integer sort;
	/**
	 * ???
	 */
	private String descript;
	/**
	 * ??ͼ?
	 */
	private String icon;
	/**
	 * ????????id
	 */
	private Long catelogId;

}
