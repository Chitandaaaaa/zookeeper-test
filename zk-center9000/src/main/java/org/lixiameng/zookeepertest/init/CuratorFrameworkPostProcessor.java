package org.lixiameng.zookeepertest.init;

import jakarta.annotation.Resource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @program: zookeeper-test
 * @description:
 * @author: lixiameng
 * @create: 2024-06-12 23:25
 */
@Component
public class CuratorFrameworkPostProcessor implements BeanPostProcessor {

    @Resource
    private CuratorFramework curatorFramework;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        TreeCache treeCache = new TreeCache(curatorFramework, "/servers");
//        treeCache.getListenable().addListener(new TreeCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
//                System.out.println("节点变化："+treeCacheEvent);
//            }
//        });
//        try {
//            treeCache.start();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/servers", true);
//        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//                System.out.println("节点变化："+pathChildrenCacheEvent);
//
//            }
//        });
//
//        try {
//            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
////            pathChildrenCache.start(true);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        CuratorCache curatorCache = CuratorCache.build(curatorFramework, "/servers");

        CuratorCacheListener curatorCacheListener = CuratorCacheListener.builder().forCreates(childData -> System.out.println(
                "Creates! " + childData)).build();
        curatorCache.listenable().addListener(curatorCacheListener);

        curatorCache.start();
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
