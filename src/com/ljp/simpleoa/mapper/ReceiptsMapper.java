package com.ljp.simpleoa.mapper;

import com.ljp.simpleoa.model.Receipts;
import com.ljp.simpleoa.model.ReceiptsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReceiptsMapper {
    long countByExample(ReceiptsExample example);

    int deleteByExample(ReceiptsExample example);

    int deleteByPrimaryKey(Integer receiptsId);

    int insert(Receipts record);

    int insertSelective(Receipts record);

    List<Receipts> selectByExample(ReceiptsExample example);
    
    List<Receipts> selectByExampleAndLeftJoinWorker(ReceiptsExample example);

    Receipts selectByPrimaryKey(Integer receiptsId);

    int updateByExampleSelective(@Param("record") Receipts record, @Param("example") ReceiptsExample example);

    int updateByExample(@Param("record") Receipts record, @Param("example") ReceiptsExample example);

    int updateByPrimaryKeySelective(Receipts record);

    int updateByPrimaryKey(Receipts record);
}