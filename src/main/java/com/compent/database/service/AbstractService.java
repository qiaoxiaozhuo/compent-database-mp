package com.compent.database.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.compent.database.criteria.AbstractCriteria;
import com.compent.database.mapper.BaseMapper;

import java.util.Collection;
import java.util.List;

/**
 * @author qiaoxiaozhuo 2019/03/28
 */
public interface AbstractService<T> {

    BaseMapper<T> getMapper();

    //****************************** 增 start ******************************

    default Integer insert(T entity) {
        return getMapper().insert(entity);
    }

    default Integer batchInsertAllColumn(Collection<T> entitys) {
        if (entitys == null || entitys.isEmpty()) {
            return 0;
        }
        // 一次性批量插入限制，防止因业务逻辑的不合理，而影响数据库性能
        int limit = 5000;
        int insertCount = 0;
        if (entitys.size() <= limit) {
            insertCount = getMapper().batchInsertAllColumn(entitys);
        } else {
            for (Collection<T> es : CollUtil.split(entitys, limit)) {
                insertCount += getMapper().batchInsertAllColumn(es);
            }
        }

        return insertCount;
    }

    //****************************** 删 (物理)******************************

    default Integer hardDeleteById(Long id) {
        return getMapper().hardDeleteById(id);
    }

    default Integer hardDeleteByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        int limit = 2000;
        int count = 0;

        if (ids.size() <= limit) {
            count = getMapper().hardDeleteByIds(ids);
        } else {
            for (Collection<Long> idList : CollUtil.split(ids, limit)) {
                count += getMapper().hardDeleteByIds(idList);
            }
        }

        return count;
    }

    /**
     * 逻辑删
     *
     * @param id 实体的id
     * @return
     */
    default Integer softDeleteById(Long id) {
        return getMapper().softDeleteById(id);
    }

    /**
     * 逻辑删
     *
     * @param ids 实体的id集合
     * @return
     */
    default Integer softDeleteByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        // 一次性生成sql的in（）参数个数的限制，防止因业务逻辑的不合理，而影响数据库性能
        int limit = 2000;
        int count = 0;

        if (ids.size() <= limit) {
            count = getMapper().softDeleteByIds(ids);
        } else {
            for (Collection<Long> idList : CollUtil.split(ids, limit)) {
                count += getMapper().softDeleteByIds(idList);
            }
        }

        return count;
    }

    //****************************** 改  ******************************


    default Integer updateNotNullFieldsById(T entity) {
        return getMapper().updateById(entity);
    }

    default Integer updateAllColumnsByCriteria(T entity, AbstractCriteria<T> criteria) {
        return getMapper().update(entity, criteria.getWrapper());
    }

    default Integer updateNotNullFieldsByCriteria(T entity, AbstractCriteria<T> criteria) {
        return getMapper().update(entity, criteria.getWrapper());
    }

    //****************************** 查  ******************************

    default T getById(Long id) {
        return getMapper().selectById(id);
    }

    default List<T> findByIds(Collection<Long> ids) {
        return getMapper().selectBatchIds(ids);
    }

    default T getByIdAndVersion(Long id, Integer version) {
        Assert.notNull(id);
        Assert.notNull(version);

        return getMapper()
                .selectList(new QueryWrapper<T>().eq("id", id).eq("version", version))
                .stream()
                .findFirst()
                .orElse(null);
    }

    default List<T> findByCriteria(AbstractCriteria<T> criteria) {
        return getMapper().selectList(criteria.getWrapper());
    }

    default T findOneByCriteria(AbstractCriteria<T> criteria) {
        return getMapper()
                .selectList(criteria.getWrapper())
                .stream()
                .findFirst()
                .orElse(null);
    }

    default T findOneByEntity(QueryWrapper<T> wrapper) {
        return getMapper().selectOne(wrapper);
    }

    default List<T> findByWrapper(QueryWrapper<T> wrapper) {
        return getMapper().selectList(wrapper);
    }

    default IPage<T> findPageByCriteria(AbstractCriteria<T> criteria) {
        return this.findPageByCriteria(criteria.getPage(), criteria);
    }

    default IPage<T> findByCriteria(Page<T> page, AbstractCriteria<T> criteria) {
        return this.findPageByCriteria(page, criteria);
    }

    default IPage<T> findByCriteria(Page<T> page, QueryWrapper wrapper) {
        return this.findPageByWrapper(page, wrapper);
    }

    default IPage<T> findPageByCriteria(Page<T> page, AbstractCriteria<T> criteria) {
        return this.findPageByWrapper(page, criteria.getWrapper());
    }

    default IPage<T> findPageByWrapper(int pageNum, int pageSize, QueryWrapper wrapper) {
        return this.findPageByWrapper(new Page<>(pageNum, pageSize), wrapper);
    }

    default IPage<T> findPageByWrapper(IPage<T> page, QueryWrapper wrapper) {
        page.setRecords(getMapper().selectPage(page, wrapper).getRecords());
        return page;
    }

    default Integer countByCriteria(AbstractCriteria<T> criteria) {
        return getMapper().selectCount(criteria.getWrapper());
    }

    default long countByWrapper(QueryWrapper wrapper) {
        return getMapper().selectCount(wrapper);
    }

    default List<T> findAll() {
        return getMapper().selectList(null);
    }

    default Integer countAll() {
        return getMapper().selectCount(null);
    }
}
