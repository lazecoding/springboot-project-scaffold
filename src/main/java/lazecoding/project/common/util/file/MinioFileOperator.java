package lazecoding.project.common.util.file;

import java.io.File;
import java.io.InputStream;

/**
 *  TODO Minio 文件操作
 *
 * @author lazecoding
 */
public class MinioFileOperator implements FileOperator {

    /**
     * 是否存在
     *
     * @param path 路径
     */
    @Override
    public boolean exists(String path) {
        return false;
    }

    /**
     * 是否是文件
     *
     * @param path 路径
     */
    @Override
    public boolean isFile(String path) {
        return false;
    }

    /**
     * 是否是文件夹
     *
     * @param path 路径
     */
    @Override
    public boolean isDirectory(String path) {
        return false;
    }

    /**
     * 修改文件名
     *
     * @param path    路径
     * @param newName 新名称
     */
    @Override
    public boolean rename(String path, String newName) {
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
        return false;
    }

    /**
     * 删除文件
     *
     * @param path 路径
     */
    @Override
    public boolean delete(String path) {
        return false;
    }

    /**
     * 上传文件
     *
     * @param path        文件路径
     * @param inputStream 文件流
     */
    @Override
    public boolean upload(String path, InputStream inputStream) {
        return false;
    }

    /**
     * 上传文件
     *
     * @param path      文件路径
     * @param fileBytes 文件字节
     */
    @Override
    public boolean upload(String path, byte[] fileBytes) {
        return false;
    }

    /**
     * 获取文件流
     *
     * @param path 文件地址
     */
    @Override
    public InputStream getInputStream(String path) {
        return null;
    }

    /**
     * 获取文件
     *
     * @param path 文件地址
     */
    @Override
    public File getFile(String path) {
        return null;
    }
}
