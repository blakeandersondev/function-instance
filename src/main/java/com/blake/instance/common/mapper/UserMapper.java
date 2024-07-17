package com.blake.instance.common.mapper;

import com.blake.instance.common.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User getById(@Param("id") Long id);

    int updatePointsById(@Param("id") Long id, @Param("increment") Integer increment, @Param("version") Integer version);
}
