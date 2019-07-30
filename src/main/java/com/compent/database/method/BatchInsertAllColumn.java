package com.compent.database.method;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

/**
 * @author qiaoxiaozhuo 2019/03/29
 */
@Slf4j
public class BatchInsertAllColumn extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        KeyGenerator keyGenerator = new NoKeyGenerator();
        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder placeholderBuilder = new StringBuilder();

        fieldBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        placeholderBuilder
                .append("\n<foreach collection=\"collection\" item=\"item\" index=\"index\" separator=\",\">")
                .append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");

        // 表包含主键处理逻辑,如果不包含主键当普通字段处理

        String keyProperty = null;
        String keyColumn = null;

        if (StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
            /** 用户输入自定义ID */
            fieldBuilder.append(tableInfo.getKeyColumn()).append(",");
            // 正常自定义主键策略
            placeholderBuilder.append("#{item.").append(tableInfo.getKeyProperty()).append("},");
            if (tableInfo.getIdType() == IdType.AUTO) {
                /** 自增主键 */
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(tableInfo, builderAssistant,"batchInsertAllColumn", languageDriver);
                    keyProperty = tableInfo.getKeyProperty();
                    keyColumn = tableInfo.getKeyColumn();
                }
            }

        }
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        for (TableFieldInfo fieldInfo : fieldList) {
            fieldBuilder.append(fieldInfo.getColumn()).append(",");
            placeholderBuilder.append("#{item.").append(fieldInfo.getEl()).append("},");
        }
        fieldBuilder.append("\n</trim>");
        placeholderBuilder
                .append("\n</trim>")
                .append("\n</foreach>");
        String sql = String.format("<script>INSERT INTO %s %s VALUES %s</script>", tableInfo.getTableName(), fieldBuilder.toString(),
                placeholderBuilder.toString());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);

        return this.addInsertMappedStatement(mapperClass, modelClass, "batchInsertAllColumn", sqlSource, keyGenerator,
                keyProperty,keyColumn);
    }
}
