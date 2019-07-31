package com.android.hz.czc.mapper;

import com.android.hz.czc.entity.TResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
public interface TResultMapper extends BaseMapper<TResult> {

    List<Map<String,Object>> getResultList(IPage<Map<String,Object>> page,@Param("userid") Integer userid, @Param("startTime") String startTime,
                                           @Param("endTime") String endTime, @Param("treeIds") List<Long> treeIds);

}
