package com.compent.database.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qiaoxiaozhuo 2019/05/10
 */
@Setter
@Getter
public class PageInfo<T>{

    private Long pageNo;

    private Long pageSize;

    private List<T> list;

    /**
     * 当前分页总页数
     *
     * @return 总页数
     */
    private Long totalPages;



    /**
     * 当前满足条件总行数
     *
     * @return 总条数
     */
    private Long count;

}
