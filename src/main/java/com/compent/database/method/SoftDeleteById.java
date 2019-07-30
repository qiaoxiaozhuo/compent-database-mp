package com.compent.database.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author qiaoxiaozhuo 2019/03/29
 */
public class SoftDeleteById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

        String method = "softDeleteById";
        String idStr = tableInfo.getKeyProperty();
        /* mapper 接口方法名一致 */
        String sql = String.format("<script>UPDATE %s SET del_flag = 1 WHERE %s=#{%s}</script>", tableInfo.getTableName(), tableInfo.getKeyColumn(), idStr);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, method, sqlSource);
    }
}
