package com.blake.instance.lock.optimistic.cas;

import com.blake.instance.common.domain.User;
import com.blake.instance.common.mapper.UserMapper;
import com.blake.instance.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class UserUpdateByCasOptimisticLockService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 基于CAS实现的乐观锁
     *
     * @param id        数据主键id
     * @param increment 新的积分
     * @return
     */
    public Boolean updatePointsByOptimisticLock(Long id, int increment) {
        // 最大重试次数
        AtomicInteger retryCount = new AtomicInteger(3);

        while (retryCount.getAndIncrement() > 0) {
            User user = this.userService.getById(id);
            int currentPoints = user.getPoints();

            int newPoints = currentPoints + increment;
            int updateRows = this.userMapper.updatePointsById(id, currentPoints, newPoints);

            if (updateRows == 1) {
                return Boolean.TRUE;
            } else {
                log.error("updatePointsByOptimisticLock failed, version={}", user.getVersion());
            }
        }
        throw new RuntimeException("...");
    }
}
