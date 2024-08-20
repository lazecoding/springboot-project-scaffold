package lazecoding.project.common.util.file;

import java.io.File;
import java.io.InputStream;

/**
 * 文件操作
 *
 * @author lazecoding
 */
public interface FileOperator {

    /**
     * 是否存在
     *
     * @param path 路径
     */
    boolean exists(String path);

    /**
     * 是否是文件
     *
     * @param path 路径
     */
    boolean isFile(String path);

    /**
     * 是否是文件夹
     *
     * @param path 路径
     */
    boolean isDirectory(String path);

    /**
     * 修改文件名
     *
     * @param path    路径
     * @param newName 新名称
     */
    boolean rename(String path, String newName);

    /**
     * 文件拷贝
     *
     * @param srcPath  源文件地址
     * @param destPath 目标文件地址
     */
    boolean copy(String srcPath, String destPath);

    /**
     * 删除文件
     *
     * @param path 路径
     */
    boolean delete(String path);

    /**
     * 上传文件
     *
     * @param path        文件路径
     * @param inputStream 文件流
     */
    boolean upload(String path, InputStream inputStream);

    /**
     * 上传文件
     *
     * @param path      文件路径
     * @param fileBytes 文件字节
     */
    boolean upload(String path, byte[] fileBytes);

    /**
     * 获取文件流
     *
     * @param path 文件地址
     */
    InputStream getInputStream(String path);

    /**
     * 获取文件
     *
     * @param path 文件地址
     */
    File getFile(String path);


}
