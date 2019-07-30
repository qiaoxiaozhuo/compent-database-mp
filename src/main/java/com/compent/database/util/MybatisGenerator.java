package com.compent.database.util;



import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author qiaoxiaozhuo 2019/03/28
 */
public class MybatisGenerator {

    public static void main(String[] args) {
        MybatisGeneratorObj mybatisGeneratorObj = new MybatisGeneratorObj();
        mybatisGeneratorObj.setOutputDir("./target/generator".replace("/", File.separator));
        mybatisGeneratorObj.setDataSourceUserName("root");
        mybatisGeneratorObj.setDataSourcePassword("123456");
        mybatisGeneratorObj.setDataSourceUrl("jdbc:mysql://localhost:3306/wechat-online");
        mybatisGeneratorObj.setTablePrefix(new String[]{"t_"});
        mybatisGeneratorObj.setPackageName("com.qiaoxiaozhuo");
        mybatisGeneratorObj.setIncludeTables(new String[]{
                "t_lottery",
        });
        MybatisGenerator.generate(mybatisGeneratorObj);
    }

    public static void generate(MybatisGeneratorObj mybatisGeneratorObj) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUrl(mybatisGeneratorObj.getDataSourceUrl())
                .setUsername(mybatisGeneratorObj.getDataSourceUserName())
                .setPassword(mybatisGeneratorObj.getDataSourcePassword())
                .setDriverName("com.mysql.jdbc.Driver");

        StrategyConfig strategyConfig = new StrategyConfig()
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setTablePrefix(mybatisGeneratorObj.getTablePrefix())
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(mybatisGeneratorObj.getIncludeTables())//修改替换成你需要的表名，多个表名传数组
                .setExclude(mybatisGeneratorObj.getExcludeTables())
                .setVersionFieldName("version")//默认乐观锁字段
                .setLogicDeleteFieldName("del_flag")
                .setTableFillList(Arrays.asList(
                        new TableFill("version", FieldFill.INSERT),
                        new TableFill("del_flag", FieldFill.INSERT),
                        new TableFill("create_time", FieldFill.INSERT),
                        new TableFill("creator", FieldFill.INSERT),
                        new TableFill("updator", FieldFill.INSERT_UPDATE),
                        new TableFill("create_datetime", FieldFill.INSERT),
                        new TableFill("update_datetime", FieldFill.INSERT_UPDATE),
                        new TableFill("time_version", FieldFill.INSERT_UPDATE)));


        GlobalConfig config = new GlobalConfig()
                .setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor("qiaoxiaozhuo")
                .setServiceName("%sService")
                .setOutputDir(mybatisGeneratorObj.getOutputDir())
                .setFileOverride(true)
                .setDateType(DateType.ONLY_DATE)
                .setBaseResultMap(true);

        // 自定义新模板
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                this.setMap(new HashMap<String, Object>(16) {{
                    put("criteriaPackageName".substring(0, 1).toUpperCase(), mybatisGeneratorObj.getPackageName() + mybatisGeneratorObj.getModule());
                }});
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/template/criteria.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path = mybatisGeneratorObj.getOutputDir() +
                        "/" + mybatisGeneratorObj.getPackageName().replaceAll("\\.", "/") +
                        "/criteria/";
                new File(path).mkdirs();
                return path + tableInfo.getEntityName() + "Criteria.java";
            }
        });
        cfg.setFileOutConfigList(focList);

        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(mybatisGeneratorObj.getPackageName())
                                .setMapper("dao")
                                .setXml("dao.mapper")
                                .setEntity("model")
                                .setService("service")
                                .setServiceImpl("service.impl")
                )
                .setTemplate(
                        new TemplateConfig()
                                .setEntity("/template/entity.java.vm")
                                .setMapper("/template/mapper.java.vm")
                                .setXml("/template/mapper.xml.vm")
                                .setService("/template/service.java.vm")
                                .setServiceImpl("/template/serviceImpl.java.vm")
                                .setController(null)
                )
                .setCfg(cfg)
                .execute();
    }
}
