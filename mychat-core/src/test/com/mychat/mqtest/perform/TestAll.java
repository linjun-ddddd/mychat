package com.mychat.mqtest.perform;

public class TestAll {
	public static void main(String[] args) {
		System.out.println("****构架库****");
		StringPerformTest.main(null);
		System.out.println("****原生****");
		StringPerformTest2.main(null);
	}
}
