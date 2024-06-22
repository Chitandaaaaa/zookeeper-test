package org.lixiameng.zookeepertest.init;

import jakarta.annotation.Resource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: zookeeper-test
 * @description:
 * @author: lixiameng
 * @create: 2024-06-22 19:35
 */
@Component
public class ZookeeperLisenerInit implements ApplicationRunner {

    @Resource
    private CuratorFramework curatorFramework;

    @Override
    public void run(ApplicationArguments args) {
        CuratorCache curatorCache = CuratorCache.build(curatorFramework, "/servers");

        CuratorCacheListener curatorCacheListener = CuratorCacheListener.builder()
                .forCreates(childData -> System.out.println("节点创建! " + childData))
                .forDeletes(childData -> System.out.println("节点删除! " + childData))
                .forChanges((oldData, data) -> System.out.println("节点设值! " + oldData))
                .forCreatesAndChanges((oldNode, node) -> System.out.println("节点创建和设值! oldNode:" + oldNode + "node:" + node))
                .forAll(((type, oldData, data) -> System.out.println("节点所有操作! 类型:" + type + ", oldData:" + oldData +
                        "data:" + data)))
                .build();
        curatorCache.listenable().addListener(curatorCacheListener);

        curatorCache.start();
    }
}
