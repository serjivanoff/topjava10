package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

/**
 * Created by bender on 11.05.2017.
 */
public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest{
    @Autowired
    protected JpaUtil jpaUtil;
    @Override
    @Before
    public void setUp() throws Exception {
        service.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
