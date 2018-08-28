package com.boxuegu.sms.dao.mapper;

import com.boxuegu.sms.domain.ChannelConfigDO;
import com.boxuegu.sms.domain.ChannelConfigDOCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ChannelConfigMapper {
    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ChannelConfigSqlProvider.class, method="countByExample")
    long countByExample(ChannelConfigDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @DeleteProvider(type=ChannelConfigSqlProvider.class, method="deleteByExample")
    int deleteByExample(ChannelConfigDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Delete({
        "delete from chnl_config",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Insert({
        "insert into chnl_config (id, `name`, ",
        "`desc`, `type`, `status`, ",
        "create_time, update_time, ",
        "delete_flag)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{desc,jdbcType=VARCHAR}, #{type,jdbcType=BIT}, #{status,jdbcType=BIT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{deleteFlag,jdbcType=BIT})"
    })
    int insert(ChannelConfigDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @InsertProvider(type=ChannelConfigSqlProvider.class, method="insertSelective")
    int insertSelective(ChannelConfigDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ChannelConfigSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.BIT),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    List<ChannelConfigDO> selectByExample(ChannelConfigDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Select({
        "select",
        "id, `name`, `desc`, `type`, `status`, create_time, update_time, delete_flag",
        "from chnl_config",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.BIT),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    ChannelConfigDO selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelConfigSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ChannelConfigDO record, @Param("example") ChannelConfigDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelConfigSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ChannelConfigDO record, @Param("example") ChannelConfigDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelConfigSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ChannelConfigDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Update({
        "update chnl_config",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR},",
          "`type` = #{type,jdbcType=BIT},",
          "`status` = #{status,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "delete_flag = #{deleteFlag,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ChannelConfigDO record);
}