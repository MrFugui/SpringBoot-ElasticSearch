package com.wangfugui.apprentice.service.impl;

import com.wangfugui.apprentice.dao.domain.Comment;
import com.wangfugui.apprentice.dao.mapper.CommentMapper;
import com.wangfugui.apprentice.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-23
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
