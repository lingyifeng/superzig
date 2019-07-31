package com.android.hz.czc.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.druid.util.StringUtils;
import com.android.hz.czc.BasicServiceImpl;
import com.android.hz.czc.entity.*;
import com.android.hz.czc.mapper.TPictureMapper;
import com.android.hz.czc.mapper.TResultMapper;
import com.android.hz.czc.mapper.TTreeMapper;
import com.android.hz.czc.resultvue.Result;
import com.android.hz.czc.resultvue.ResultFactory;
import com.android.hz.czc.service.ITResultService;
import com.android.hz.czc.utils.ExcelUtil;
import com.android.hz.czc.utils.FileUploadUtil;
import com.android.hz.czc.utils.MyBase64Utils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hly
 * @since 2019-01-05
 */
@Slf4j
@Service
public class TResultServiceImpl extends BasicServiceImpl<TResultMapper, TResult> implements ITResultService {

    @Autowired
    private TPictureMapper pictureMapper;
    @Autowired
    private TResultMapper resultMapper;
    @Autowired
    private TTreeMapper tTreeMapper;

    @Override
    public Result saveResult(MultipartFile file, String result, Long treeId, String resultname, Long userid) {
        HashMap<String, String> pictureResult = FileUploadUtil.fileUpload("picture", file);
        Date date = new Date();
        TransactionStatus status = beginTransaction();
        Boolean commit = false;
        try {
            TPicture tPicture = new TPicture();
            tPicture.setName(pictureResult.get("filename"));
            tPicture.setPicturePath(pictureResult.get("fileUrl"));
            tPicture.setPictureUrl(pictureResult.get("filename"));
            pictureMapper.insert(tPicture);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (result.length() > 0) {
                String[] split = result.split("_");
                if (split.length > 0) {
                    for (String s : split) {
                        TResult tResult = new TResult();
                        tResult.setCurrentDate(format.format(date));
                        tResult.setName(resultname);
                        tResult.setPictureId(tPicture.getId());
                        if (treeId != null) {
                            tResult.setTreeId(treeId);
                        }
                        tResult.setUserId(userid);
                        tResult.setResult(s);
                        resultMapper.insert(tResult);
                    }
                }
            }
            commit = true;
            return ResultFactory.buildSuccessResult("success");
        } catch (Exception e) {
            log.error("保存结果异常", e.getMessage());
            commit = false;
            return ResultFactory.buildFailResult("保存结果异常");
        } finally {
            if (commit) {
                endTransaction(status);
            } else {
                rollback(status);
            }
        }

    }

    @Override
    public Result getResultList(Integer currentPage, Integer size, Integer userid, String startTime, String endTime, Long treeId) {
        List<Long> treeIds = null;
        if (treeId != null) {
            TTree tTree = tTreeMapper.selectById(treeId);
            String structcode = tTree.getStructcode();
            treeIds = tTreeMapper.selectList(new QueryWrapper<TTree>().lambda().likeRight(TTree::getStructcode, structcode))
                    .stream().map(BaseEntity::getId).collect(Collectors.toList());
        }
        Page<Map<String, Object>> page = new Page<>(currentPage, size);
        List<Map<String, Object>> resultList = resultMapper.getResultList(page, userid, startTime, endTime, treeIds);
        return ResultFactory.buildSuccessResult(page.setRecords(resultList));
    }

    @Override
    public Result getResultExcel(HttpServletResponse response, Integer currentPage, Integer size, Integer userid, String startTime, String endTime, Long treeId) {
        Result result = null;
        Result resultList = getResultList(currentPage, size, userid, startTime, endTime, treeId);
        Page<Map<String, Object>> page = (Page<Map<String, Object>>) resultList.getData();
        List<Map<String, Object>> records = page.getRecords();
        String[] keys = new String[]{"result", "name", "all_name", "username"};
        String[] columnNames = new String[]{"结果", "结果名", "节点名称", "用户名称"};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            ExcelUtil.createWorkBook(records, keys, columnNames).write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(("导出结果.xls").getBytes(), "ISO-8859-1"));
            ServletOutputStream out = response.getOutputStream();

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }

            result = ResultFactory.buildSuccessResult("success");
        } catch (IOException e) {
            log.error("导出excel失败", e);
            result = ResultFactory.buildFailResult("导出excel失败");
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (bis != null)
                    bis.close();
            } catch (IOException e) {
                log.error("导出excel失败", e);
                result = ResultFactory.buildFailResult("导出excel失败");
            }
        }

        return result;
    }
}
