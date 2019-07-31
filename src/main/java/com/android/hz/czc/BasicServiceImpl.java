package com.android.hz.czc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by lingzong on 2019/7/30.
 */
@Slf4j
public class BasicServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> {
    @Autowired
    protected DataSourceTransactionManager txManager;

    /**
     * 开启事务，新建事务机制
     */
    protected TransactionStatus beginTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(java.util.UUID.randomUUID().toString());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); // 事物隔离级别，开启新事务

        log.info("DefaultTransactionDefinition: " + def.getName());

        return txManager.getTransaction(def);
    }

    /**
     * 结束事务，并提交操作
     * @param status  事务保存点
     */
    protected void endTransaction(TransactionStatus status) {
        txManager.commit(status);
    }
    /**
     * 回滚
     * @param status 	存储点
     */
    protected void rollback(TransactionStatus status) {
        txManager.rollback(status);
    }
}
