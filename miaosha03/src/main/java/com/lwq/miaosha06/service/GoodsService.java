package com.lwq.miaosha06.service;

import com.lwq.miaosha06.dao.GoodsDao;
import com.lwq.miaosha06.model.MiaoshaGoods;
import com.lwq.miaosha06.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 16:14
 * @Version 1.0
 * @Describe
 */
@Service
public class GoodsService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }


    public int reduceStock(GoodsVo goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        g.setStockCount(goods.getStockCount()-1);
        return goodsDao.reduceStock(g);
    }
}
