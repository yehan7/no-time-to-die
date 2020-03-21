package com.yh.service;

import com.yh.business.entity.Comment;
import com.yh.business.repository.CommentRepository;
import com.yh.business.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MogonDBTest {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Test
    public void getAll(){
        List<Comment> all = commentRepository.findAll();
        System.out.println(all);
      //  commentService.deleteCommentById(all.get(0).getId());



    }
}
