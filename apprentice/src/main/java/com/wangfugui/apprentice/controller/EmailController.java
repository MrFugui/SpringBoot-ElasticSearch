package com.wangfugui.apprentice.controller;

import com.wangfugui.apprentice.common.util.EmailUtil;
import com.wangfugui.apprentice.common.util.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/11/20
 * @since JDK 1.8.0
 */
@Api(tags = "邮件服务")
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailUtil emailUtil;

    @GetMapping("/sendSimpleMail")
    @ApiOperation("发送简单邮件")
    public ResponseUtils sendSimpleMail(String email,String subject,String content) {
        emailUtil.sendSimpleMail(email,subject,content);
        return ResponseUtils.success();
    }
    @GetMapping("/sendAttachmentsMail")
    @ApiOperation("发送附件邮件")
    public ResponseUtils sendAttachmentsMail(String email,String subject,String content,String filePath) {
        emailUtil.sendAttachmentsMail(email, subject, content, filePath);
        return ResponseUtils.success();
    }
}
