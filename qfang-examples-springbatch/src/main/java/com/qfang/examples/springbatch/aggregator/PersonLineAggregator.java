package com.qfang.examples.springbatch.aggregator;

import com.qfang.examples.springbatch.dto.PersonDto;
import org.springframework.batch.item.file.transform.LineAggregator;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
public class PersonLineAggregator implements LineAggregator<PersonDto> {
    @Override
    public String aggregate(PersonDto personDto) {
        StringBuffer sb=new StringBuffer();
            sb.append(personDto.getId());
            sb.append(",");
            sb.append(personDto.getName());
            sb.append(",");
            sb.append(personDto.getAge());
            sb.append(",");
            sb.append(personDto.getOnJob());
        return sb.toString();
    }
}
