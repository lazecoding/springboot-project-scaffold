package lazecoding.project.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.service.user.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 注册用户
 *
 * @author lazecoding
 */
@Tag(name = "注册", description = "用户注册")
@RestController
@RequestMapping("sys")
public class RegisterController {


    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;



    @Operation(summary = "获取注册验证码", description = "获取注册验证码")
    @ApiResponse(
            responseCode = "200", description = "成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResultBean.class))}
    )
    @Parameter(name = "phone", description = "手机号", example = "15011112222", required = true)
    @PostMapping(value = "phone-register-sms")
    @ResponseBody
    public ResultBean phoneRegisterSms(String phone) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = registerService.phoneRegisterSms(phone);
        } catch (BusException e) {
            logger.error("获取注册验证码异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("获取注册验证码异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    @Operation(summary = "注册用户", description = "注册用户")
    @ApiResponse(
            responseCode = "200", description = "成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResultBean.class))}
    )
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15011112222", required = true),
            @Parameter(name = "code", description = "验证码", example = "1234", required = true)
    })
    @PostMapping(value = "phone-register-do")
    @ResponseBody
    public ResultBean phoneRegisterDo(String phone, String code) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = registerService.phoneRegisterDo(phone, code);
        } catch (BusException e) {
            logger.error("注册用户异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("注册用户异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }
}
