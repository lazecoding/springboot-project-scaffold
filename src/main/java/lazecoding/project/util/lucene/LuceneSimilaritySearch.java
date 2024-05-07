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


/**
 * 相似度检索
 *
 * @author lazecoding
 */
public class LuceneSimilaritySearch {

    public static void main(String[] args) throws Exception {
        // 创建内存型索引
        Directory index = new ByteBuffersDirectory();

        // 创建IndexWriter
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(index, config);

        // 添加文档到索引
        addDocument(writer, "1", "Lucene is a full-text search library.");
        addDocument(writer, "2", "Lucene is written in Java.");
        addDocument(writer, "3", "Java is written in Lucene.");
        addDocument(writer, "4", "Lucene is written in Golang.");
        addDocument(writer, "5", "Lucene is powerful.");
        addDocument(writer, "6", "PHP is Good");

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
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.println("Score: " + scoreDoc.score + ", Document: " + doc);
        }
    }

    /**
     * 添加文档
     */
    private static void addDocument(IndexWriter writer, String id, String content) throws Exception {
        Document doc = new Document();
        // 仅存储
        doc.add(new StoredField("id", id));
        // 检索 + 存储
        doc.add(new TextField("content", content, Field.Store.YES));
        writer.addDocument(doc);
    }
}
