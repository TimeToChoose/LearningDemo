package com.shineofeidos.demo.mapper

import com.shineofeidos.demo.model.SysUser
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserMapper {
    fun findByUsername(@Param("username") username: String): SysUser?

    fun findById(@Param("id") id: Long): SysUser?

    fun updateProfile(user: SysUser): Int
}
