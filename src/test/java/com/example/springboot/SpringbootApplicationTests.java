package com.example.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Test
	public void contextLoads() throws IllegalAccessException, InstantiationException {

		Class<?> c = String.class;
		Object str = c.newInstance();



	}
	@Test
	public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


		//获取String所对应的Class对象
		Class<?> c = String.class;
		//获取String类带一个String参数的构造器
		Constructor constructor = c.getConstructor(String.class);
		//根据构造器创建实例
		Object obj = constructor.newInstance("23333");
		System.out.println(obj);

		String str=new String("1233");
		Class<?> klass = str.getClass();



	}



}
