package kr.or.ddit.basic;

/*
 	동기화 영역 안에서 효율적인 작업을 위한 예제
 */


public class T18WaitNotifyTest {
	public static void main(String[] args) {
		
		// 1. 공유객체 안에 데이터를 세팅하기만 하는 스레드
		// 2. 공유객체 안에 데이터가 있으면, 데이터를 가져오기만 하는 스레드 
		// 2가지를 만들 것임
		
		DataBox dataBox = new DataBox();
		
		ProducerThread pTh = new ProducerThread(dataBox);
		ConsumerThread cTh = new ConsumerThread(dataBox);
		
		pTh.start();
		cTh.start();
		
	}
}


class DataBox {
	private String data;
	
	// data가 null이 아닐 때, data 값을 반환하는 메서드
	public synchronized String getData() {
		System.out.println(Thread.currentThread().getName() + " : getData() 메서드 진입...");
		
		if (this.data == null) {
			try {
				System.out.println(Thread.currentThread().getName() + " : getData() 메서드 안에서  wait() 호출...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		String returnData = this.data;
		System.out.println("읽어온 데이터 : " + returnData);
		this.data = null; // 데이터 제거하기
		
		System.out.println(Thread.currentThread().getName() + " : getData() 메서드 안에서 notify() 호출...");
		notify();
		
		System.out.println(Thread.currentThread().getName() + " : getData() 메서드 끝...");
		
		return returnData;
	}
	
	
	// data가 null일 경우에만 데이터를 세팅하는 메서드
	public synchronized void setData(String data) {
		System.out.println(Thread.currentThread().getName() + " : setData() 메서드 진입...");
		
		if (this.data != null) {
			try {
				System.out.println(Thread.currentThread().getName() + " : setData() 메서드 안에서  wait() 호출...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.data = data; // 신규 데이터 설정
		System.out.println("세팅한 데이터 : " + this.data);
		
		System.out.println(Thread.currentThread().getName() + " : setData() 메서드 안에서  notify() 호출...");
		notify();
		
		System.out.println(Thread.currentThread().getName() + " : setData() 메서드 끝...");
	}
}


// 데이터를 세팅만 하는 스레드
class ProducerThread extends Thread {
	private DataBox dataBox;
	
	public ProducerThread(DataBox dataBox) {
		super("ProducerThread");
		this.dataBox = dataBox;
	}
	
	@Override
	public void run() {
		for (int i=1; i<=5; i++) {
			String data = "Data-" + i;
			System.out.println(this.getName() + "가 dataBox.setData(" + data + ") 호출하려고 함.");
			dataBox.setData(data);
		}
	}
}


// 데이터를 읽어오기만 하는 스레드
class ConsumerThread extends Thread {
	private DataBox dataBox;
	
	public ConsumerThread(DataBox dataBox) {
		super("ConsumerThread");
		this.dataBox = dataBox;
	}
	
	@Override
	public void run() {
		for (int i=1; i<=5; i++) {
			String data = dataBox.getData();
			System.out.println(this.getName() + "가 dataBox.getData() 호출 후 결과 받음 : " + data);
		}
	}
}