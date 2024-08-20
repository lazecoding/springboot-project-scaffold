package lazecoding.project.mock;

import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.cache.CacheOperator;
import lazecoding.project.common.util.cache.RedissonClientUtil;
import org.redisson.api.RBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * MockController
 *
 * @author lazecoding
 */
@RestController
@RequestMapping("interface/mock")
public class MockController {

    private static final Logger logger = LoggerFactory.getLogger(MockController.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * redisTemplateMock
     */
    @RequestMapping(value = "redis-template-mock", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean redisTemplateMock() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            String mockValue = redisTemplate.opsForValue().get("redis-template-mock");
            System.out.println("mockValue 1:" + mockValue);
            redisTemplate.opsForValue().set("redis-template-mock", "mock1");
            mockValue = redisTemplate.opsForValue().get("redis-template-mock");
            System.out.println("mockValue 2:" + mockValue);
            redisTemplate.opsForValue().set("redis-template-mock", "mock2");
            System.out.println("mockValue 3:" + mockValue);
            redisTemplate.delete("redis-template-mock");
            mockValue = redisTemplate.opsForValue().get("redis-template-mock");
            System.out.println("mockValue 4:" + mockValue);
            isSuccess = true;
            message = "获取成功";
        } catch (BusException e) {
            logger.error("redisTemplateMock 异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("redisTemplateMock 异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * redissonMock
     */
    @RequestMapping(value = "redisson-mock", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean redissonMock() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            RBucket<String> bucket = RedissonClientUtil.getRedissonClient().getBucket("redis-template-mock");

            String mockValue = bucket.get();
            System.out.println("mockValue 1:" + mockValue);
            bucket.set("mock1");
            mockValue = bucket.get();
            System.out.println("mockValue 2:" + mockValue);
            bucket.set("mock2");
            System.out.println("mockValue 3:" + mockValue);

            CacheOperator.set("redis-template-mock", "CacheOperator 3 - 1");
            mockValue = CacheOperator.get("redis-template-mock");
            System.out.println("mockValue 3 - 1:" + mockValue);

            bucket.delete();
            mockValue = bucket.get();
            System.out.println("mockValue 4:" + mockValue);


            System.out.println("mockValue incr 1:" + CacheOperator.incr("redis-template-cr"));
            System.out.println("mockValue incr 2:" + CacheOperator.incr("redis-template-cr"));
            System.out.println("mockValue incr 3:" + CacheOperator.incr("redis-template-cr", 3));
            System.out.println("mockValue decr 1:" + CacheOperator.decr("redis-template-cr", 3));

            System.out.println("exists 1:" + CacheOperator.exists("redis-template-cr"));

            Integer cr = CacheOperator.get("redis-template-cr");
            System.out.println("mockValue cr 1:" + cr);
            CacheOperator.delete("redis-template-cr");
            cr = CacheOperator.get("redis-template-cr");
            System.out.println("mockValue cr 2:" + cr);
            System.out.println("exists 2:" + CacheOperator.exists("redis-template-cr"));
            isSuccess = true;
            message = "获取成功";

        } catch (BusException e) {
            logger.error("redissonMock 异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("redissonMock 异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


}
