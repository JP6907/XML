package com.jp.unitTest;

import java.util.ArrayList;

import com.jp.DOM.DOMTest;
import com.jp.DOM4J.DOM4JTest;
import com.jp.JDOM.JDOMTest;
import com.jp.SAX.SAXText;
import com.jp.entity.Book;

public class Test {
	public void parseTest(){
		System.out.println("解析性能测试");
		long start = System.currentTimeMillis();
		DOMTest.parseByDOM();
		System.out.println("DOM:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		SAXText.parseBySAX();
		System.out.println("SAX:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		DOM4JTest.parseByDOM4J();
		System.out.println("DOM4J:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		JDOMTest.parseByJDOM();
		System.out.println("JDOM:" + (System.currentTimeMillis() - start));
	}
	@org.junit.Test
	public void createTest(){
		System.out.println("写入性能测试");
		long start = System.currentTimeMillis();
		DOMTest.createXML();
		System.out.println("DOM:" + (System.currentTimeMillis() - start));
		Book book = new Book();
		book.setId("1");
		book.setName("小王子");
		ArrayList<Book> list = new ArrayList<Book>();
		list.add(book);
		start = System.currentTimeMillis();
		SAXText.createXML(list);
		System.out.println("SAX:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		DOM4JTest.createXML();
		System.out.println("DOM4J:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		JDOMTest.createXML();
		System.out.println("JDOM:" + (System.currentTimeMillis() - start));
	}
}
