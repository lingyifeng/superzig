package com.android.hz.czc.service.impl;

import com.android.hz.czc.entity.TTree;
import com.android.hz.czc.mapper.TTreeMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.ITTreeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hly
 * @since 2019-01-19
 */
@Service
public class TTreeServiceImpl extends ServiceImpl<TTreeMapper, TTree> implements ITTreeService {

    @Autowired
    private TTreeMapper treeMapper;

    @Override
    public Result findAll(Long userId) {
        TTree tree = treeMapper.selectOne(new QueryWrapper<TTree>().lambda().eq(TTree::getUserId, userId).eq(TTree::getPid, 0L));
        if (tree == null) {
            return ResultFactory.buildSuccessResult(new TTree());
        }
        //递归
        return ResultFactory.buildSuccessResult(dg(tree));
    }

    private TTree dg(TTree t) {
        List<TTree> trees = treeMapper.selectList(new QueryWrapper<TTree>().lambda().eq(TTree::getPid, t.getId()));
        if (trees.size() != 0) {
            for (TTree tree : trees) {
                dg(tree);
            }
            t.setTreeList(trees);
        }
        return t;
    }


}
