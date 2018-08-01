package com.qfang.examples.springbatch.mapper;

import com.qfang.examples.springbatch.dto.PersonDto;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.util.StringUtils;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
public class PersonLineMapper implements LineMapper<PersonDto> {
    @Override
    public PersonDto mapLine(String s, int i) throws Exception {
        if(StringUtils.isEmpty(s)){
            return null;
        }
        String [] tmps=s.split(",");

        PersonDto dto=new PersonDto();
        dto.setId(tmps[0]);
        dto.setName(tmps[1]);
        dto.setAge(Integer.parseInt(tmps[2]));

        return dto;
    }
}
