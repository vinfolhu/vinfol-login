package com.vinfol.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ZSetOperations;

public interface BaseRedisDao<K, V> {
	/**
	 */
	double getCreateTimeScore(long date);

	/**
	 */
	Set<K> getAllKeys();

	/**
	 */
	Map<K, V> getAllString();

	/**
	 */
	Map<K, Set<V>> getAllSet();

	/**
	 */
	Map<K, Set<V>> getAllZSetReverseRange();

	/**
	 */
	Map<K, Set<V>> getAllZSetRange();

	/**
	 * 获取所有的List -key-value
	 */
	Map<K, List<V>> getAllList();

	/**
	 */
	Map<K, Map<K, V>> getAllMap();

	/**
	 */
	void addList(K key, List<V> objectList);

	/**
	 */
	long addList(K key, V obj);

	/**
	 */
	long addList(K key, V... obj);

	/**
	 */
	List<V> getList(K key, long s, long e);

	/**
	 */
	List<V> getList(K key);

	/**
	 */
	long getListSize(K key);

	/**
	 */
	long removeListValue(K key, V object);

	/**
	 */
	long removeListValue(K key, V... object);

	/**
	 */
	void remove(final K... keys);

	/**
	 */
	void remove(final K key);

	/**
	 *
	 */
	void removeZSetRangeByScore(String key, double s, double e);

	/**
	 */
	Boolean setSetExpireTime(String key, Long time);

	/**
	 */
	Boolean setZSetExpireTime(String key, Long time);

	/**
	 */
	boolean exists(final K key);

	/**
	 */
	V get(final K key);

	/**
	 */
	List<V> get(final K... key);

	List<Object> getByRegular(final K regKey);

	void set(final K key, V value);

	void set(final K key, V value, Long expireTime);

	boolean setExpireTime(K key, Long expireTime);

	DataType getType(K key);

	void removeMapField(K key, V... field);

	Map<K, V> getMap(K key);

	Long getMapSize(K key);

	<T> T getMapField(K key, K field);

	/**
	 */
	Boolean hasMapKey(K key, K field);

	/**
	 */
	List<V> getMapFieldValue(K key);

	/**
	 */
	Set<V> getMapFieldKey(K key);

	/**
	 */
	void addMap(K key, Map<K, V> map);

	/**
	 */
	void addMap(K key, K field, Object value);

	/**
	 */
	void addMap(K key, K field, V value, long time);

	/**
	 */
	void addSet(K key, V... obj);

	/**
	 */
	void watch(String key);

	/**
	 */
	long removeSetValue(K key, V obj);

	/**
	 */
	long removeSetValue(K key, V... obj);

	/**
	 */
	long getSetSize(K key);

	/**
	 */
	Boolean hasSetValue(K key, V obj);

	/**
	 */
	Set<V> getSet(K key);

	/**
	 */
	Set<V> getSetUnion(K key, K otherKey);

	/**
	 */
	Set<V> getSetUnion(K key, Set<Object> set);

	/**
	 */
	Set<V> getSetIntersect(K key, K otherKey);

	/**
	 * 获得set 交集
	 */
	Set<V> getSetIntersect(K key, Set<Object> set);

	/**
	 */
	void removeBlear(K... blears);

	/**
	 */
	Boolean renameIfAbsent(String oldKey, String newKey);

	/**
	 */
	void removeBlear(K blear);

	/**
	 */
	void removeByRegular(String... blears);

	/**
	 */
	void removeByRegular(String blears);

	/**
	 */
	void removeMapFieldByRegular(K key, K... blears);

	/**
	 */
	void removeMapFieldByRegular(K key, K blear);

	/**
	 * 移除key 对应的value
	 */
	Long removeZSetValue(K key, V... value);

	/**
	 */
	void removeZSet(K key);

	/**
	 */
	void removeZSetRange(K key, Long start, Long end);

	/**
	 */
	void setZSetUnionAndStore(String key, String key1, String key2);

	/**
	 */
	<T> T getZSetRange(K key);

	/**
	 */
	<T> T getZSetRange(K key, long start, long end);

	/**
	 */
	Set<Object> getZSetReverseRange(K key);

	Set<V> getZSetReverseRange(K key, long start, long end);

	Set<V> getZSetRangeByScore(String key, double start, double end);

	Set<V> getZSetReverseRangeByScore(String key, double start, double end);

	Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key, long start, long end);

	Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key, long start, long end);

	Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key);

	Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key);

	long getZSetCountSize(K key, double sMin, double sMax);

	long getZSetSize(K key);

	double getZSetScore(K key, V value);

	double incrementZSetScore(K key, V value, double delta);

	Boolean addZSet(String key, double score, Object value);

	Long addZSet(K key, TreeSet<V> value);

	Boolean addZSet(K key, double[] score, Object[] value);
}