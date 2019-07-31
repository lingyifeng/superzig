package com.android.hz.czc.service;

import com.android.hz.czc.entity.TResult;
import com.android.hz.czc.resultvue.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
public interface ITResultService extends IService<TResult> {

    Result saveResult(MultipartFile file,String result,Long treeId,String resultname,Long userid);

    Result getResultList(Integer currentPage,Integer size,Integer userid,String startTime,String endTime,Long treeId);
    Result getResultExcel(HttpServletResponse response, Integer currentPage, Integer size, Integer userid, String startTime, String endTime, Long treeId);

}
