package com.compent.database.util;

import lombok.Data;

/**
 * @author qiaoxiaozhuo 2019/03/28
 */

@Data
public class MybatisGeneratorObj {

    private String module;
    private String outputDir;
    private String dataSourceUserName;
    private String dataSourcePassword;
    private String dataSourceUrl;
    private String packageName;

    private String[] tablePrefix;
    private String[] includeTables;
    private String[] excludeTables;


}
