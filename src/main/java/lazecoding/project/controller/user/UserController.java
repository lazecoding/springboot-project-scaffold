package lazecoding.project.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.util.page.PageParam;
import lazecoding.project.common.model.user.UserAddParam;
import lazecoding.project.common.model.user.UserListParam;
import lazecoding.project.common.model.user.UserVo;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.page.ProcessedPage;
import lazecoding.project.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author lazecoding
 */
@Tag(name = "用户", description = "操作用户属性")
@RestController
@RequestMapping("manager/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(summary = "新增用户", description = "新增用户")
    @ApiResponse(
            responseCode = "200", description = "成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResultBean.class))}
    )
    @PostMapping(value = "add")
    @ResponseBody
    public ResultBean add(@RequestBody UserAddParam userAddParam) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = userService.add(userAddParam);
        } catch (BusException e) {
            logger.error("新增用户异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("新增用户异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    @Operation(summary = "用户列表", description = "用户列表")
    @ApiResponse(
            responseCode = "200", description = "成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResultBean.class))}
    )
    @PostMapping(value = "list")
    @ResponseBody
    public ResultBean list(@ParameterObject UserListParam userAddParam, @ParameterObject PageParam pageParam) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            ProcessedPage<UserVo> processedPage = userService.list(userAddParam, pageParam);
            resultBean.addData("page", processedPage);
            isSuccess = true;
        } catch (BusException e) {
            logger.error("用户列表异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("用户列表异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


}
