package com.pt.ptuser.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptuser.entity.SysPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPostMapper  {
    /**
     * 分页获取职位
     * @param page 分页参数
     * @param sysPost
     * @return
     */
    IPage<List<SysPost>> getPostPage(Page page,@Param("sysPost") SysPost sysPost,@Param("companyId") String companyId);
    /**
     * 查询岗位数据集合
     *
     * @param post 岗位信息
     * @return 岗位数据集合
     */
    List<SysPost> selectPostList(@Param("post") SysPost post,@Param("companyId") String companyId);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPost> selectPostAll(@Param("companyId") String companyId);

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    SysPost selectPostById(@Param("postId") String postId,@Param("companyId") String companyId);

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    List<String> selectPostListByUserId(@Param("userId") String userId,@Param("companyId") String companyId);

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    List<SysPost> selectPostsByUserName(@Param("userName") String userName,@Param("companyId") String companyId);

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    Boolean deletePostById(@Param("postId") String postId,@Param("companyId") String companyId);

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    Boolean deletePostByIds(@Param("postIds") String[] postIds,@Param("companyId") String companyId);

    /**
     * 修改岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    Boolean updatePost(@Param("post") SysPost post,@Param("companyId") String companyId);

    /**
     * 新增岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    Boolean insertPost(@Param("post") SysPost post,@Param("companyId") String companyId);

    /**
     * 校验岗位名称
     *
     * @param postName 岗位名称
     * @return 结果
     */
    SysPost checkPostNameUnique(String postName);

    /**
     * 校验岗位编码
     *
     * @param postCode 岗位编码
     * @return 结果
     */
    SysPost checkPostCodeUnique(String postCode);
}