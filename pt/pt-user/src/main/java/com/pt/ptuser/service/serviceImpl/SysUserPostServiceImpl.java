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
    public Boolean deleteUserPostByUserId(String userId,String companyId) {
        return sysUserPostMapper.deleteUserPostByUserId(userId,companyId);
    }

    @Override
    public int countUserPostById(String postId,String companyId) {
        return sysUserPostMapper.countUserPostById(postId,companyId);
    }

    @Override
    public Boolean deleteUserPost(String[] ids,String companyId) {
        return sysUserPostMapper.deleteUserPost(ids,companyId);
    }

    @Override
    public Boolean batchUserPost(List<SysUserPost> userPostList,String companyId) {
        return sysUserPostMapper.batchUserPost(userPostList,companyId);
    }
}
