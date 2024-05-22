package lazecoding.project.util.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
        IndexWriter writer = null;
        try {
            // 创建内存型索引
            Directory index = new ByteBuffersDirectory();

            // 创建IndexWriter
            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
            writer = new IndexWriter(index, config);

            // 添加文档到索引
            Document doc = new Document();
            // 仅存储
            doc.add(new StoredField("id", "id-1"));
            // 检索 + 存储
            doc.add(new TextField("content", "Lucene", Field.Store.YES));
            writer.addDocument(doc);

            // 提交写入
            writer.close();

            // 构建查询
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            Query query = parser.parse("Lucene Java.");

            // 执行查询
            IndexReader indexReader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs results = searcher.search(query, 10);

            // 输出查询结果
            for (ScoreDoc scoreDoc : results.scoreDocs) {
                System.out.println("Score: " + scoreDoc.score + ", Document: " + searcher.doc(scoreDoc.doc));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }


    }

}
