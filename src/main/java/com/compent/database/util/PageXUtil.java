package com.compent.database.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author qiaoxiaozhuo 2019/05/10
 */
public class PageXUtil {


    public static <T> PageInfo<T> calculate(IPage page) {
        PageInfo<T> viewPageInfo = new PageInfo<T>();
        viewPageInfo.setList(page.getRecords());
        viewPageInfo.setPageNo(page.getCurrent());
        viewPageInfo.setPageSize(page.getSize());
        viewPageInfo.setTotalPages(page.getPages());
        viewPageInfo.setCount(page.getTotal());
        return viewPageInfo;
    }


    public static <T> PageInfo<T> calculate(IPage page, List<T> records) {
        PageInfo<T> viewPageInfo = new PageInfo<T>();
        viewPageInfo.setList(records);
        viewPageInfo.setPageNo(page.getCurrent());
        viewPageInfo.setPageSize(page.getSize());
        viewPageInfo.setTotalPages(page.getPages());
        viewPageInfo.setCount(page.getTotal());
        return viewPageInfo;
    }
}
