package com.qfang.examples.springbatch.processor;

import com.qfang.examples.springbatch.dto.ReportDto;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
public class ReportProcessor implements ItemProcessor<ReportDto,ReportDto> {
    @Override
    public ReportDto process(ReportDto reportDto) throws Exception {
        reportDto.setTenementDetail(reportDto.getTenementDetail()+"_BAK");
        System.out.println(reportDto.getTenementDetail());
        return reportDto;
    }
}
