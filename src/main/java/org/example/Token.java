package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class Token {

    private String token;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void updateToken(String token) {
        writeLock.lock();
        try {
            this.token = token;
        } finally {
            writeLock.unlock();
        }
    }

    public String getToken() {
        readLock.lock();
        try {
            return token;
        } finally {
            readLock.unlock();
        }
    }
}
