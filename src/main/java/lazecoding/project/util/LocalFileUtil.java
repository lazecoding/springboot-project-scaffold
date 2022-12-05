package lazecoding.project.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * LocalFileUtil
 *
 * @author lazecoding
 */
public class LocalFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileUtil.class);

    public static void lineIterator(String path, int batch, LocalFileUtil.LineProcessor lineProcessor) {
        lineIterator(new File(path), batch, lineProcessor);
    }

    /**
     * 按行读取
     */
    public static void lineIterator(File file, int batch, LocalFileUtil.LineProcessor lineProcessor) {
        new ArrayList(1);
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
    public static void lineIterator(InputStream inputStream, int batch, LocalFileUtil.LineProcessor lineProcessor) {
        LineIterator lineIterator = IOUtils.lineIterator(inputStream, "utf-8");
        iterator(lineIterator, batch, lineProcessor);
    }

    private static void iterator(LineIterator lineIterator, int batch, LocalFileUtil.LineProcessor lineProcessor) {
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
}
