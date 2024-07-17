package com.blake.instance.lock.optimistic;

import com.blake.instance.FunctionInstanceApplication;
import com.blake.instance.lock.optimistic.cas.UserUpdateByCasOptimisticLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
@SpringBootTest(classes = FunctionInstanceApplication.class)
public class CasLockTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserUpdateByCasOptimisticLockService casOptimisticLockService;

    @Test
    public void testVersionLock() {
        Boolean isSuccess = this.casOptimisticLockService.updatePointsByOptimisticLock(1L, 100);
        Assert.assertTrue(isSuccess);
    }
}
