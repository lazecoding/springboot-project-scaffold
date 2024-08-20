package lazecoding.project.common.util.file;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * 本地文件操作
 *
 * @author lazecoding
 */
public class LocalFileOperator implements FileOperator {


    private static final Logger logger = LoggerFactory.getLogger(LocalFileOperator.class);

    /**
     * 是否存在
     *
     * @param path 路径
     */
    @Override
    public boolean exists(String path) {
        return FileUtil.exist(path);
    }

    /**
     * 是否是文件
     *
     * @param path 路径
     */
    @Override
    public boolean isFile(String path) {
        return FileUtil.isFile(path);
    }

    /**
     * 是否是文件夹
     *
     * @param path 路径
     */
    @Override
    public boolean isDirectory(String path) {
        return FileUtil.isDirectory(path);
    }

    /**
     * 修改文件名
     *
     * @param path    路径
     * @param newName 新名称
     */
    @Override
    public boolean rename(String path, String newName) {
        File file = FileUtil.rename(new File(path), newName, false);
        return FileUtil.exist(file);
    }

    /**
     * 文件拷贝
     *
     * @param srcPath  源文件地址
     * @param destPath 目标文件地址
     */
    @Override
    public boolean copy(String srcPath, String destPath) {
        File file = FileUtil.copy(new File(srcPath), new File(destPath), true);
        return FileUtil.exist(file);
    }

    /**
     * 删除文件和目录
     *
     * @param path 路径
     */
    @Override
    public boolean delete(String path) {
        return FileUtil.del(path);
    }

    /**
     * 上传文件
     *
     * @param path        文件路径
     * @param inputStream 文件流
     */
    @Override
    public boolean upload(String path, InputStream inputStream) {
        File file = FileUtil.writeFromStream(inputStream, new File(path));
        return FileUtil.exist(file);
    }

    /**
     * 上传文件
     *
     * @param path      文件路径
     * @param fileBytes 文件字节
     */
    @Override
    public boolean upload(String path, byte[] fileBytes) {
        File file = FileUtil.writeBytes(fileBytes, path);
        return FileUtil.exist(file);
    }

    /**
     * 获取文件流
     *
     * @param path 文件地址
     */
    @Override
    public InputStream getInputStream(String path) {
        return FileUtil.getInputStream(path);
    }

    /**
     * 获取文件
     *
     * @param path 文件地址
     */
    @Override
    public File getFile(String path) {
        return new File(path);
    }


}

