package com.yh.business.repository;

import com.yh.business.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/21
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    Page<Comment> findByParentid(String parentid, Pageable pageable);
}
