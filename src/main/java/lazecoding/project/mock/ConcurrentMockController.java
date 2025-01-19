package lazecoding.project.mock;

import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.JsonUtil;
import lazecoding.project.common.util.cache.RedissonClientUtil;
import lazecoding.project.common.util.concurrent.AtomicNumber;
import lazecoding.project.common.util.concurrent.Locker;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("mock/concurrent")
public class ConcurrentMockController {

    @GetMapping(value = "locker")
    @ResponseBody
    public ResultBean locker() {
        ResultBean resultBean = ResultBean.getInstance();
        Locker locker1 = new Locker("key");
        Locker locker2 = new Locker("key");
        boolean tryLock1 = locker1.tryLock();
        System.out.println("tryLock1:" + tryLock1);
        if (tryLock1) {
            try {
                System.out.println("111111111111111111");
                boolean tryLock2 = locker2.tryLock();
                System.out.println("tryLock2:" + tryLock1);
                if (tryLock2) {
                    try {
                        System.out.println("222222222222222");
                    } catch (Exception e) {

                    } finally {
                        locker2.unlock();
                        System.out.println("unlock2");
                    }
                }
            } catch (Exception e) {

            } finally {
                locker1.unlock();
                System.out.println("unlock1");
            }
        }
        resultBean.setMessage("pong");
        return resultBean;
    }

    @GetMapping(value = "atomic")
    @ResponseBody
    public ResultBean atomic() {
        ResultBean resultBean = ResultBean.getInstance();
        AtomicNumber atomicNumber = new AtomicNumber("atomic");
        atomicNumber.delete();
        System.out.println("atomicNumber:" + atomicNumber.get());
        atomicNumber = new AtomicNumber("atomic", 2L);
        System.out.println("atomicNumber:" + atomicNumber.get());
        System.out.println("atomicNumber incr 1:" + atomicNumber.incr());
        System.out.println("atomicNumber incr 3:" + atomicNumber.incr(3));
        System.out.println("atomicNumber:" + atomicNumber.get());
        System.out.println("atomicNumber decr 1:" + atomicNumber.decr());
        System.out.println("atomicNumber decr 1:" + atomicNumber.decr(2));
        System.out.println("atomicNumber:" + atomicNumber.get());
        atomicNumber.delete();
        System.out.println("atomicNumber:" + atomicNumber.get());
        resultBean.setMessage("pong");
        return resultBean;
    }

    @GetMapping(value = "queue")
    @ResponseBody
    public ResultBean queue() {
        ResultBean resultBean = ResultBean.getInstance();
        RedissonClient redisson = RedissonClientUtil.getRedissonClient();
        // 获取队列对象
        RQueue<String> queue = redisson.getQueue("myQueue");
        // 入队操作
        queue.offer("element1");
        queue.offer("element2");
        queue.offer("element3");
        // 获取队列大小
        long size = queue.size();
        System.out.println("队列大小: " + size);
        // 出队操作
        String firstElement = queue.poll();
        System.out.println("出队元素: " + firstElement);
        List<String> all = queue.readAll();
        System.out.println("全部元素: " + JsonUtil.GSON.toJson(all));
        // 移除 mock 数据
        queue.delete();
        resultBean.setMessage("queue");
        return resultBean;
    }


}
