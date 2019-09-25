package com.yudu.face.repository;

import com.yudu.face.model.InspectInfo;
import com.yudu.face.model.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoRepository {

    @Select("SELECT * FROM USERINFO where name = #{name} and isValid =1")
    @Results({
            @Result(property = "userInfoId",  column = "userInfoId"),
            @Result(property = "name", column = "name"),
            @Result(property = "userName", column = "userName"),
            @Result(property = "passWord", column = "passWord"),
            @Result(property = "isValid", column = "isValid"),
            @Result(property = "userType", column = "userType"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "departmentId", column = "departmentID"),
            @Result(property = "contactListId", column = "contactListID"),
            @Result(property = "isCheckAttendance", column = "isCheckAttendance"),
            @Result(property = "duty", column = "duty")
    })
    UserInfo getUserInfo(String name);
}
