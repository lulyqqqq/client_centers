package com.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.client.model.domain.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 29769
 * @description 针对表【users】的数据库操作Mapper
 * @createDate 2022-07-28 23:30:22
 * @Entity com.client.model.domain.Users
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {


}




