package com.blake.instance.lock.optimistic;

import com.blake.instance.FunctionInstanceApplication;
import com.blake.instance.lock.optimistic.version.UserUpdateByVersionOptimisticLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
@SpringBootTest(classes = FunctionInstanceApplication.class)
public class VersionLockTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserUpdateByVersionOptimisticLockService versionOptimisticLockService;

    @Test
    public void testVersionLock() {
        Boolean isSuccess = this.versionOptimisticLockService.updatePointsByOptimisticLock(1L, 100);
        Assert.assertTrue(isSuccess);
    }
}
