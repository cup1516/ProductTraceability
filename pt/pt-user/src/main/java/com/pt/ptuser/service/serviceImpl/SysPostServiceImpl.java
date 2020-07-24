package com.pt.ptuser.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommonsecurity.exception.CustomException;
import com.pt.ptuser.entity.SysPost;
import com.pt.ptuser.mapper.SysPostMapper;
import com.pt.ptuser.service.SysPostService;
import com.pt.ptuser.service.SysUserPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wl
 */
@Service
@AllArgsConstructor
public class SysPostServiceImpl  implements SysPostService {

    private SysUserPostService sysUserPostService;
    private SysPostMapper sysPostMapper;
    @Override
    public IPage getPostPage(Page page, SysPost sysPost,String companyId) {
        return sysPostMapper.getPostPage(page,sysPost,companyId);
    }

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post,String companyId)
    {
        return sysPostMapper.selectPostList(post,companyId);
    }



    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll(String companyId)
    {
        return sysPostMapper.selectPostAll(companyId);
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(String postId,String companyId)
    {
        return sysPostMapper.selectPostById(postId,companyId);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<String> selectPostListByUserId(String userId,String companyId)
    {
        return sysPostMapper.selectPostListByUserId(userId,companyId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean checkPostNameUnique(SysPost post,String companyId)
    {
        if(StrUtil.isEmpty(post.getPostId())){
            return Boolean.TRUE;
        }
        SysPost sysPost = sysPostMapper.checkPostNameUnique(post.getPostName());

        if (sysPost != null && !sysPost.getPostId().equals(sysPost.getPostId()))
        {
            throw new CustomException("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        return Boolean.TRUE;

    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean checkPostCodeUnique(SysPost post,String companyId)
    {
        if(StrUtil.isEmpty(post.getPostId())){
            return Boolean.TRUE;
        }
        SysPost sysPost = sysPostMapper.checkPostCodeUnique(post.getPostCode());

        if (sysPost != null && !sysPost.getPostId().equals(sysPost.getPostId()))
        {
            throw new CustomException("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        return Boolean.TRUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(String postId,String companyId)
    {
        return sysUserPostService.countUserPostById(postId,companyId);
    }

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public Boolean deletePostById(String postId,String companyId)
    {
        return sysPostMapper.deletePostById(postId,companyId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public Boolean deletePostByIds(String[] postIds,String companyId)
    {
        for (String postId : postIds)
        {
            SysPost post = selectPostById(postId,companyId);
            if (countUserPostById(postId,companyId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return sysPostMapper.deletePostByIds(postIds,companyId);
    }

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean insertPost(SysPost post,String companyId)
    {
        post.setPostId(IdUtils.simpleUUID());
        return sysPostMapper.insertPost(post,companyId);
    }

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public Boolean updatePost(SysPost post,String companyId)
    {
        return sysPostMapper.updatePost(post,companyId);
    }
}
