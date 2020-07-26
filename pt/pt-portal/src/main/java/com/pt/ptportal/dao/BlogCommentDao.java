package com.pt.ptportal.dao;

import com.pt.ptportal.entity.BlogComments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCommentDao extends JpaRepository<BlogComments, String> {
    Iterable<BlogComments> findAllByBlogIdAndCompanyIdAndState(int blogId,String companyId,Boolean state);
    List<BlogComments> findAllByStateIsTrueAndCompanyId(String companyId);
}
