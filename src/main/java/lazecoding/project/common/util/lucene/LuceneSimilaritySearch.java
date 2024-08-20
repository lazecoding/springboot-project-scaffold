package lazecoding.project.common.util.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;

/**
 * 相似度检索
 *
 * @author lazecoding
 */
public class LuceneSimilaritySearch {

    public static void main(String[] args) {
        // 创建内存型索引
        Directory index = new ByteBuffersDirectory();

        try (IndexWriter writer = new IndexWriter(index, new IndexWriterConfig(new StandardAnalyzer()))) {
            // 添加文档到索引
            Document doc = new Document();
            // 仅存储
            doc.add(new StoredField("id", "id-1"));
            // 检索 + 存储
            doc.add(new TextField("content", "Lucene", Field.Store.YES));
            writer.addDocument(doc);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try {
            // 构建查询
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            Query query = parser.parse("Lucene Java.");

            // 执行查询
            try (IndexReader indexReader = DirectoryReader.open(index)) {
                IndexSearcher searcher = new IndexSearcher(indexReader);
                TopDocs results = searcher.search(query, 10);

                // 输出查询结果
                for (ScoreDoc scoreDoc : results.scoreDocs) {
                    System.out.println("Score: " + scoreDoc.score + ", Document: " + searcher.doc(scoreDoc.doc));
                }
            }
        } catch (IOException | ParseException e) {
            System.err.println(e.getMessage());
        }
    }
}
