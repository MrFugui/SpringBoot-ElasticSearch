## 啦啦啦啦啦，富贵同学又开始开坑了，出了个免费的专栏，主要给大家从0基础开始用springBoot集成第三方的插件或者功能，如果这篇专栏能帮到你，一定不要忘了点一个赞哦！！欢迎大家收藏分享
![在这里插入图片描述](https://img-blog.csdnimg.cn/385dc942abfc4a019d845055328814c1.png#pic_center)
**如果还有朋友还没有安装ElasticSearch 的，请先移步到我的这篇文章**
[Linux安装ElasticSearch以及Ik分词器](https://blog.csdn.net/csdnerM/article/details/121509681)
## 第一步，导入jar包，注意这里的jar包版本可能和你导入的不一致，所以需要修改

```java
 	<properties>
        <java.version>1.8</java.version>
        <elasticsearch.version>7.6.2</elasticsearch.version>
    </properties>
```

```sql
		<!-- elasticsearch -->
        <!--es客户端-->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>7.6.2</version>
        </dependency>

        <!--springboot的elasticsearch服务-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>

```
## 第二步，编写配置类

```java
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClientConfig {

    @Value("${elasticSearch.hostname}")
    private String hostname;

    @Value("${elasticSearch.port}")
    private Integer port;

    @Value("${elasticSearch.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostname,port,scheme))
        );
    }
}

```
## 第三步，填写yml

```java
elasticSearch:
  hostname: 127.0.0.1
  port: 9200
  scheme: http
```
## 第四步，编写util类

```java

import com.alibaba.fastjson.JSON;
import com.wangfugui.apprentice.dao.domain.Dynamic;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/11/25
 * @since JDK 1.8.0
 */
@Component
@Slf4j
public class ElasticSearchUtil {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    //索引的创建
    public CreateIndexResponse createIndex(String index) throws IOException {
        //1.创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        //2客户端执行请求，请求后获得响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        log.info("索引的创建{}", response);
        return response;
    }

    //索引是否存在
    public Boolean existIndex(String index) throws IOException {
        //1.创建索引的请求
        GetIndexRequest request = new GetIndexRequest(index);
        //2客户端执行请求，请求后获得响应
        boolean exist = client.indices().exists(request, RequestOptions.DEFAULT);
        log.info("索引是否存在-----" + exist);
        return exist;
    }

    //删除索引
    public Boolean deleteIndex(String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        log.info("删除索引--------" + delete.isAcknowledged());
        return delete.isAcknowledged();
    }

    //添加文档
    public IndexResponse addDocument(Dynamic dynamic, String index) throws IOException {
        IndexRequest request = new IndexRequest(index);
        //设置超时时间
        request.timeout("1s");
        //将数据放到json字符串
        request.source(JSON.toJSONString(dynamic), XContentType.JSON);
        //发送请求
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        log.info("添加文档-------" + response.toString());
        log.info("添加文档-------" + response.status());
        return response;
    }

    //文档是否存在
    public Boolean existDocument(String index, String documents) throws IOException {
        //文档的 没有index
        GetRequest request = new GetRequest(index, documents);
        //没有indices()了
        boolean exist = client.exists(request, RequestOptions.DEFAULT);
        log.info("文档是否存在-----" + exist);
        return exist;
    }

    //获取文档
    public GetResponse getDocument(String index, String documents) throws IOException {
        GetRequest request = new GetRequest(index, documents);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        log.info("获取文档-----" + response.getSourceAsString());
        log.info("获取文档-----" + response);
        return response;
    }

    //修改文档
    public UpdateResponse updateDocument(Dynamic dynamic, String index, String documents) throws IOException {

        //修改是id为1的
        UpdateRequest request = new UpdateRequest(index, documents);
        request.timeout("1s");
        request.doc(JSON.toJSONString(dynamic), XContentType.JSON);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        log.info("修改文档-----" + response);
        log.info("修改文档-----" + response.status());

        return response;
    }


    //删除文档
    public RestStatus deleteDocument(String index, String documents) throws IOException {
        DeleteRequest request = new DeleteRequest(index, documents);
        request.timeout("1s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        log.info("删除文档------" + response.status());
        return response.status();
    }

    //批量添加文档
    public BulkResponse bulkAddDocument(List<Dynamic> dynamics) throws IOException {

        //批量操作的Request
        BulkRequest request = new BulkRequest();
        request.timeout("1s");

        //批量处理请求
        for (int i = 0; i < dynamics.size(); i++) {
            request.add(
                    new IndexRequest("lisen_index")
                            .id("" + (i + 1))
                            .source(JSON.toJSONString(dynamics.get(i)), XContentType.JSON)
            );
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        //response.hasFailures()是否是失败的
        log.info("批量添加文档-----" + response.hasFailures());

//        结果:false为成功 true为失败
        return response;
    }


    //查询文档
    public SearchResponse searchDocument(String index) throws IOException {
        SearchRequest request = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置了高亮
        sourceBuilder.highlighter();
        //term name为cyx1的
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "cyx1");
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        log.info("查询文档-----" + JSON.toJSONString(response.getHits()));
        log.info("=====================");
        for (SearchHit documentFields : response.getHits().getHits()) {
            log.info("查询文档--遍历参数--" + documentFields.getSourceAsMap());
        }
        return response;
    }

    public IndexResponse addDocumentId(Dynamic dynamic, String index, String id) throws IOException {
        IndexRequest request = new IndexRequest(index);
        //设置超时时间
        request.id(id);
        //将数据放到json字符串
        request.source(JSON.toJSONString(dynamic), XContentType.JSON);
        //发送请求
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        log.info("添加文档-------" + response.toString());
        log.info("添加文档-------" + response.status());
        return response;
    }
}

```
## 第五步，编写controller类

```java

import com.wangfugui.apprentice.common.util.ElasticSearchUtil;
import com.wangfugui.apprentice.common.util.ResponseUtils;
import com.wangfugui.apprentice.dao.domain.Dynamic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/11/25
 * @since JDK 1.8.0
 */
@RestController
@RequestMapping("/elasticSearch")
@Api(tags = "elasticSearch操作")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchUtil elasticSearchUtil;

    /**索引的创建*/
    @PostMapping("/createIndex")
    @ApiOperation("索引的创建")
    public ResponseUtils createIndex(@RequestParam String index) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.createIndex(index));
    }

    /**索引是否存在*/
    @GetMapping("/existIndex")
    @ApiOperation("索引是否存在")
    public ResponseUtils existIndex(@RequestParam String index) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.existIndex(index));
    }

    /**删除索引*/
    @DeleteMapping("/deleteIndex")
    @ApiOperation("删除索引")
    public ResponseUtils deleteIndex(@RequestParam String index) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.deleteIndex(index));
    }

    /**添加文档*/
    @PostMapping("/addDocument")
    @ApiOperation("添加文档随机id")
    public ResponseUtils addDocument(@RequestBody Dynamic dynamic, @RequestParam String index) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.addDocument(dynamic,index));
    }

    /**添加文档*/
    @PostMapping("/addDocument")
    @ApiOperation("添加文档自定义id")
    public ResponseUtils addDocumentId(@RequestBody Dynamic dynamic, @RequestParam String index,@RequestParam String id) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.addDocumentId(dynamic,index,id));
    }

    /**文档是否存在*/
    @GetMapping("/existDocument")
    @ApiOperation("文档是否存在")
    public ResponseUtils existDocument(@RequestParam String index, @RequestParam String documents) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.existDocument(index,documents));
    }

    /**获取文档*/
    @GetMapping("/getDocument")
    @ApiOperation("获取文档")
    public ResponseUtils getDocument(@RequestParam String index, @RequestParam String documents) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.getDocument(index,documents));
    }

    /**修改文档*/
    @ApiOperation("修改文档")
    @PutMapping("/updateDocument")
    public ResponseUtils updateDocument(@RequestBody Dynamic dynamic, @RequestParam String index, @RequestParam String documents) throws IOException {

        return ResponseUtils.success(elasticSearchUtil.updateDocument(dynamic,index,documents));
    }


    /**删除文档*/
    @ApiOperation("删除文档")
    @DeleteMapping("/deleteDocument")
    public ResponseUtils deleteDocument(@RequestParam String index, @RequestParam String documents) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.deleteDocument(index,documents));
    }

    /**批量添加文档*/
    @ApiOperation("批量添加文档")
    @PostMapping("/bulkAddDocument")
    public ResponseUtils bulkAddDocument(@RequestBody List<Dynamic> dynamics) throws IOException {

        return ResponseUtils.success(elasticSearchUtil.bulkAddDocument(dynamics));
    }


    /**查询文档*/
    @ApiOperation("查询文档")
    @GetMapping("/searchDocument")
    public ResponseUtils searchDocument(@RequestParam String index) throws IOException {
        return ResponseUtils.success(elasticSearchUtil.searchDocument(index));
    }


}

```
## 第六步，测试即可
![在这里插入图片描述](https://img-blog.csdnimg.cn/8a3da0beae184428a73d7038e2270bd6.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
成功！！
好了，就是这么的简单，完整代码请移至[SpringBoot+ElasticSearch ](https://gitee.com/WangFuGui-Ma/spring-boot-elasticSearch)查看
![在这里插入图片描述](https://img-blog.csdnimg.cn/a866736dfb41420f8d8a8484d1e9abb7.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_10,color_FFFFFF,t_70,g_se,x_16#pic_center)
