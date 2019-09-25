package com.yudu.face.repository;

import com.yudu.face.model.InspectInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface InspectInfoRepository {
    @Insert("INSERT INTO InspectInfo(id,userName,userInfo,Times,Year,Month,Day,Hour,Minute,Second,sortTime,mip,countTime) VALUES(#{id}, #{userName}, #{userInfo},#{times},#{year},#{month},#{day},#{hour}" +
            ",#{minute},#{second},#{sortTime},#{mip},#{countTime})")
    void insert(InspectInfo inspectInfo);

    @Select("SELECT top 1 * FROM InspectInfo order by sortTime desc")
    @Results({
            @Result(property = "enrollNumber",  column = "EnrollNumber"),
            @Result(property = "verifyMode", column = "VerifyMode"),
            @Result(property = "inOutMode", column = "InOutMode"),
            @Result(property = "times", column = "Times"),
            @Result(property = "year", column = "Year"),
            @Result(property = "month", column = "Month"),
            @Result(property = "day", column = "Day"),
            @Result(property = "hour", column = "Hour"),
            @Result(property = "minute", column = "Minute"),
            @Result(property = "second", column = "Second"),
            @Result(property = "sortTime", column = "sortTime"),
            @Result(property = "mip", column = "mip"),
            @Result(property = "countTime", column = "countTime")
    })
    InspectInfo getLastRecord();

    @Select("SELECT CASE WHEN EXISTS(SELECT * FROM InspectInfo WHERE times = #{times}) THEN 1 ELSE 0 END EXISTENCE")

    Integer checkRepeat(String times);
}
