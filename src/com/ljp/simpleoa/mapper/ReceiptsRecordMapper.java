package com.ljp.simpleoa.mapper;

import com.ljp.simpleoa.model.ReceiptsRecord;
import com.ljp.simpleoa.model.ReceiptsRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReceiptsRecordMapper {
    long countByExample(ReceiptsRecordExample example);

    int deleteByExample(ReceiptsRecordExample example);

    int deleteByPrimaryKey(Integer receiptsRecordId);

    int insert(ReceiptsRecord record);

    int insertSelective(ReceiptsRecord record);

    List<ReceiptsRecord> selectByExample(ReceiptsRecordExample example);
    
    List<ReceiptsRecord> selectByExampleAndLeftJoinWorker(ReceiptsRecordExample example);

    ReceiptsRecord selectByPrimaryKey(Integer receiptsRecordId);

    int updateByExampleSelective(@Param("record") ReceiptsRecord record, @Param("example") ReceiptsRecordExample example);

    int updateByExample(@Param("record") ReceiptsRecord record, @Param("example") ReceiptsRecordExample example);

    int updateByPrimaryKeySelective(ReceiptsRecord record);

    int updateByPrimaryKey(ReceiptsRecord record);
}