package com.jp.SAX;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.jp.entity.Book;

public class SAXParserHandler extends DefaultHandler {
	/**
	 * ��־ ���˳��
	 */
	private int bookIndex = 0;
	private Book book;
	private String value; //�ڵ�ֵ
	private ArrayList<Book> bookList = new ArrayList<Book>();

	/**
	 * ������ʾ������ʼ
	 */
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		System.out.println("������ʼ!");
	}

	public ArrayList<Book> getBookList() {
		return bookList;
	}

	/**
	 * ������־��������
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		System.out.println("��������!");
	}

	/**
	 * ����������ʼ��ǩ
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		// ��ȡ book ��ǩ
		if (qName.equals("book")) {
			bookIndex++;
			
			// �����µ�  Book ����
			book = new Book();
			System.out.println("------���濪ʼ������" + bookIndex + "����------");
			// //��֪��������ȡ����ֵ
			// String value = attributes.getValue("id");
			// System.out.println("book��ǩ��id���Ե�ֵΪ" + value);
			// ��֪�����Ը�����������
			for (int i = 0; i < attributes.getLength(); i++) {
				String attrName = attributes.getQName(i);
				String attrValue = attributes.getValue(i);
				System.out.println("book��ǩ�ĵ�" + (i + 1) + "�����ԣ�" + attrName 
								+ "--" + "����ֵΪ: " + attrValue);
				if(attrName.equals("id")){
					book.setId(attrValue);
				}
			}
		} else if(!qName.equals("book") && !qName.equals("bookstore")){
			System.out.print("�ڵ���Ϊ:" + qName);
		}
	}

	/**
	 * ��������������ǩ
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName.equals("book")){
			bookList.add(book);
			book = null;
			System.out.println("------����������" + bookIndex + "����------"); 
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
	 * @param ch ���� xml�ĵ����ַ���
	 * @param start ����ƶ����Ľڵ�ֵ��ʼ��λ��
	 * @param length �ڵ�ֵ�ĳ���
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		//��ȡ�ڵ�ֵ
		value = new String(ch, start, length);
		//ȥ���ո�ڵ�ֵ
		if(!value.trim().equals("")){
			System.out.println("---�ڵ�ֵ��:" + value);
		}
	}
}
