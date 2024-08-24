package lazecoding.project.mock;

import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.security.annotation.RequireRoles;
import lazecoding.project.common.util.security.constant.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("class")
@RequireRoles({Role.ADMIN, Role.ORGANISER})
public class ClassAopController {

    // http://localhost:9977/class/ping
    @GetMapping(value = "ping")
    @ResponseBody
    public ResultBean ping() {
        ResultBean resultBean = ResultBean.getInstance();
        resultBean.setMessage("pong");
        return resultBean;
    }

}
