package lazecoding.project.util;

import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * 文件合并
 *
 * @author lazecoding
 */
public class MergeFileUtil {

    /**
     * 文件合并
     *
     * @param srcFilePaths 源文件地址数组
     * @param destPath     目的文件地址
     * @return
     */
    public static boolean mergeFiles(String[] srcFilePaths, String destPath) {
        if (srcFilePaths == null || srcFilePaths.length < 1 || !StringUtils.hasText(destPath)) {
            return false;
        }
        if (srcFilePaths.length == 1) {
            return new File(srcFilePaths[0]).renameTo(new File(destPath));
        }

        File[] files = new File[srcFilePaths.length];
        for (int i = 0; i < srcFilePaths.length; i++) {
            files[i] = new File(srcFilePaths[i]);
            if (!StringUtils.hasText(srcFilePaths[i])) {
                return false;
            }
            if (!files[i].exists()) {
                return false;
            }
            if (!files[i].isFile()) {
                return false;
            }
        }

        File destFile = new File(destPath);
        FileChannel resultFileChannel = null;
        FileChannel blk = null;
        try {
            resultFileChannel = new FileOutputStream(destFile, true).getChannel();
            for (int i = 0; i < srcFilePaths.length; i++) {
                try {
                    blk = new FileInputStream(files[i]).getChannel();
                    resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    if (blk != null) {
                        try {
                            blk.close();
                        } catch (IOException e) {
                            // do nothing
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (resultFileChannel != null) {
                try {
                    resultFileChannel.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        // 暂时不删除
        /*for (int i = 0; i < fpaths.length; i++) {
            files[i].delete();
        }*/

        return true;
    }

}
