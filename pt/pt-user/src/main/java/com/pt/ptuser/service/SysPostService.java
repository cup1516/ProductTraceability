package com.pt.ptuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptuser.entity.SysPost;

import java.util.List;

public interface SysPostService  {
    /**
     * 分页查询职位
     *
     * @param page    分页对象
     * @param sysPost
     * @return
     */
    IPage getPostPage(Page page, SysPost sysPost,String companyId);
    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位列表
     */
    List<SysPost> selectPostList(SysPost post,String companyId);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPost> selectPostAll(String companyId);

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    SysPost selectPostById(String postId,String companyId);

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    List<String> selectPostListByUserId(String userId,String companyId);

    /**
     * 校验岗位名称
     *
     * @param post 岗位信息
     * @return 结果
     */
    Boolean checkPostNameUnique(SysPost post,String companyId);

    /**
     * 校验岗位编码
     *
     * @param post 岗位信息
     * @return 结果
     */
    Boolean checkPostCodeUnique(SysPost post,String companyId);

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    int countUserPostById(String postId,String companyId);

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    Boolean deletePostById(String postId,String companyId);

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     * @throws Exception 异常
     */
    Boolean deletePostByIds(String[] postIds,String companyId);

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    Boolean insertPost(SysPost post,String companyId);

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    Boolean updatePost(SysPost post,String companyId);
}
