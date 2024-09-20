package lazecoding.project.mock;

import cn.hutool.core.io.FileUtil;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.BeanUtil;
import lazecoding.project.common.util.file.FileOperator;
import lazecoding.project.common.util.file.FileOperatorHolder;
import lazecoding.project.common.util.file.MinioConfig;
import lazecoding.project.common.util.file.MinioFileOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.InputStream;

@RestController
@RequestMapping("file/mock")
public class MinioMockController {

    private static final Logger logger = LoggerFactory.getLogger(MockController.class);

    @Resource
    private MinioConfig minioConfig;

    // http://localhost:9977/file/mock/minio-linux
    @GetMapping(value = "minio-linux")
    @ResponseBody
    public ResultBean minioLinux() {
        ResultBean resultBean = ResultBean.getInstance();
        resultBean.setMessage("minio");
        FileOperator fileOperator = new MinioFileOperator();
        try {
            System.out.println(minioConfig);
            String originPath = "/media/lazy/D/downloads/A.png";
            String destPath = "/media/lazy/D/downloads/A1.png";
            InputStream originInputStream = FileUtil.getInputStream(originPath);
            System.out.println(originInputStream);
            System.out.println("originPath mimetype:" + FileUtil.getMimeType(originPath));
            System.out.println("start ========");
            System.out.println("originPath exists:" + originPath + ":" + fileOperator.exists(originPath));
            System.out.println("destPath exists:" + destPath + ":" + fileOperator.exists(destPath));
            System.out.println("upload ========");
            System.out.println("upload:" + fileOperator.upload(originPath, originInputStream));
            System.out.println("originPath exists:" + originPath + ":" + fileOperator.exists(originPath));

            System.out.println("copy ========");
            fileOperator.copy(originPath, destPath);
            System.out.println("originPath exists:" + originPath + ":" + fileOperator.exists(originPath));
            System.out.println("destPath exists:" + destPath + ":" + fileOperator.exists(destPath));
            // 读取本地文件
            System.out.println("delete ===========");
            System.out.println("delete:" + destPath + ":" + fileOperator.delete(destPath));
            System.out.println("destPath exists:" + destPath + ":" + fileOperator.exists(destPath));
            String previewPath = fileOperator.getPreviewPath(originPath);
            resultBean.addData("previewPath", minioConfig.getEndpoint() + previewPath);
        } catch (Exception e) {
            logger.error("minio error", e);
        }
        return resultBean;
    }

    // http://localhost:9977/file/mock/minio-windows
    @GetMapping(value = "minio-windows")
    @ResponseBody
    public ResultBean minioWindows() {
        ResultBean resultBean = ResultBean.getInstance();
        resultBean.setMessage("minio");
        FileOperator fileOperator = new MinioFileOperator();
        try {
            System.out.println(minioConfig);
            String originPath = "E:/deveoper/envs/minio/temp/A.jpg";
            String minioPath = "minio/A.jpg";
            InputStream originInputStream = FileUtil.getInputStream(originPath);
            System.out.println(originInputStream);
            System.out.println("upload ========");
            System.out.println("upload:" + fileOperator.upload(minioPath, originInputStream));
            System.out.println("minioPath exists:" + minioPath + ":" + fileOperator.exists(minioPath));
            String previewPath = fileOperator.getPreviewPath(minioPath);
            resultBean.addData("previewPath", minioConfig.getEndpoint() + previewPath);
        } catch (Exception e) {
            logger.error("minio error", e);
        }
        return resultBean;
    }

    @GetMapping(value = "operator")
    @ResponseBody
    public ResultBean operator() {
        ResultBean resultBean = ResultBean.getInstance();
        FileOperator fileOperator = FileOperatorHolder.getInstance();
        System.out.println(fileOperator);
        return resultBean;
    }

}
