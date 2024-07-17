package com.blake.instance.lock.optimistic.version;

public interface UserUpdateByVersionOptimisticLock {

    /**
     * 基于MySQL数据表版本号的方式实现乐观锁
     *
     * @param id     数据主键id
     * @param increment 新的积分
     * @return
     */
    Boolean updatePointsByOptimisticLock(Long id, int increment);

}
