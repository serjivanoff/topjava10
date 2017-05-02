package ru.javawebinar.topjava.service;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractServiceTest <T> {
    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }
   @Autowired
   protected T service;
}
