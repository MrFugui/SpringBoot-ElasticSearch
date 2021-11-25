package com.wangfugui.apprentice.controller;

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
