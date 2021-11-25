package com.wangfugui.apprentice.service.impl;

import com.wangfugui.apprentice.dao.domain.Good;
import com.wangfugui.apprentice.dao.mapper.GoodMapper;
import com.wangfugui.apprentice.service.IGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-23
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {

}
