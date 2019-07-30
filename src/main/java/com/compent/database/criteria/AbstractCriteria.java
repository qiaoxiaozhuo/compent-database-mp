package com.compent.database.criteria;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author qiaoxiaozhuo 2019/03/28
 */
@Slf4j
public abstract class AbstractCriteria<T> {

    private static final ObjectMapper JSON = new ObjectMapper()
            // 当实体类中不含有 json 字符串的某些字段时，不抛出异常
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            // 非 bean 对象不抛异常
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

            // 只输出非 null 字段
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private List<String> selects;

    private Page<T> page;

    @Getter
    @JsonIgnore
    private transient QueryWrapper<T> wrapper = new QueryWrapper<>();

    @Setter
    private int pageNum = 1;

    @Getter
    @Setter
    private int pageSize = 20;


    @Getter
    private String alias;


    /**
     * 添加 = 的查询条件
     *
     * @param column 需要 = 的数据库字段
     * @param value  需要 = 的值
     */
    protected void equals(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.eq(processColumn(column), value);
    }

    protected void orEquals(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.or().eq(processColumn(column), value);
    }

    protected void notEquals(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.ne(processColumn(column), value);
    }

    protected void orNotEquals(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.or().ne(processColumn(column), value);
    }

    protected void lessThan(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.lt(processColumn(column), value);
    }

    protected void orLessThan(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.or().lt(processColumn(column), value);
    }

    protected void lessThanEqual(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.le(processColumn(column), value);
    }

    protected void orLessThanEqual(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.or().le(processColumn(column), value);
    }

    protected void greaterThan(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.gt(processColumn(column), value);
    }

    protected void orGreaterThan(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.or().gt(processColumn(column), value);
    }

    protected void greaterThanEqual(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.ge(processColumn(column), value);
    }

    protected void orGreaterThanEqual(String column, @NonNull Object value) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.or().ge(processColumn(column), value);
    }

    protected void like(String column, String value) {
        Assert.notBlank(column, "column不能为空");
        Assert.notBlank(value, "value不能为空");
        this.wrapper.like(processColumn(column), value);
    }

    protected void orLike(String column, String value) {
        Assert.notBlank(column, "column不能为空");
        Assert.notBlank(value, "value不能为空");
        this.wrapper.or().like(processColumn(column), value);
    }

    protected void notLike(String column, String value) {
        Assert.notBlank(column, "column不能为空");
        Assert.notBlank(value, "value不能为空");
        this.wrapper.notLike(processColumn(column), value);
    }

    protected void orNotLike(String column, String value) {
        Assert.notBlank(column, "column不能为空");
        Assert.notBlank(value, "value不能为空");
        this.wrapper.or().notLike(processColumn(column), value);
    }

    protected void in(String column, Set<?> values) {
        Assert.notBlank(column, "column不能为空");
        Assert.notEmpty(values, "values不能为空");
        this.wrapper.in(processColumn(column), values);
    }

    protected void orIn(String column, Set<?> values) {
        Assert.notBlank(column, "column不能为空");
        Assert.notEmpty(values, "values不能为空");
        this.wrapper.or().in(processColumn(column), values);
    }

    protected void notIn(String column, Set<?> values) {
        Assert.notBlank(column, "column不能为空");
        Assert.notEmpty(values, "values不能为空");
        this.wrapper.notIn(processColumn(column), values);
    }

    protected void orNotIn(String column, Set<?> values) {
        Assert.notBlank(column, "column不能为空");
        Assert.notEmpty(values, "values不能为空");
        this.wrapper.or().notIn(processColumn(column), values);
    }


    protected void between(String column, @NonNull Object value1,@NonNull Object value2) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.between(processColumn(column), value1,value2);
    }

    protected void notBetween(String column, @NonNull Object value1,@NonNull Object value2) {
        Assert.notBlank(column, "column不能为空");
        this.wrapper.notBetween(processColumn(column), value1,value2);
    }

    public void setSelects(String... columns) {
        if (this.selects == null) {
            this.selects = Arrays.asList(columns);
        } else {
            this.selects.addAll(Arrays.asList(columns));
        }

        this.wrapper.select((String[]) this.selects.toArray(new String[0]));
    }

    public void addSelect(String column) {
        if (this.selects == null) {
            this.selects = new ArrayList<>();
        }
        this.selects.add(processColumn(column));

        this.wrapper.select((String[]) this.selects.toArray(new String[0]));
    }

    public Page<T> getPage() {
        if (this.page == null) {
            this.page = new Page<>();
        }
        this.page.setCurrent(this.pageNum);
        this.page.setSize(this.pageSize);
        return this.page;
    }


    public int getPageNum() {
        if (pageNum == 0) {
            pageNum = 1;
        }
        return pageNum;
    }

    private String processColumn(String column) {
        if (StrUtil.isNotBlank(this.alias)) {
            return this.alias + "." + column;
        }
        return column;
    }

    @Override
    public String toString() {
        try {
            return JSON.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return super.toString();
        }
    }

}
