package com.jaf.examples.expert.lesson7.q3;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年11月30日
 * @since 1.0
 */
public class MycatRecord {
	public int id;
	public int col1;
	public short col2;

	public MycatRecord(int id, int col1, short col2) {
		this.id = id;
		this.col1 = col1;
		this.col2 = col2;
	}

	@Override
	public String toString() {
		return new StringBuilder()
					.append("{ id: ").append(id)
					.append(", col1: ").append(col1)
					.append(", col2: ").append(col2)
					.append(" }").toString();
	}
	
}
