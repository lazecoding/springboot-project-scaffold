package lazecoding.project.common.util.file;

import cn.hutool.core.io.FileUtil;
import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.http.Method;
import lazecoding.project.common.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Minio 文件操作
 *
 * @author lazecoding
 */
@Component
public class MinioFileOperator implements FileOperator {

    private final static Logger logger = LoggerFactory.getLogger(MinioFileOperator.class);

    // 配置
    private final MinioConfig minioConfig = BeanUtil.getBean(MinioConfig.class);

    // 客户端
    private final MinioClient minioClient = BeanUtil.getBean(MinioClient.class);

    /**
     * bucket 是否存在
     **/
    private boolean bucketExists() {
        String bucket = minioConfig.getBucket();
        if (!StringUtils.hasText(bucket)) {
            return false;
        }
        boolean exists = false;
        try {
            exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                exists = true;
            }
        } catch (Exception e) {
            logger.error("bucketExists OR makeBucket Exception", e);
        }
        return exists;
    }

    /**
     * 是否存在
     *
     * @param path 路径
     */
    @Override
    public boolean exists(String path) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return false;
        }
        boolean exists = false;
        try {
            StatObjectResponse statObjectResponse =
                    minioClient.statObject(
                            StatObjectArgs.builder().bucket(minioConfig.getBucket()).object(path).build());
            logger.debug("path:[{}] statObject:[{}]", path, statObjectResponse.toString());
            exists = true;
        } catch (Exception e) {
            logger.error("statObject Exception", e);
        }
        return exists;
    }

    /**
     * 是否是文件
     *
     * @param path 路径
     */
    @Override
    public boolean isFile(String path) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return false;
        }
        boolean isFile = false;
        try {
            StatObjectResponse statObjectResponse =
                    minioClient.statObject(
                            StatObjectArgs.builder().bucket(minioConfig.getBucket()).object(path).build());
            logger.debug("path:[{}] statObject:[{}]", path, statObjectResponse.toString());
            // 对象存在，如果以 / 结尾则是文件夹，反之则是文件
            isFile = !path.endsWith("/");
        } catch (Exception e) {
            logger.error("statObject Exception", e);
        }
        return isFile;
    }

    /**
     * 是否是文件夹
     *
     * @param path 路径
     */
    @Override
    public boolean isDirectory(String path) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return false;
        }
        boolean isDirectory = false;
        try {
            StatObjectResponse statObjectResponse =
                    minioClient.statObject(
                            StatObjectArgs.builder().bucket(minioConfig.getBucket()).object(path).build());
            logger.debug("path:[{}] statObject:[{}]", path, statObjectResponse.toString());
            // 对象存在，如果以 / 结尾则是文件夹，反之则是文件
            isDirectory = path.endsWith("/");
        } catch (Exception e) {
            logger.error("statObject Exception", e);
        }
        return isDirectory;
    }

    /**
     * 修改文件名
     *
     * @param path    路径
     * @param newName 新名称
     */
    @Override
    public boolean rename(String path, String newName) {
        // 禁止修改文件名称
        return false;
    }

    /**
     * 文件拷贝
     *
     * @param srcPath  源文件地址
     * @param destPath 目标文件地址
     */
    @Override
    public boolean copy(String srcPath, String destPath) {
        srcPath = substringPath(srcPath);
        destPath = substringPath(destPath);
        if (!this.bucketExists()) {
            return false;
        }
        boolean isCopy = false;
        try {
            ObjectWriteResponse objectWriteResponse = minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(minioConfig.getBucket()).object(destPath)
                            .source(CopySource.builder().bucket(minioConfig.getBucket()).object(srcPath).build())
                            .build()
            );
            logger.debug("srcPath:[{}] destPath:[{}] objectWriteResponse:[{}]",
                    srcPath, destPath, objectWriteResponse.toString());
            isCopy = true;
        } catch (Exception e) {
            logger.error("copyObject Exception", e);
        }
        return isCopy;
    }

    /**
     * 删除文件
     *
     * @param path 路径
     */
    @Override
    public boolean delete(String path) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return false;
        }
        boolean isDelete = false;
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(minioConfig.getBucket()).object(path).build()
            );
            isDelete = true;
        } catch (Exception e) {
            logger.error("removeObject Exception", e);
        }
        return isDelete;
    }

    /**
     * 上传文件
     *
     * @param path        文件路径
     * @param inputStream 文件流
     */
    @Override
    public boolean upload(String path, InputStream inputStream) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return false;
        }
        boolean isUpload = false;
        try {
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(path)
                    .stream(inputStream, -1, 10485760)
                    .contentType(getMimeType(path))
                    .build());
            logger.debug("path:[{}] objectWriteResponse:[{}]", path, objectWriteResponse.toString());
            isUpload = true;
        } catch (Exception e) {
            logger.error("putObject Exception", e);
        }
        return isUpload;
    }

    /**
     * 上传文件
     *
     * @param path      文件路径
     * @param fileBytes 文件字节
     */
    @Override
    public boolean upload(String path, byte[] fileBytes) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return false;
        }
        boolean isUpload = false;
        try {
            ObjectWriteResponse objectWriteResponse =
                    minioClient.putObject(
                            PutObjectArgs.builder()
                                    .bucket(minioConfig.getBucket())
                                    .object(path)
                                    .stream(new ByteArrayInputStream(fileBytes), 0, -1)
                                    .contentType(getMimeType(path))
                                    .build()
                    );
            logger.debug("path:[{}] objectWriteResponse:[{}]", path, objectWriteResponse.toString());
            isUpload = true;
        } catch (Exception e) {
            logger.error("putObject Exception", e);
        }
        return isUpload;
    }

    /**
     * 获取文件流
     *
     * @param path 文件地址
     */
    @Override
    public InputStream getInputStream(String path) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioConfig.getBucket())
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            logger.error("getObject Exception", e);
        }
        return inputStream;
    }

    /**
     * 获取文件
     * 非本地文件，无法直接读取，使用 minio 读取文件流使用 getInputStream
     *
     * @param path 文件地址
     */
    @Override
    public File getFile(String path) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return null;
        }
        try {
            StatObjectResponse statObjectResponse =
                    minioClient.statObject(
                            StatObjectArgs.builder().bucket(minioConfig.getBucket()).object(path).build());
            logger.debug("path:[{}] statObject:[{}]", path, statObjectResponse.toString());
            return new File(path);
        } catch (Exception e) {
            logger.error("statObject Exception", e);
        }
        return null;
    }

    /**
     * 获取相对预览地址
     *
     * @param path 文件地址
     */
    @Override
    public String getPreviewPath(String path) {
        path = substringPath(path);
        if (!this.bucketExists()) {
            return "";
        }
        String previewPath = "";
        try {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("response-content-type", getMimeType(path));
            previewPath = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(path)
                    .expiry(1, TimeUnit.DAYS)
                    .method(Method.GET)
                    .extraQueryParams(queryParams)
                    .build());
            previewPath = URLDecoder.decode(previewPath, StandardCharsets.UTF_8);
            if (StringUtils.hasText(previewPath)) {
                // 替换成相对地址
                previewPath = previewPath.replaceFirst(minioConfig.getEndpoint(), "");
            }
        } catch (Exception e) {
            logger.error("statObject Exception", e);
        }
        return previewPath;
    }


    /**
     * 去掉路径开头的 /
     *
     * @param path 文件路径
     */
    private String substringPath(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

    /**
     * 获取 mimeType
     *
     * @param path 文件路径
     */
    private String getMimeType(String path) {
        String mimeType = "";
        mimeType = FileUtil.getMimeType(path);
        if (!StringUtils.hasText(mimeType)) {
            // 二进制流
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }
}
