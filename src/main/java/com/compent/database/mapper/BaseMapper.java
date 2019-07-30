package com.compent.database.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author qiaoxiaozhuo 2019/03/28
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 批量插入所有列
     * @param entityList
     * @return
     */
    Integer batchInsertAllColumn(@Param("collection") Collection<T> entityList);

    /**
     * 执行delete语句
     * @param id
     * @return
     */
    default Integer hardDeleteById(Long id){
        return deleteById(id);
    }

    /**
     * 执行delete批量删除
     * @param ids
     * @return
     */
    default Integer hardDeleteByIds(Collection<Long> ids){
        return deleteBatchIds(ids);
    }

    /**
     * 根据ID 软删除一条数据
     * @param id
     * @return
     */
    Integer softDeleteById(Long id);

    /**
     * 根据ID集合，批量软删除数据
     * @param idList
     * @return
     */
    Integer softDeleteByIds(@Param("coll") Collection<? extends Serializable> idList);

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    int insert(T entity);


    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return int
     */
    @Override
    int deleteById(Serializable id);


    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    @Override
    int deleteByMap(@Param("ew") Map<String, Object> columnMap);


    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return int
     */
    @Deprecated
    @Override
    int delete(@Param("ew") Wrapper<T> wrapper);

    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @return int
     */
    @Override
    int deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList);

    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    int updateById(@Param("et") T entity);


    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param entity  实体对象
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return
     */
    @Override
    int update(@Param("et") T entity, @Param("ew") Wrapper<T> wrapper);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return E
     */
    @Override
    T selectById(Serializable id);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return List<T>
     */
    @Override
    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList);

    /**
     * <p>
     * 查询（根据 columnMap 条件）
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @return List<T>
     */
    @Deprecated
    @Override
    List<T> selectByMap(@Param("cm") Map<String, Object> columnMap);

    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return E
     */
    @Override
    T selectOne(@Param("ew") Wrapper<T> queryWrapper);

    /**
     * <p>
     * 根据 Wrapper 条件，查询总记录数
     * </p>
     *
     * @param wrapper 实体对象
     * @return int
     */
    @Override
    Integer selectCount(@Param("ew") Wrapper<T> wrapper);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return List<T>
     */
    @Override
    List<T> selectList(@Param("ew") Wrapper<T> wrapper);

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录
     * </p>
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return List<T>
     */
    @Deprecated
    @Override
    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> wrapper);

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录
     * 注意： 只返回第一个字段的值
     * </p>
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return List<Object>
     */
    @Deprecated
    @Override
    List<Object> selectObjs(@Param("ew") Wrapper<T> wrapper);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param page         分页查询条件
     * @param wrapper   实体对象封装操作类（可以为 null）
     * @return List<T>
     */
    @Override
    IPage<T> selectPage(IPage<T> page, @Param("ew") Wrapper<T> wrapper);

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param page         分页查询条件
     * @param wrapper   实体对象封装操作类
     */
    @Deprecated
    @Override
    IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param("ew") Wrapper<T> wrapper);

}
