package com.boxuegu.sms.dao.mapper;

import com.boxuegu.sms.domain.SignatureDO;
import com.boxuegu.sms.domain.SignatureDOCriteria.Criteria;
import com.boxuegu.sms.domain.SignatureDOCriteria.Criterion;
import com.boxuegu.sms.domain.SignatureDOCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class SignatureSqlProvider {

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String countByExample(SignatureDOCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("signature");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String deleteByExample(SignatureDOCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("signature");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String insertSelective(SignatureDO record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("signature");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getChnlConfigId() != null) {
            sql.VALUES("chnl_config_id", "#{chnlConfigId,jdbcType=INTEGER}");
        }
        
        if (record.getSignature() != null) {
            sql.VALUES("signature", "#{signature,jdbcType=VARCHAR}");
        }
        
        if (record.getDesc() != null) {
            sql.VALUES("`desc`", "#{desc,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("`status`", "#{status,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeleteFlag() != null) {
            sql.VALUES("delete_flag", "#{deleteFlag,jdbcType=BIT}");
        }
        
        return sql.toString();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String selectByExample(SignatureDOCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("chnl_config_id");
        sql.SELECT("signature");
        sql.SELECT("`desc`");
        sql.SELECT("`status`");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.SELECT("delete_flag");
        sql.FROM("signature");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        SignatureDO record = (SignatureDO) parameter.get("record");
        SignatureDOCriteria example = (SignatureDOCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("signature");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getChnlConfigId() != null) {
            sql.SET("chnl_config_id = #{record.chnlConfigId,jdbcType=INTEGER}");
        }
        
        if (record.getSignature() != null) {
            sql.SET("signature = #{record.signature,jdbcType=VARCHAR}");
        }
        
        if (record.getDesc() != null) {
            sql.SET("`desc` = #{record.desc,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("`status` = #{record.status,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeleteFlag() != null) {
            sql.SET("delete_flag = #{record.deleteFlag,jdbcType=BIT}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("signature");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("chnl_config_id = #{record.chnlConfigId,jdbcType=INTEGER}");
        sql.SET("signature = #{record.signature,jdbcType=VARCHAR}");
        sql.SET("`desc` = #{record.desc,jdbcType=VARCHAR}");
        sql.SET("`status` = #{record.status,jdbcType=BIT}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        sql.SET("delete_flag = #{record.deleteFlag,jdbcType=BIT}");
        
        SignatureDOCriteria example = (SignatureDOCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String updateByPrimaryKeySelective(SignatureDO record) {
        SQL sql = new SQL();
        sql.UPDATE("signature");
        
        if (record.getChnlConfigId() != null) {
            sql.SET("chnl_config_id = #{chnlConfigId,jdbcType=INTEGER}");
        }
        
        if (record.getSignature() != null) {
            sql.SET("signature = #{signature,jdbcType=VARCHAR}");
        }
        
        if (record.getDesc() != null) {
            sql.SET("`desc` = #{desc,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("`status` = #{status,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeleteFlag() != null) {
            sql.SET("delete_flag = #{deleteFlag,jdbcType=BIT}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    protected void applyWhere(SQL sql, SignatureDOCriteria example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}