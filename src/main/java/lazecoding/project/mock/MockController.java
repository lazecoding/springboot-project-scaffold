package lazecoding.project.mock;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lazecoding.project.common.entity.User;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.JsonUtil;
import lazecoding.project.common.util.cache.CacheOperator;
import lazecoding.project.common.util.cache.RedissonClientUtil;
import lazecoding.project.repository.UserRepository;
import org.redisson.api.RBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRepository userRepository;

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


    /**
     * redisTemplateMock
     */
    @RequestMapping(value = "user-mock", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean userMock() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            Date now = new Date();
            String uid = "uid-1";
            String uname = UUID.randomUUID().toString();
            List<User> users = userRepository.findAll();
            System.out.println("users:" + JsonUtil.GSON.toJson(users));
            User user = new User();
            user.setUid(uid);
            user.setUname(uname);
            user.setPwd("9999999999999999");
            System.out.println("user save:" + userRepository.save(user));
            users = userRepository.findAll();
            System.out.println("users:" + JsonUtil.GSON.toJson(users));

            Optional<User> userOptional = userRepository.findById(uid);
            System.out.println("findById:" + JsonUtil.GSON.toJson(userOptional));
            System.out.println("findById:" + JsonUtil.GSON.toJson(userRepository.findById("11111111")));

            user.setPhone("33333333333333");
            System.out.println("user save:" + userRepository.save(user));
            users = userRepository.findAll();
            System.out.println("users:" + JsonUtil.GSON.toJson(users));
            System.out.println("findByName:" + JsonUtil.GSON.toJson(userRepository.findByName(uname)));

            // 批量查询一堆
            List<User> userList = new ArrayList<>();
            userList.add(new User(UUID.randomUUID().toString(), "111"));
            userList.add(new User(UUID.randomUUID().toString(), "222"));
            userList.add(new User(UUID.randomUUID().toString(), "333"));
            userList.add(new User(UUID.randomUUID().toString(), "444"));
            userList.add(new User(UUID.randomUUID().toString(), "555"));
            userList.add(new User(UUID.randomUUID().toString(), "666"));
            userList.add(new User(UUID.randomUUID().toString(), "777"));
            userList.add(new User(UUID.randomUUID().toString(), "888"));
            userRepository.saveAll(userList);

            List<User> stateUsers = userRepository.findByState(0);
            System.out.println("findByState:" + JsonUtil.GSON.toJson(stateUsers));


            try {
                Thread.sleep(1000);
                System.out.println("sleep ...");
            } catch (Exception e) {

            }

            Pageable pageable = PageRequest.of(0, 2);
            // pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "pwd");
            Page<User> pageableUsers = userRepository.findByCreateTime(now, pageable);
            System.out.println("pageable users:" + JsonUtil.GSON.toJson(pageableUsers));

            userRepository.deleteAll();
            System.out.println("user delete");
            users = userRepository.findAll();
            System.out.println("users:" + JsonUtil.GSON.toJson(users));


        } catch (BusException e) {
            logger.error("userMock 异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("userMock 异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    @PostMapping(value = "xss")
    @ResponseBody
    public ResultBean xss(String modelId, String modelJson) {
        ResultBean resultBean = ResultBean.getInstance();
        logger.debug("xss params modelId:[{}]  modelJson:[{}]", modelId, modelJson);
        resultBean.setMessage("xss");
        resultBean.addData("modelId", modelId);
        resultBean.addData("modelJson", modelJson);
        resultBean.addData("modelJsonObj", JsonUtil.GSON.fromJson(modelJson, Map.class));
        Map<String, Object> map = new HashMap<>();
        map.put("str", "string");
        map.put("date", new Date());
        map.put("int", 1);
        String defaultModelJson = JsonUtil.GSON.toJson(map);
        resultBean.addData("defaultModelJson", defaultModelJson);
        return resultBean;
    }

    @PostMapping(value = "mail-mock")
    @ResponseBody
    public ResultBean mailMock(String tos, String subject, String content, Boolean isHtml) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        String result = "";
        boolean isSuccess = false;
        try {
            if (!StringUtils.hasText(tos)) {
                throw new BusException("tos is nil");
            }
            if (!StringUtils.hasText(subject)) {
                throw new BusException("subject is nil");
            }
            subject = subject + " " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            if (!StringUtils.hasText(content)) {
                throw new BusException("content is nil");
            }
            if (isHtml == null) {
                isHtml = false;
            }
            // tos 转 to list
            List<String> toList = Arrays.stream(tos.split(","))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            result = MailUtil.send(toList, subject, content, isHtml);
            isSuccess = true;
            resultBean.setSuccess(isSuccess);
        } catch (BusException e) {
            logger.error("mailMock 异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("mailMock 异常", e);
            message = "系统异常";
        }
        resultBean.addData("result", result);
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


}
