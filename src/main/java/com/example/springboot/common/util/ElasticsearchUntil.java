package com.example.springboot.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rui
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchUntil {


    private static ElasticsearchRestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(ElasticsearchRestTemplate restTemplate) {

        ElasticsearchUntil.restTemplate = restTemplate;
    }



/**
     * 分页查询返回结果
     *
     * @param currentPage
     * @param size
     * @param query
     * @param <T>
     * @return
     */

    public static <T> Page<T> searchByPage(int currentPage, int size, @Nullable QueryBuilder query, Class<T> clazz, String indexName) {
        Assert.notNull(indexName, "indexName must not be null");
        return searchByPage(currentPage, size, query, null, clazz, indexName);
    }


/**
     * 分页查询返回结果
     *
     * @param currentPage
     * @param size
     * @param query
     * @param <T>
     * @return
     */

    public static <T> Page<T> searchByPage(int currentPage, int size, @Nullable QueryBuilder query, Class<T> clazz) {
        return searchByPage(currentPage, size, query, null, clazz);
    }

/**
     * 分页查询返回结果
     *
     * @param currentPage
     * @param size
     * @param query
     * @param sorts
     * @param <T>
     * @return
     */

    public static <T> Page<T> searchByPage(int currentPage, int size, @Nullable QueryBuilder query, @Nullable List<SortBuilder<?>> sorts, Class<T> clazz, String indexName) {
        Assert.notNull(indexName, "indexName must not be null");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query, null, sorts);
        nativeSearchQuery.setPageable(PageRequest.of(currentPage - 1, size));
        SearchHits<T> hits = restTemplate.search(nativeSearchQuery, clazz, IndexCoordinates.of(indexName));
        SearchPage<T> page = SearchHitSupport.searchPageFor(hits, nativeSearchQuery.getPageable());
        return (Page) SearchHitSupport.unwrapSearchHits(page);
    }


/**
     * 分页查询返回结果
     *
     * @param currentPage
     * @param size
     * @param query
     * @param sorts
     * @param <T>
     * @return
     */

    public static <T> Page<T> searchByPage(int currentPage, int size, @Nullable QueryBuilder query, @Nullable List<SortBuilder<?>> sorts, Class<T> clazz) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query, null, sorts);
        nativeSearchQuery.setPageable(PageRequest.of(currentPage - 1, size));
        SearchHits<T> hits = restTemplate.search(nativeSearchQuery, clazz);
        SearchPage<T> page = SearchHitSupport.searchPageFor(hits, nativeSearchQuery.getPageable());
        return (Page) SearchHitSupport.unwrapSearchHits(page);
    }


/**
     * 查询总量
     *
     * @param query
     * @param <T>
     * @return
     */

    public static <T> long searchCount(@Nullable QueryBuilder query, @Nullable List<SortBuilder<?>> sorts, Class<T> clazz, String indexName) {
        Assert.notNull(indexName, "indexName must not be null");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query, null, sorts);
        long count = restTemplate.count(nativeSearchQuery, clazz, IndexCoordinates.of(indexName));
        return count;
    }


/**
     * 查询总量
     *
     * @param query
     * @param <T>
     * @return
     */

    public static <T> long searchCount(@Nullable QueryBuilder query, @Nullable List<SortBuilder<?>> sorts, Class<T> clazz) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query, null, sorts);
        long count = restTemplate.count(nativeSearchQuery, clazz);
        return count;
    }


/**
     * 查询List返回结果
     *
     * @param query
     * @param <T>
     * @return
     */

    public static <T> Iterable<T> searchList(@Nullable QueryBuilder query, @Nullable List<SortBuilder<?>> sorts, Class<T> clazz, String indexName) {
        Assert.notNull(indexName, "indexName must not be null");
        int itemCount = (int) searchCount(query, sorts, clazz, indexName);
        Page<T> pages = (itemCount == 0 ? new PageImpl(Collections.emptyList()) : searchByPage(1, Math.max(1, itemCount), query, sorts, clazz, indexName));
        return pages.getContent();
    }

/**
     * 查询List返回结果
     *
     * @param query
     * @param <T>
     * @return
     */

    public static <T> Iterable<T> searchList(@Nullable QueryBuilder query, @Nullable List<SortBuilder<?>> sorts, Class<T> clazz) {
        int itemCount = (int) searchCount(query, sorts, clazz);
        Page<T> pages = (itemCount == 0 ? new PageImpl(Collections.emptyList()) : searchByPage(1, Math.max(1, itemCount), query, sorts, clazz));
        return pages.getContent();
    }


/**
     * 查询单条
     *
     * @param id
     * @param <T>
     * @return
     */

    public static <T> T getById(String id, Class<T> clazz) {
        Assert.notNull(id, "id must not be null");
        return restTemplate.get(id, clazz);
    }


/**
     * 查询单条
     *
     * @param id
     * @param <T>
     * @return
     */

    public static <T> T getById(String id, Class<T> clazz, String indexName) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(indexName, "indexName must not be null");
        return restTemplate.get(id, clazz, IndexCoordinates.of(indexName));
    }


/**
     * 查询单条
     *
     * @param query
     * @param <T>
     * @return
     */

    public static <T> T searchOne(@Nullable QueryBuilder query, Class<T> clazz) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query);
        SearchHit<T> hits = restTemplate.searchOne(nativeSearchQuery, clazz);
        return hits == null ? null : hits.getContent();
    }



/**
     * 查询单条
     *
     * @param query
     * @param indexName
     * @param <T>
     * @return
     */

    public static <T> T searchOne(@Nullable QueryBuilder query, Class<T> clazz, String indexName) {
        Assert.notNull(indexName, "indexName must not be null");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query);
        SearchHit<T> hits = restTemplate.searchOne(nativeSearchQuery, clazz, IndexCoordinates.of(indexName));
        return hits == null ? null : hits.getContent();
    }



/**
     * 实体类有id保存
     *
     * @param entity
     * @return T
     */

    public static <T> T save(T entity) {
        Assert.notNull(entity, "entity must not be null");
        Map<String, Object> map = JacksonUtils.obj2Map(entity);
        Object id = map.get("id");
        Assert.notNull(id, "id must not be null");
        return restTemplate.save(entity);
    }

/**
     * 保存
     *
     * @param entity
     * @param indexName
     * @return id
     */

    public static <T> String doIndex(T entity, String indexName) {
        Assert.notNull(entity, "entity must not be null");
        Assert.notNull(indexName, "indexName must not be null");
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(entity);
        return restTemplate.index(indexQuery, IndexCoordinates.of(indexName));
    }

/**
     * 保存
     *
     * @param entity
     * @param indexName
     * @param id
     * @return id
     */

    public static <T> String doIndex(T entity, String indexName, String id) {
        Assert.notNull(entity, "entity must not be null");
        Assert.notNull(indexName, "indexName must not be null");
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(id);
        indexQuery.setObject(entity);
        return restTemplate.index(indexQuery, IndexCoordinates.of(indexName));
    }


/**
     * 保存
     * 对象ID属性不能为空
     *
     * @param entity
     * @param indexName
     * @return T
     */

    public static <T> T save(T entity, String indexName) {
        Assert.notNull(entity, "entity must not be null");
        Assert.notNull(indexName, "indexName must not be null");
        Map<String, Object> map = JacksonUtils.obj2Map(entity);
        Object id = map.get("id");
        Assert.notNull(id, "id must not be null");
        return restTemplate.save(entity, IndexCoordinates.of(indexName));
    }

/**
     * 批量保存
     * 对象ID属性不能为空
     *
     * @param entities
     * @return T
     */

    public static <T> Iterable<T> saveBatch(Iterable<T> entities) {
        Assert.notNull(entities, "List must not be null");
        entities.forEach(entity -> {
            Map<String, Object> map = JacksonUtils.obj2Map(entity);
            Object id = map.get("id");
            Assert.notNull(id, "id must not be null");
        });
        return restTemplate.save(entities);
    }

/**
     * 实体类有id批量保存
     *
     * @param entities
     * @param indexName
     * @return T
     */

    public static <T> Iterable<T> saveBatch(Iterable<T> entities, String indexName) {
        Assert.notNull(entities, "List must not be null");
        Assert.notNull(indexName, "indexName must not be null");
        entities.forEach(entity -> {
            Map<String, Object> map = JacksonUtils.obj2Map(entity);
            Object id = map.get("id");
            Assert.notNull(id, "id must not be null");
        });
        return restTemplate.save(entities, IndexCoordinates.of(indexName));
    }

/**
     * 更新
     *
     * @param query
     * @param indexName
     */

    public static <T> long updateByQuery(Query query,T entity, String indexName) {
        Map<String, Object> map = JacksonUtils.obj2Map(entity);
        Document document = Document.from(map);
        UpdateQuery updateQuery = UpdateQuery.builder(query).withDocument(document).withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
        ByQueryResponse   byQueryResponse=restTemplate.updateByQuery(updateQuery, IndexCoordinates.of(indexName));
         return byQueryResponse.getUpdated();
    }


/**
     * 批量更新
     *
     * @param queries
     * @param indexName
     */

    public static <T> void updateBatchByQuery(List<Query> queries, String indexName) {
       List<UpdateQuery> updateQueries=queries.stream().map(query -> {
            return UpdateQuery.builder(query).withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
        }).collect(Collectors.toList());

        restTemplate.bulkUpdate(updateQueries, IndexCoordinates.of(indexName));
    }

/**
     * 更新
     *
     * @param entity
     * @param indexName
     */

    public static <T> void update(T entity, String indexName) {
        Assert.notNull(entity, "Entity must not be null");
        Map<String, Object> map = JacksonUtils.obj2Map(entity);
        Document document = Document.from(map);
        Object id = document.get("id");
        Assert.notNull(id, "id must not be null");
        UpdateQuery updateQuery = UpdateQuery.builder(id.getClass().equals(String.class) ? (String) id : id.toString()).withDocument(document).withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
        UpdateResponse updateResponse = restTemplate.update(updateQuery, IndexCoordinates.of(indexName));
        updateResponse.getResult();
    }



/**
     * 批量更新
     *
     * @param entities
     * @param indexName
     * @return
     */

    public static <T> void updateBatch(Iterable<T> entities, String indexName) {
        Assert.notNull(entities, "List of UpdateQuery must not be null");
        Assert.notNull(indexName, "indexName must not be null");
        List<UpdateQuery> queries = new ArrayList<>();
        for (T entity : entities) {
            Assert.notNull(entity, "Entity must not be null");
            Map<String, Object> map = JacksonUtils.obj2Map(entity);
            Document document = Document.from(map);
            Object id = document.get("id");
            Assert.notNull(id, "id must not be null");
            UpdateQuery updateQuery = UpdateQuery.builder(id.getClass().equals(String.class) ? (String) id : id.toString()).withDocument(document).withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
            queries.add(updateQuery);
        }
        restTemplate.bulkUpdate(queries, IndexCoordinates.of(indexName));
    }



/**
     * 删除
     *
     * @param id
     * @param indexName
     */

    public static <T> void deleteById(String id, String indexName) {
        restTemplate.delete(id, IndexCoordinates.of(indexName));

    }



/**
     * 删除
     *
     * @param entity
     */

    public static <T> void deleteByEntity(T entity) {
        restTemplate.delete(entity);
    }


/**
     * 删除
     *
     * @param entity
     * @param indexName
     */

    public static <T> void deleteByEntity(T entity, String indexName) {
        restTemplate.delete(entity, IndexCoordinates.of(indexName));
    }


/**
     * 删除索引
     *
     * @param indexName
     * @return Boolean
     */

    public static <T> boolean deleteIndex(String indexName) {
        boolean isDelete = restTemplate.indexOps(IndexCoordinates.of(indexName)).delete();
        return isDelete;
    }

/**
     * 删除
     * @param query
     * @param clazz
     * @param indexName
     * @return long 已删除的文档数
     */

    public static <T> long deleteByQuery(QueryBuilder query, Class<?> clazz,String indexName) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query);
        ByQueryResponse  byQueryResponse=restTemplate.delete(nativeSearchQuery,clazz,IndexCoordinates.of(indexName));
       return byQueryResponse.getDeleted();
    }


/**
     * 删除
     * @param query
     * @param clazz
     * @return long 已删除的文档数
     */

    public static <T> long deleteByQuery(QueryBuilder query, Class<?> clazz) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query);
        ByQueryResponse  byQueryResponse= restTemplate.delete(nativeSearchQuery,clazz);
        return byQueryResponse.getDeleted();
    }


/**
     * 判断索引是否存在
     *
     * @param indexName
     * @return Boolean
     */

    public static <T> boolean existIndex(String indexName) {
        boolean exists = restTemplate.indexOps(IndexCoordinates.of(indexName)).exists();
        return exists;
    }


/**
     * 创建索引
     *
     * @param indexName
     * @return Boolean
     */

    public static <T> boolean createIndex(String indexName) {
        boolean isCreate = restTemplate.indexOps(IndexCoordinates.of(indexName)).create();
        return isCreate;
    }


}

