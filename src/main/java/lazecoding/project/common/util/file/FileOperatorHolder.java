package lazecoding.project.common.util.file;

import lazecoding.project.common.util.BeanUtil;

/**
 * FileExecutor
 *
 * @author lazecoding
 */
public class FileOperatorHolder {

    /**
     * 文件操作类型配置
     */
    private static final FileProperties fileProperties = BeanUtil.getBean(FileProperties.class);

    /**
     * 实例内部类
     */
    private static final class InstanceHolder {
        /**
         * 实例
         */
        private static final FileOperator INSTANCE;

        static {
            if (MinioFileOperator.TYPE.equals(fileProperties.getOperator())) {
                INSTANCE = BeanUtil.getBean(MinioFileOperator.BEAN_NAME, FileOperator.class);
            } else {
                INSTANCE = BeanUtil.getBean(LocalFileOperator.BEAN_NAME, FileOperator.class);
            }
        }
    }

    /**
     * 获取 CaffeineCache 实例
     */
    public static FileOperator getInstance() {
        return FileOperatorHolder.InstanceHolder.INSTANCE;
    }

    /**
     * 私有构造
     */
    private FileOperatorHolder() {
    }
}
