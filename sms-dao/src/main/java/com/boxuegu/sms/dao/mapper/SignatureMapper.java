package com.boxuegu.sms.dao.mapper;

import com.boxuegu.sms.domain.SignatureDO;
import com.boxuegu.sms.domain.SignatureDOCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SignatureMapper {
    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=SignatureSqlProvider.class, method="countByExample")
    long countByExample(SignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @DeleteProvider(type=SignatureSqlProvider.class, method="deleteByExample")
    int deleteByExample(SignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Delete({
        "delete from signature",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Insert({
        "insert into signature (id, chnl_config_id, ",
        "signature, `desc`, ",
        "`status`, create_time, ",
        "update_time, delete_flag)",
        "values (#{id,jdbcType=INTEGER}, #{chnlConfigId,jdbcType=INTEGER}, ",
        "#{signature,jdbcType=VARCHAR}, #{desc,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleteFlag,jdbcType=BIT})"
    })
    int insert(SignatureDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @InsertProvider(type=SignatureSqlProvider.class, method="insertSelective")
    int insertSelective(SignatureDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=SignatureSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    List<SignatureDO> selectByExample(SignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Select({
        "select",
        "id, chnl_config_id, signature, `desc`, `status`, create_time, update_time, delete_flag",
        "from signature",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    SignatureDO selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=SignatureSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") SignatureDO record, @Param("example") SignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=SignatureSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") SignatureDO record, @Param("example") SignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=SignatureSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SignatureDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Update({
        "update signature",
        "set chnl_config_id = #{chnlConfigId,jdbcType=INTEGER},",
          "signature = #{signature,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR},",
          "`status` = #{status,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "delete_flag = #{deleteFlag,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SignatureDO record);
}