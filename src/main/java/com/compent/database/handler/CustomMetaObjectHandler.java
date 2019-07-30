package com.compent.database.handler;

import cn.hutool.core.date.SystemClock;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author qiaoxiaozhuo
 */
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("version", 1, metaObject);
        // create_datetime
        this.setInsertFieldValByName("createDatetime", new Date(SystemClock.now()), metaObject);

        this.setInsertFieldValByName("delFlag", false, metaObject);
        this.setInsertFieldValByName("updateDatetime", new Date(SystemClock.now()), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updateDatetime", new Date(SystemClock.now()), metaObject);

    }


}
