package com.pt.ptuser.service.serviceImpl;

import com.pt.ptuser.entity.SysUserPost;
import com.pt.ptuser.mapper.SysUserPostMapper;
import com.pt.ptuser.service.SysUserPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysUserPostServiceImpl  implements SysUserPostService{
    private final SysUserPostMapper sysUserPostMapper;
    @Override
    public Boolean deleteUserPostByUserId(String userId) {
        return sysUserPostMapper.deleteUserPostByUserId(userId);
    }

    @Override
    public int countUserPostById(String postId) {
        return sysUserPostMapper.countUserPostById(postId);
    }

    @Override
    public Boolean deleteUserPost(String[] ids) {
        return sysUserPostMapper.deleteUserPost(ids);
    }

    @Override
    public Boolean batchUserPost(List<SysUserPost> userPostList) {
        return sysUserPostMapper.batchUserPost(userPostList);
    }
}
