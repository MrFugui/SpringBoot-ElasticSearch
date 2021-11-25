package com.wangfugui.apprentice;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.wangfugui.apprentice.common.util.RedisUtils;
import com.wangfugui.apprentice.dao.domain.Dynamic;
import com.wangfugui.apprentice.dao.domain.User;
import com.wangfugui.apprentice.dao.mapper.UserMapper;
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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApprenticeApplicationTests {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${base.javaDir}")
    private String javaDir;
    @Value("${base.mapperDir}")
    private String mapperDir;


    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.lambda().eq(User::getUsername,"fugui");
        List<User> user = userMapper.selectList(objectQueryWrapper);
        System.out.println(user);
    }

    /** 生成代码
     * @Param: []
     * @return: void
     * @Author: MaSiyi
     * @Date: 2021/11/13
     */
    @Test
    void generateCode() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("MrFugui") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()
                            .commentDate("yyyy-MM-dd")
                            .outputDir(javaDir); // 指定输出目录

                })
                .packageConfig(builder -> {
                    builder.parent("com.wangfugui") // 设置父包名
                            .moduleName("apprentice") // 设置父包模块名
                            .entity("dao.domain")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("dao.mapper")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperDir)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("good"); // 设置需要生成的表名
                })
                .execute();
    }


    @Autowired
    RedisUtils redisUtils;
    @Test
    void redisTest() {
        System.out.println(redisUtils.get("test"));
    }


    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    //测试索引的创建
    @Test
    void testCreateIndex() throws IOException {
        //1.创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest("lisen_index");
        //2客户端执行请求，请求后获得响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    //测试索引是否存在
    @Test
    void testExistIndex() throws IOException {
        //1.创建索引的请求
        GetIndexRequest request = new GetIndexRequest("lisens_index");
        //2客户端执行请求，请求后获得响应
        boolean exist =  client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("测试索引是否存在-----"+exist);
    }

    //删除索引
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("lisen_index");
        AcknowledgedResponse delete = client.indices().delete(request,RequestOptions.DEFAULT);
        System.out.println("删除索引--------"+delete.isAcknowledged());
    }

    //测试添加文档
    @Test
    void testAddDocument() throws IOException {
        Dynamic dynamic = new Dynamic("lisen",27);
        IndexRequest request = new IndexRequest("lisen_index");
        request.id("1");
        //设置超时时间
        request.timeout("1s");
        //将数据放到json字符串
        request.source(JSON.toJSONString(dynamic), XContentType.JSON);
        //发送请求
        IndexResponse response = client.index(request,RequestOptions.DEFAULT);
        System.out.println("添加文档-------"+response.toString());
        System.out.println("添加文档-------"+response.status());
//        结果
//        添加文档-------IndexResponse[index=lisen_index,type=_doc,id=1,version=1,result=created,seqNo=0,primaryTerm=1,shards={"total":2,"successful":1,"failed":0}]
//        添加文档-------CREATED
    }

    //测试文档是否存在
    @Test
    void testExistDocument() throws IOException {
        //测试文档的 没有index
        GetRequest request= new GetRequest("lisen_index","1");
        //没有indices()了
        boolean exist = client.exists(request, RequestOptions.DEFAULT);
        System.out.println("测试文档是否存在-----"+exist);
    }

    //测试获取文档
    @Test
    void testGetDocument() throws IOException {
        GetRequest request= new GetRequest("lisen_index","1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println("测试获取文档-----"+response.getSourceAsString());
        System.out.println("测试获取文档-----"+response);

//        结果
//        测试获取文档-----{"age":27,"name":"lisen"}
//        测试获取文档-----{"_index":"lisen_index","_type":"_doc","_id":"1","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"age":27,"name":"lisen"}}

    }

    //测试修改文档
    @Test
    void testUpdateDocument() throws IOException {
        Dynamic dynamic = new Dynamic("李逍遥", 55);
        //修改是id为1的
        UpdateRequest request= new UpdateRequest("lisen_index","1");
        request.timeout("1s");
        request.doc(JSON.toJSONString(dynamic),XContentType.JSON);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println("测试修改文档-----"+response);
        System.out.println("测试修改文档-----"+response.status());

//        结果
//        测试修改文档-----UpdateResponse[index=lisen_index,type=_doc,id=1,version=2,seqNo=1,primaryTerm=1,result=updated,shards=ShardInfo{total=2, successful=1, failures=[]}]
//        测试修改文档-----OK

//        被删除的
//        测试获取文档-----null
//        测试获取文档-----{"_index":"lisen_index","_type":"_doc","_id":"1","found":false}
    }


    //测试删除文档
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest request= new DeleteRequest("lisen_index","1");
        request.timeout("1s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println("测试删除文档------"+response.status());
    }

    //测试批量添加文档
    @Test
    void testBulkAddDocument() throws IOException {
        ArrayList<Dynamic> list=new ArrayList<Dynamic>();
        list.add(new Dynamic("cyx1",5));
        list.add(new Dynamic("cyx2",6));
        list.add(new Dynamic("cyx3",40));
        list.add(new Dynamic("cyx4",25));
        list.add(new Dynamic("cyx5",15));
        list.add(new Dynamic("cyx6",35));

        //批量操作的Request
        BulkRequest request = new BulkRequest();
        request.timeout("1s");

        //批量处理请求
        for (int i = 0; i < list.size(); i++) {
            request.add(
                    new IndexRequest("lisen_index")
                            .id(""+(i+1))
                            .source(JSON.toJSONString(list.get(i)),XContentType.JSON)
            );
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        //response.hasFailures()是否是失败的
        System.out.println("测试批量添加文档-----"+response.hasFailures());

//        结果:false为成功 true为失败
//        测试批量添加文档-----false
    }


    //测试查询文档
    @Test
    void testSearchDocument() throws IOException {
        SearchRequest request = new SearchRequest("lisen");
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

        System.out.println("测试查询文档-----"+ JSON.toJSONString(response.getHits()));
        System.out.println("=====================");
        for (SearchHit documentFields : response.getHits().getHits()) {
            System.out.println("测试查询文档--遍历参数--"+documentFields.getSourceAsMap());
        }

//        测试查询文档-----{"fragment":true,"hits":[{"fields":{},"fragment":false,"highlightFields":{},"id":"1","matchedQueries":[],"primaryTerm":0,"rawSortValues":[],"score":1.8413742,"seqNo":-2,"sortValues":[],"sourceAsMap":{"name":"cyx1","age":5},"sourceAsString":"{\"age\":5,\"name\":\"cyx1\"}","sourceRef":{"fragment":true},"type":"_doc","version":-1}],"maxScore":1.8413742,"totalHits":{"relation":"EQUAL_TO","value":1}}
//        =====================
//        测试查询文档--遍历参数--{name=cyx1, age=5}
    }


}
