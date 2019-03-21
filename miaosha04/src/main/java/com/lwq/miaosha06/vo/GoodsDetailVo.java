package com.lwq.miaosha06.vo;

import com.lwq.miaosha06.model.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Lwq
 * @Date: 2019/3/20 13:39
 * @Version 1.0
 * @Describe
 */
@Getter
@Setter
@ToString
public class GoodsDetailVo {

    private int miaoshaStauts = 0;
    int remainSeconds = 0;
    private GoodsVo goods ;
    private MiaoshaUser user;
}
