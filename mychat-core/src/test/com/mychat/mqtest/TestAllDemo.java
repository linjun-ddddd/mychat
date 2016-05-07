package com.mychat.mqtest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BinaryMqDemo.class, FileMqDemo.class, JsonMqDemo.class, StringMqDemo.class, XmlMqDemo.class })
public class TestAllDemo {

}
