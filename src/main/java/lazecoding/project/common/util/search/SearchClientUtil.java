package lazecoding.project.common.util.search;

import lazecoding.project.common.util.BeanUtil;
import lazecoding.project.common.util.cache.RedissonClientUtil;
import lazecoding.project.config.ElasticSearchConfig;
import lazecoding.project.config.model.Address;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchClientUtil
 *
 * @author lazecoding
 */
public class SearchClientUtil {


    private static final Logger logger = LoggerFactory.getLogger(SearchClientUtil.class);

    /**
     * 客户端
     */
    private static RestHighLevelClient restHighLevelClient;

    /**
     * ElasticSearch 配置
     */
    private static final ElasticSearchConfig ELASTIC_SEARCH_CONFIG = BeanUtil.getBean(ElasticSearchConfig.class);

    /**
     * 私有构造函数
     */
    private SearchClientUtil() {
    }

    /**
     * 获取 RestClient
     */
    public static RestHighLevelClient getRestClient() {
        if (ObjectUtils.isEmpty(restHighLevelClient)) {
            synchronized (RedissonClientUtil.class) {
                if (ObjectUtils.isEmpty(restHighLevelClient)) {
                    if (ObjectUtils.isEmpty(ELASTIC_SEARCH_CONFIG)) {
                        logger.info("project.search is nil");
                        return null;
                    }
                    List<Address> addresses = ELASTIC_SEARCH_CONFIG.getAddresses();
                    if (CollectionUtils.isEmpty(addresses)) {
                        logger.info("project.search.addresses is nil");
                        return null;
                    }
                    List<HttpHost> hostList = new ArrayList<>();
                    for (Address address : addresses) {
                        hostList.add(new HttpHost(address.getIp(), address.getPort(), "http"));
                    }
                    RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
                    CredentialsProvider credentialsProvider = null;
                    // 账号密码
                    if (StringUtils.hasText(ELASTIC_SEARCH_CONFIG.getUsername()) && StringUtils.hasText(ELASTIC_SEARCH_CONFIG.getPassword())) {
                        credentialsProvider = new BasicCredentialsProvider();
                        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ELASTIC_SEARCH_CONFIG.getUsername(), ELASTIC_SEARCH_CONFIG.getPassword()));
                    }

                    // 构建器
                    CredentialsProvider finalCredentialsProvider = credentialsProvider;
                    builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            httpClientBuilder.disableAuthCaching();
                            if (!ObjectUtils.isEmpty(finalCredentialsProvider)) {
                                httpClientBuilder.setDefaultCredentialsProvider(finalCredentialsProvider);
                            }
                            return httpClientBuilder;
                        }
                    });
                    restHighLevelClient = new RestHighLevelClient(builder);
                }
            }
        }
        return restHighLevelClient;
    }


}
