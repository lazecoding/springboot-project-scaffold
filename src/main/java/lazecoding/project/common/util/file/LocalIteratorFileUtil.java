package lazecoding.project.common.util.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LocalIteratorFileUtil
 *
 * @author lazecoding
 */
public class LocalIteratorFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(LocalIteratorFileUtil.class);

    /**
     * 如果不存在就创建文件
     *
     * @param path
     * @return
     */
    public static boolean createIfNil(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                boolean isDir = path.endsWith("/") || path.endsWith("\\");
                if (isDir) {
                    return file.mkdirs();
                } else {
                    return file.createNewFile();
                }
            } catch (IOException e) {
                logger.error("createDirIfNil 异常", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 列出目录下一层所有文件
     */
    public static List<File> list(String path) {
        File dir = new File(path);
        // 列出所有文件（不递归）
        return (List<File>) FileUtils.listFiles(dir, null, false);
    }

    /**
     * 列出目录下一层所有文件夹
     */
    public static List<File> listDirs(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return null;
        }
        // 列出所有文件（不递归）
        File[] files = dir.listFiles();
        if (ObjectUtils.isEmpty(files) || files.length == 0) {
            return null;
        }
        return Arrays.stream(files).filter(File::isDirectory).collect(Collectors.toList());
    }

    public static void lineIterator(String path, int batch, LineProcessor lineProcessor) {
        lineIterator(new File(path), batch, lineProcessor);
    }

    /**
     * 按行读取
     */
    public static void lineIterator(File file, int batch, LineProcessor lineProcessor) {
        LineIterator lineIterator = null;

        try {
            lineIterator = FileUtils.lineIterator(file);
            iterator(lineIterator, batch, lineProcessor);
        } catch (Exception e) {
            logger.error("lineIterator iterator error", e);
        } finally {
            try {
                lineIterator.close();
            } catch (Exception e) {
                logger.error("lineIterator close error", e);
            }

        }

    }

    /**
     * 按行读取
     */
    public static void lineIterator(InputStream inputStream, int batch, LineProcessor lineProcessor) {
        LineIterator lineIterator = IOUtils.lineIterator(inputStream, "utf-8");
        iterator(lineIterator, batch, lineProcessor);
    }

    private static void iterator(LineIterator lineIterator, int batch, LineProcessor lineProcessor) {
        ArrayList list = new ArrayList(1);
        while (lineIterator.hasNext()) {
            String lineContent = lineIterator.next();
            list.add(lineContent);
            if (list.size() >= batch) {
                lineProcessor.readLines(list);
                list.clear();
            }
        }

        if (!CollectionUtils.isEmpty(list)) {
            lineProcessor.readLines(list);
            list.clear();
        }

    }

    /**
     * 行处理接口
     */
    public interface LineProcessor {
        void readLines(List<String> list);
    }

    /**
     * 递归删除
     */
    public static boolean forceDelete(String path) {
        boolean isSuccess = false;
        try {
            FileUtils.forceDelete(new File(path));
            isSuccess = true;
        } catch (FileNotFoundException e) {
            isSuccess = true;
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (NoSuchFileException x) {
            isSuccess = true;
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 删除文件或目录
     *
     * @param path
     */
    public static boolean delete(String path) {
        boolean isSuccess = false;
        try {
            Files.delete(Paths.get(path));
            isSuccess = true;
        } catch (NoSuchFileException x) {
            isSuccess = true;
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
            isSuccess = false;
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 写内容到文件里
     */
    public static boolean writeStringToFile(String path, String content) {
        return writeStringToFile(path, content, false);
    }

    /**
     * 写内容到文件里
     */
    public static boolean writeStringToFile(String path, String content, boolean append) {
        boolean isSuccess = false;
        try {
            OutputStream out = null;
            try {
                out = openOutputStream(new File(path), append);
                write(content, out, StandardCharsets.UTF_8);
                out.close();
            } finally {
                closeQuietly(out);
            }
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    private static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }

        return new FileOutputStream(file, append);
    }

    private static void write(String data, OutputStream output, Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(encoding));
        }

    }

    private static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {
        }
    }
}
