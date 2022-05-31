package lazecoding.project.controller;

import lazecoding.project.mvc.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * ProjectController
 *
 * @author lazecoding
 */
@RestController
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    /**
     * 申请号段（自定义）
     */
    @RequestMapping(value = "/api/project/find/{namespace}", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean apply(@PathVariable("namespace") String namespace) {
        boolean isSuccess = false;
        ResultBean resultBean = new ResultBean();
        String message = "";
        try {
            resultBean.setValue(StringUtils.hasText(namespace) ? namespace : "null");
            isSuccess = true;
            message = "获取成功";
        } catch (Exception e) {
            isSuccess = false;
            logger.error("接口:[/api/project/find/{namespace}] 获取失败", e);
            message = "系统异常，获取失败";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

}
