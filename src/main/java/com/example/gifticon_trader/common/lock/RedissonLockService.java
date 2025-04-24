package com.example.gifticon_trader.common.lock;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedissonLockService implements LockService {
  private final RedissonClient redissonClient;

  @Override
  public boolean tryLock(String lockKey, long waitTimeSec, long leaseTimeSec) {
    RLock lock = redissonClient.getLock(lockKey);
    try {
      return lock.tryLock(waitTimeSec, leaseTimeSec, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return false;
    }
  }

  @Override
  public void unlock(String lockKey) {
    RLock lock = redissonClient.getLock(lockKey);
    if (lock.isHeldByCurrentThread()) {
      lock.unlock();
    }
  }
}
