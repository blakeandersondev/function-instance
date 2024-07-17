package com.blake.instance.lock.optimistic.version;

import com.blake.instance.common.mapper.UserMapper;
import com.blake.instance.common.domain.User;
import com.blake.instance.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class UserUpdateByVersionOptimisticLockService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 基于MySQL数据表版本号的方式实现乐观锁
     *
     * @param id     数据主键id
     * @param increment 新的积分
     * @return
     */
    public Boolean updatePointsByOptimisticLock(Long id, int increment) {
        // 最大重试次数
        AtomicInteger retryCount = new AtomicInteger(3);

        while (retryCount.getAndIncrement() > 0) {
            User user = this.userService.getById(id);
            int updateRows = this.userMapper.increasePointsByIdAndVersion(id, increment, user.getVersion());
            if (updateRows == 1) {
                return Boolean.TRUE;
            } else {
                log.error("updatePointsByOptimisticLock failed, version={}", user.getVersion());
            }
        }
        throw new RuntimeException("...");
    }
}
