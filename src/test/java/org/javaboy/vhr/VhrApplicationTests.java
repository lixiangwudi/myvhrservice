package org.javaboy.vhr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VhrApplicationTests {

	@Test
	void contextLoads() {
		MyThread thread = new MyThread();
		thread.start();
	}

	class MyThread extends Thread{
		@Override
		public void run() {
			System.out.println("123");
		}
	}

}
