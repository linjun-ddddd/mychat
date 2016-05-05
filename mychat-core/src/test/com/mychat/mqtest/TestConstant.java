package com.mychat.mqtest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({JsonMqDemo.class,StringMqDemo.class})
public class TestConstant {

	public static final String CHANNEL = "channelTest";
	public static final String TOPIC = "topicTest";

}
