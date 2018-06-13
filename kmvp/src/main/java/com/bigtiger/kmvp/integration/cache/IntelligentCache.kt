package com.bigtiger.kmvp.integration.cache

class IntelligentCache<V>(size: Int): Cache<String, V>{

    companion object {
        const val KEY_KEEP = "Keep="
    }

    private val mMap = HashMap<String, V> ()//可将数据永久存储至内存中的存储容器
    private val mCache = LruCache<String, V>(size)//当达到最大容量时可根据 LRU 算法抛弃不合规数据的存储容器


    /**
     * 将 {@link #mMap} 和 {@link #mCache} 的 {@code size} 相加后返回
     *
     * @return 相加后的 {@code size}
     */
    override fun size(): Int {
        return mMap.size + mCache.size()
    }

    /**
     * 将 {@link #mMap} 和 {@link #mCache} 的 {@code maxSize} 相加后返回
     *
     * @return 相加后的 {@code maxSize}
     */
    override fun getMaxSize(): Int {
        return mMap.size + mCache.getMaxSize();
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key {@code key}
     * @return {@code value}
     */
    override fun get(key:String):V? {
        if (key.startsWith(KEY_KEEP)) {
            return mMap[key]
        }
        return mCache.get(key)
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key   {@code key}
     * @param value {@code value}
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value}, 则返回之前的 {@code value} 否则返回 {@code null}
     */
    override fun put(key: String, value: V):V? {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.put(key, value)
        }
        return mCache.put(key, value)
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key {@code key}
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value} 并且删除成功则返回删除的 {@code value}, 否则返回 {@code null}
     */
    override fun remove(key:String): V? {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.remove(key)
        }
        return mCache.remove(key)
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key {@code key}
     * @return {@code true} 为在容器中含有这个 {@code key}, 否则为 {@code false}
     */
    override fun containsKey(key:String): Boolean {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.containsKey(key)
        }
        return mCache.containsKey(key)
    }

    /**
     * 将 {@link #mMap} 和 {@link #mCache} 的 {@code keySet} 合并返回
     *
     * @return 合并后的 {@code keySet}
     */
    override fun keySet():Set<String> {
        val set = mCache.keySet()
        set.plus(mMap.keys)
        return set
    }

    /**
     * 清空 {@link #mMap} 和 {@link #mCache} 容器
     */
    override fun clear() {
        mCache.clear()
        mMap.clear()
    }
}