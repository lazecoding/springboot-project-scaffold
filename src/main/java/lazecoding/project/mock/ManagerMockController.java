package lazecoding.project.mock;


import lazecoding.project.common.mvc.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * MockController
 *
 * @author lazecoding
 */
@RestController
@RequestMapping("manager/mock")
public class ManagerMockController {

    // http://localhost:9977/manager/mock/ping
    @GetMapping(value = "ping")
    @ResponseBody
    public ResultBean ping() {
        ResultBean resultBean = ResultBean.getInstance();
        resultBean.setMessage("pong");
        return resultBean;
    }
}
