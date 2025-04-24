package com.example.gifticon_trader.common.lock;

public interface LockService {

  boolean tryLock(String lockKey, long waitTimeSec, long leaseTimeSec);
  void unlock(String lockKey);
}