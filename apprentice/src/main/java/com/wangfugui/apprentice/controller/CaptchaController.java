package com.wangfugui.apprentice.controller;

import com.wangfugui.apprentice.common.util.RedisUtils;
import com.wangfugui.apprentice.common.util.ResponseUtils;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码")
@Controller
@RequestMapping("captcha")
public class CaptchaController {

    @Autowired
    private RedisUtils redisUtil;
    
    @ResponseBody
    @ApiOperation("生成验证码")
    @GetMapping("/makeCaptcha")
    public ResponseUtils makeCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisUtil.setStrEx(key, verCode, 30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("image", specCaptcha.toBase64());
        return ResponseUtils.success(map);
    }
    
    @ResponseBody
    @ApiOperation("校验验证码")
    @PostMapping("/getCaptcha")
    public ResponseUtils getCaptcha(String verCode,String verKey){
        // 获取redis中的验证码
        String redisCode = redisUtil.getStr(verKey);
        // 判断验证码
        if (verCode==null || !redisCode.equals(verCode.trim().toLowerCase())) {
            return ResponseUtils.error("验证码不正确");
        }
        return ResponseUtils.success("验证成功");
    }  
}