package com.qfang.examples.elasticsearch.node;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

public class TestJSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Object> map = Maps.newHashMap();
		map.put("title", "title-01");
		map.put("body", "body test");
		map.put("publish", "publish_date");
		String s = new Gson().toJson(map);
		System.out.print(s);
	    //JSON.toJSONString(map);
	}

}
