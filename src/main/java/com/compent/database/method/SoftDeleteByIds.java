package com.compent.database.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author qiaoxiaozhuo 2019/03/29
 */
public class SoftDeleteByIds extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String idStr = tableInfo.getKeyProperty();
        StringBuilder ids = new StringBuilder();
        ids.append("\n<foreach item=\"item\" index=\"index\" collection=\"coll\" separator=\",\">");
        ids.append("#{item}");
        ids.append("\n</foreach>");
        idStr = ids.toString();
        String sql = String.format("<script>UPDATE %s SET del_flag = 1 WHERE %s IN (%s)</script>", tableInfo.getTableName(), tableInfo.getKeyColumn(), idStr);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, "softDeleteByIds", sqlSource);
    }
}
