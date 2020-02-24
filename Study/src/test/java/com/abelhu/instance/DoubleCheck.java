package com.abelhu.instance;

public final class DoubleCheck {
    private DoubleCheck() {
    }

    /**
     * 利用了volatile的禁止指令重排序优化特性
     */
    private volatile static DoubleCheck instance = null;

    public static DoubleCheck getInstance() {
        if (instance == null) {
            synchronized (DoubleCheck.class) {
                /**
                 * instance = new DoubleCheck()做了3件事：
                 *
                 * 0. 给ins分配内存
                 * 1. 调用构造函数来初始化成员变量(可能会很久)
                 * 2. 将ins对象指向分配的内存空间（执行完这步 ins才不为null）
                 *
                 * 指令重排导致第二三两步的执行顺序可能会被打乱，当第3步先于第2步完成，那么会导致有线程拿到了初始化未完毕的ins，那么就会错误
                 */
                if (instance == null) instance = new DoubleCheck();
            }
        }
        return instance;
    }
}
