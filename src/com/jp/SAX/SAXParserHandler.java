package com.jp.SAX;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.jp.entity.Book;

public class SAXParserHandler extends DefaultHandler {
	/**
	 * 标志 书的顺序
	 */
	private int bookIndex = 0;
	private Book book;
	private String value; //节点值
	private ArrayList<Book> bookList = new ArrayList<Book>();

	/**
	 * 用来表示解析开始
	 */
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		System.out.println("解析开始!");
	}

	public ArrayList<Book> getBookList() {
		return bookList;
	}

	/**
	 * 用来标志解析结束
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		System.out.println("解析结束!");
	}

	/**
	 * 用来解析开始标签
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		// 获取 book 标签
		if (qName.equals("book")) {
			bookIndex++;
			
			// 创建新的  Book 对象
			book = new Book();
			System.out.println("------下面开始遍历第" + bookIndex + "本书------");
			// //已知属性名获取属性值
			// String value = attributes.getValue("id");
			// System.out.println("book标签的id属性的值为" + value);
			// 不知道属性个数和属性名
			for (int i = 0; i < attributes.getLength(); i++) {
				String attrName = attributes.getQName(i);
				String attrValue = attributes.getValue(i);
				System.out.println("book标签的第" + (i + 1) + "个属性：" + attrName 
								+ "--" + "属性值为: " + attrValue);
				if(attrName.equals("id")){
					book.setId(attrValue);
				}
			}
		} else if(!qName.equals("book") && !qName.equals("bookstore")){
			System.out.print("节点名为:" + qName);
		}
	}

	/**
	 * 用来遍历结束标签
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName.equals("book")){
			bookList.add(book);
			book = null;
			System.out.println("------结束遍历第" + bookIndex + "本书------"); 
		} else if(qName.equals("name")){
			book.setName(value);
		} else if(qName.equals("author")){
			book.setAuthor(value);
		} else if(qName.equals("year")){
			book.setYear(value);
		} else if(qName.equals("price")){
			book.setPrice(value);
		} else if(qName.equals("language")){
			book.setLanguage(value);
		}
	}
	/**
	 * @param ch 整个 xml文档的字符串
	 * @param start 光标移动到的节点值开始的位置
	 * @param length 节点值的长度
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		//获取节点值
		value = new String(ch, start, length);
		//去掉空格节点值
		if(!value.trim().equals("")){
			System.out.println("---节点值是:" + value);
		}
	}
}
