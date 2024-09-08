package lazecoding.project.mock;

import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.concurrent.Locker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mock/lock")
public class LockMockController {

    @GetMapping(value = "ping")
    @ResponseBody
    public ResultBean ping() {
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

}
