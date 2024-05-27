package kr.or.ddit.basic;

	/*
 		스레드 생성방법
 	
	 */


public class T02ThreadTest {
	public static void main(String[] args) {
		
		// 방법 1 : Thread 클래스를 상속한  class의 인스턴스를 생성한 후, 이 인스턴스의 start() 메서드를 실행한다. 
		// MultiThread 라고 할 수 있음. 왜?
		Thread th1 = new MyThread1();
		th1.start(); // 스레드 구동시키기
		
		// 방법 2 : Runnable 인터페이스를 구현한 class의 인스턴스를 생성한 후, 이 인스턴스를  Thread 객체 생성시
		//		     생성자의 파라미터 값으로 넘겨준다.
		
		// MultiThread => 두개의 작업이 동시에 이루어지고 있음, 사용자 입장에서는 하나의 작업이 이루어지는 것처럼 느껴짐
		// Runnable 인터페이스를 이용해 객체를 생성해서 사용하는 스레드 생성 방법
		Runnable r = new MyRunnable();
		Thread th2 = new Thread(r);
		th2.start();
		
		// 방법 3 : 익명 클래스를 이용하는 방법
		// Runnable 인터페이스를 구현한 익명 클래스를  Thread 인스턴스를 생성할 때 파라미터 값으로 넘겨준다.
		Thread th3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i=1; i<=200; i++) {
					System.out.print("@");
					
					try {
						// Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
						// 시간은 밀리세컨드(ms) 단위를 사용한다. 1초 = 1000ms
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		th3.start();
		
		System.out.println("메인 스레드 작업 종료");
		
	}
}


class MyThread1 extends Thread {
	
	@Override
	public void run() {

		for (int i=1; i<=200; i++) {
			System.out.print("*");
			
			try {
				// Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
				// 시간은 밀리세컨드(ms) 단위를 사용한다. 1초 = 1000ms
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}


class MyRunnable implements Runnable {

	@Override
	public void run() {
		for (int i=1; i<=200; i++) {
			System.out.print("$");
			
			try {
				// Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
				// 시간은 밀리세컨드(ms) 단위를 사용한다. 1초 = 1000ms
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}





