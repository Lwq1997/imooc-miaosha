package com.lwq.miaosha06.vo;

import com.lwq.miaosha06.model.Goods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 16:15
 * @Version 1.0
 * @Describe
 */
@Getter
@Setter
@ToString
public class GoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
