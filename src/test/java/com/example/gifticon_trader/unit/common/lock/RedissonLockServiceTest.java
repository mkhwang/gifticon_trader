package com.example.gifticon_trader.unit.common.lock;

import com.example.gifticon_trader.common.lock.RedissonLockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedissonLockServiceTest {
  @Mock
  RedissonClient redissonClient;

  @InjectMocks
  RedissonLockService redissonLockService;

  @Test
  void tryLock_shouldReturnTrue() throws InterruptedException {
    // given
    String lockKey = "testLockKey";
    RLock mockLock = mock(RLock.class);
    long waitTime = 100L;
    long leaseTime = 200L;

    given(redissonClient.getLock(anyString())).willReturn(mockLock);
    given(mockLock.tryLock(eq(waitTime), eq(leaseTime), eq(TimeUnit.SECONDS))).willReturn(true);

    // when
    boolean result = redissonLockService.tryLock(lockKey, waitTime, leaseTime);

    // then
    verify(mockLock).tryLock(eq(waitTime), eq(leaseTime), eq(TimeUnit.SECONDS));
    assertTrue(result);
  }

  @Test
  void tryLock_shouldReturnFalse() throws InterruptedException {
    // given
    String lockKey = "testLockKey";
    RLock mockLock = mock(RLock.class);
    long waitTime = 100L;
    long leaseTime = 200L;

    given(redissonClient.getLock(anyString())).willReturn(mockLock);
    given(mockLock.tryLock(eq(waitTime), eq(leaseTime), eq(TimeUnit.SECONDS))).willThrow(new InterruptedException());

    // when
    boolean result = redissonLockService.tryLock(lockKey, waitTime, leaseTime);

    // then
    verify(mockLock).tryLock(eq(waitTime), eq(leaseTime), eq(TimeUnit.SECONDS));
    assertFalse(result);
  }


  @Test
  void unlock_shouldCallUnlock_whenLockIsHeldByCurrentThread() {
    // given
    String lockKey = "testLockKey";
    RLock mockLock = mock(RLock.class);

    given(redissonClient.getLock(lockKey)).willReturn(mockLock);
    given(mockLock.isHeldByCurrentThread()).willReturn(true);

    // when
    redissonLockService.unlock(lockKey);

    // then
    verify(mockLock).unlock();
  }

  @Test
  void unlock_shouldNotCallUnlock_whenLockIsNotHeldByCurrentThread() {
    // given
    String lockKey = "testLockKey";
    RLock mockLock = mock(RLock.class);

    given(redissonClient.getLock(anyString())).willReturn(mockLock);

    // when
    redissonLockService.unlock(lockKey);

    // then
    verify(mockLock, never()).unlock();
  }
}