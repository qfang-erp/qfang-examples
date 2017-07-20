package com.jaf.examples.expert.lesson1.q3;

import java.io.IOException;

/**
 * question:
 * 
 * 一个文本文件方式存储的Person表，Id，name，age,三个字段，Id为整数，name为长度8字节的英文和字母随机数，age为 1-18岁之间随机数，总共100万行记录。
 * 采用两种编程方式实现如下计算输出：
 *       年龄在  5-11岁、16-18岁 这两个区间范围内的 首字母在a-e之间的所有人的统计信息：
 *                     6-11岁的 总共XXX个 ，首字母为a的xxxx个，为b的yyy个，为c的zzz个，为e的ttt个
 *                     16-18岁的总共XXX个，首字母为a的xxxx个，为b的yyy个，为c的zzz个，为e的ttt个
 * 其中一种采用谷歌的ArrayTable数据结构，一种则自由发挥（也可以采用javalution里的数据结构）， 对比两种实现方式的内存占用，运行时间两项指标，并给出自己的分析结果
 * 主程序分别为 MyPersonCaclByArrayTable 以及     MyPersonCaclByMy 
 * 生成随机Person文件的代码为 GenRandomPersonDBFile ，生成的文件名 persondb.txt （c:\c:\leaderus\data\persondb.txt)
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class Main {
	
	public static void main(String[] args) throws IOException {
		GenRandomPersonDBFile.generate();
	}

}
