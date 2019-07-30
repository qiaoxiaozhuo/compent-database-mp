package com.compent.database.handler;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.compent.database.method.BatchInsertAllColumn;
import com.compent.database.method.SoftDeleteById;
import com.compent.database.method.SoftDeleteByIds;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author qiaoxiaozhuo 2019/03/28
 */
@Component
public class MyInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.addAll(Stream.of(
                new BatchInsertAllColumn(),
                new SoftDeleteById(),
                new SoftDeleteByIds()
        ).collect(Collectors.toList()));
        return methodList;
    }


}
