package com.pt.ptuser.mapper;

import com.pt.ptuser.entity.SysUserPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserPostMapper  {

    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    Boolean deleteUserPostByUserId(@Param("userId") String userId,@Param("companyId") String companyId);

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    int countUserPostById(@Param("postId") String postId,@Param("companyId") String companyId);

    /**
     * 批量删除用户和岗位关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    Boolean deleteUserPost(@Param("ids") String[] ids,@Param("companyId") String companyId);

    /**
     * 批量新增用户岗位信息
     *
     * @param userPostList 用户角色列表
     * @return 结果
     */
    Boolean batchUserPost(@Param("userPostList") List<SysUserPost> userPostList,@Param("companyId") String companyId);

}