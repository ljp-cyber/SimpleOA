package com.ljp.mapperInterface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ljp.bean.ReceiptsDetails;
import com.ljp.bean.ReceiptsDetailsExample;

public interface ReceiptsDetailsMapper {
    long countByExample(ReceiptsDetailsExample example);

    int deleteByExample(ReceiptsDetailsExample example);

    int deleteByPrimaryKey(Integer receiptsDetailsId);

    int insert(ReceiptsDetails record);

    int insertSelective(ReceiptsDetails record);

    List<ReceiptsDetails> selectByExample(ReceiptsDetailsExample example);

    ReceiptsDetails selectByPrimaryKey(Integer receiptsDetailsId);

    int updateByExampleSelective(@Param("record") ReceiptsDetails record, @Param("example") ReceiptsDetailsExample example);

    int updateByExample(@Param("record") ReceiptsDetails record, @Param("example") ReceiptsDetailsExample example);

    int updateByPrimaryKeySelective(ReceiptsDetails record);

    int updateByPrimaryKey(ReceiptsDetails record);
}