import org.junit.Test

//请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
//
// 实现 LFUCache 类： 
//
// 
// LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象 
// int get(int key) - 如果键存在于缓存中，则获取键的值，否则返回 -1。 
// void put(int key, int value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量时，则应该在插入新项之
//前，使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。 
// 
//
// 注意「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。 
//
// 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。 
//
// 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。 
//
// 
//
// 示例： 
//
// 
//输入：
//["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "g
//et"]
//[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
//输出：
//[null, null, null, 1, null, -1, 3, null, -1, 3, 4]
//
//解释：
//// cnt(x) = 键 x 的使用计数
//// cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
//LFUCache lFUCache = new LFUCache(2);
//lFUCache.put(1, 1);   // cache=[1,_], cnt(1)=1
//lFUCache.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
//lFUCache.get(1);      // 返回 1
//                      // cache=[1,2], cnt(2)=1, cnt(1)=2
//lFUCache.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
//                      // cache=[3,1], cnt(3)=1, cnt(1)=2
//lFUCache.get(2);      // 返回 -1（未找到）
//lFUCache.get(3);      // 返回 3
//                      // cache=[3,1], cnt(3)=2, cnt(1)=2
//lFUCache.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
//                      // cache=[4,3], cnt(4)=1, cnt(3)=2
//lFUCache.get(1);      // 返回 -1（未找到）
//lFUCache.get(3);      // 返回 3
//                      // cache=[3,4], cnt(4)=1, cnt(3)=3
//lFUCache.get(4);      // 返回 4
//                      // cache=[3,4], cnt(4)=2, cnt(3)=3 
//
// 
//
// 提示： 
//
// 
// 0 <= capacity, key, value <= 104 
// 最多调用 105 次 get 和 put 方法 
// 
//
// 
//
// 进阶：你可以为这两种操作设计时间复杂度为 O(1) 的实现吗？ 
// Related Topics 设计 
// 👍 388 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution460 {
    /**
     * 因为LinkedHashMap不符合要求了,这里需要自己构建双重链表(数组+链表)
     */
    class LFUCache(private val capacity: Int = 8) {
        val lfuMap = LFUHashMap<Int, Int>(capacity)

        fun get(key: Int): Int = lfuMap[key]?.value ?: -1

        fun put(key: Int, value: Int) = lfuMap.put(key, value)

        class LFUHashMap<K, V>(private val capacity: Int) : HashMap<K, LFUNode<K, V>>() {
            /**
             * map中的key为频率,方便根据[LFUNode.frequency]来达到O(1)的查找效率
             */
            val map = LinkedHashMap<Int, LinkedHashMap<K, LFUNode<K, V>>>()

            // 获取的时候需要更新使用频率
            override fun get(key: K): LFUNode<K, V>? = super.get(key)?.apply { remove(this);put(this) }

            // 放置更新的时候,需要先删除掉再插入
            fun put(key: K, value: V): LFUNode<K, V> = put(super.get(key)?.apply { this.value = value;remove(this) } ?: LFUNode(key, value))

            fun remove(node: LFUNode<K, V>): LFUNode<K, V>? {
                // 先根据node的frequency找到map中对应的节点
                when (val subMap = map[node.frequency]) {
                    null -> return null
                    // 再根据node的key找到subMap中的LFUNode
                    else -> subMap.remove(node.key)
                }
                // 最后删除掉源map中的数据即可
                return super.remove(node.key)
            }

            fun put(node: LFUNode<K, V>): LFUNode<K, V> {
                // 先在源map中保存数据
                super.put(node.key, node)
                // 判断是否超出了限制, 如果超过了,就需要把频率最少,最早放进去的node删除掉
                if (super.size > capacity) {
                    // 异常情况的处理
                    if (map.isEmpty()){
                        super.remove(node.key)
                        return node
                    }
                    val iterator = map.entries.iterator()
                    var subMap = iterator.next().value
                    while (subMap.isEmpty()) subMap = iterator.next().value
                    remove(subMap.entries.iterator().next().value)
                }
                // 增加数据,意味着需要增加对应的频率
                node.frequency++
                // 如果没有数据,就构造一个LinkedHashMap
                if (map.isEmpty() || !map.containsKey(node.frequency)) map[node.frequency] = LinkedHashMap<K, LFUNode<K, V>>().apply { put(node.key, node) }
                // 如果存在对应的频率,就直接在后面加就好了
                else map[node.frequency]?.put(node.key, node)
                // 返回
                return node
            }
        }

        class LFUNode<K, V>(val key: K, var value: V, var frequency: Int = 0)
    }

    @Test
    fun test0() {
        val lFUCache = LFUCache(2)
        lFUCache.put(1, 1)   // cache=[1,_], cnt(1)=1
        lFUCache.put(2, 2)   // cache=[2,1], cnt(2)=1, cnt(1)=1
        assert(1 == lFUCache.get(1))      // 返回 1
        // cache=[1,2], cnt(2)=1, cnt(1)=2
        lFUCache.put(3, 3)   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
        // cache=[3,1], cnt(3)=1, cnt(1)=2
        assert(-1 == lFUCache.get(2))      // 返回 -1（未找到）
        assert(3 == lFUCache.get(3))      // 返回 3
        // cache=[3,1], cnt(3)=2, cnt(1)=2
        lFUCache.put(4, 4)   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
        // cache=[4,3], cnt(4)=1, cnt(3)=2
        assert(-1 == lFUCache.get(1))      // 返回 -1（未找到）
        assert(3 == lFUCache.get(3))      // 返回 3
        // cache=[3,4], cnt(4)=1, cnt(3)=3
        assert(4 == lFUCache.get(4))      // 返回 4
        // cache=[3,4], cnt(4)=2, cnt(3)=3
    }

    @Test
    fun test1() {
        val lFUCache = LFUCache(2)
        lFUCache.put(3, 1)
        lFUCache.put(2, 1)
        lFUCache.put(2, 2)
        lFUCache.put(4, 4)
        assert(2 == lFUCache.get(2))
    }

    @Test
    fun test2() {
        val lFUCache = LFUCache(0)
        lFUCache.put(0, 0)
        assert(-1 == lFUCache.get(0))
    }

    @Test
    fun test3() {
        val lFUCache = LFUCache(2)
        lFUCache.put(2, 1)
        lFUCache.put(2, 2)
        assert(2 == lFUCache.get(2))
        lFUCache.put(1, 1)
        lFUCache.put(4, 1)
        assert(2 == lFUCache.get(2))
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * var obj = LFUCache(capacity)
 * var param_1 = obj.get(key)
 * obj.put(key,value)
 */
//leetcode submit region end(Prohibit modification and deletion)
