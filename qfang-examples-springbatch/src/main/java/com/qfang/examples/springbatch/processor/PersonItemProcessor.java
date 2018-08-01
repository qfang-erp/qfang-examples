package com.qfang.examples.springbatch.processor;

import com.qfang.examples.springbatch.dto.PersonDto;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
public class PersonItemProcessor implements ItemProcessor<PersonDto,PersonDto> {
    @Override
    public PersonDto process(PersonDto personDto) throws Exception {
        System.out.println("update person onJob personid:"+personDto.getId());
        personDto.setOnJob("NO");
        personDto.setId(new Random().nextInt()+"");
        return personDto;
    }
}
