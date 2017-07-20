package com.qfang.examples.redis.jedis.spring.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public class KryoSerializer implements RedisSerializer<Object> {
	
	@Override
	public byte[] serialize(Object obj) throws SerializationException {
		final Kryo kryo = KryoHolder.borrow();
		Output output = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			output = new Output(baos);
			kryo.writeClassAndObject(output, obj);
			return output.toBytes();
		} finally {
			if (output != null) {
				output.close();
			}
			KryoHolder.release(kryo);
		}
	}
	
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0)
			return null;
		
		final Kryo kryo = KryoHolder.borrow();
		Input input = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			input = new Input(bais);
			return kryo.readClassAndObject(input);
		} finally {
			if (input != null) {
				input.close();
			}
			KryoHolder.release(kryo);
		}
	}
	
}
