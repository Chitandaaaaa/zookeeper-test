import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

/**
 * @program: zookeeper-test
 * @description:
 * @author: lixiameng
 * @create: 2024-06-13 00:31
 */
public class TestClll {

    private String connectString = "localhost:2181";
    // connection 超时时间
    private int connectionTimeout = 2000;
    // session 超时时间
    private int sessionTimeout = 2000;

    public CuratorFramework getCuratorFramework(){
        //重试策略，初试时间 3 秒，重试 3 次
        RetryPolicy policy = new ExponentialBackoffRetry(3000, 3);
        //通过工厂创建 Curator
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectString)
                        .connectionTimeoutMs(connectionTimeout)
                        .sessionTimeoutMs(sessionTimeout)
                        .retryPolicy(policy).build();
        //开启连接
        client.start();
        System.out.println("zookeeper 初始化完成...");
        return client;
    }

    @Test
    public void stat() throws InterruptedException {
        CuratorFramework curatorFramework = getCuratorFramework();
        CuratorCache curatorCache = CuratorCache.build(curatorFramework, "/servers");

        CuratorCacheListener curatorCacheListener = CuratorCacheListener.builder().forCreates(childData -> System.out.println(
                "Creates! " + childData)).build();
        curatorCache.listenable().addListener(curatorCacheListener);

        curatorCache.start();

//        curatorFramework.close();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
