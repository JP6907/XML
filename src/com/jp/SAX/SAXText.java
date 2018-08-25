package com.jp.SAX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.jp.entity.Book;

public class SAXText {
	public static void main(String[] args) {
		ArrayList<Book> bookList = parseBySAX();
		createXML(bookList);
	}

	public static ArrayList<Book> parseBySAX() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			SAXParserHandler handler = new SAXParserHandler();
			parser.parse("books.xml", handler);
			ArrayList<Book> bookList = handler.getBookList();
			// System.out.println("�����" + bookList.size() + "����");
			// for(int i = 0;i<bookList.size();i++){
			// System.out.println("��" + i + "����:");
			// System.out.println(bookList.get(i));
			// }
			return bookList;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void createXML(ArrayList<Book> bookList) {
		// 1.��ȡSAXTransformerFactory����
		SAXTransformerFactory stff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		try {
			// 2.��ȡTransformerHandler����
			TransformerHandler handler = stff.newTransformerHandler();
			// 3.ͨ��handler�����ȡtransformer����
			Transformer tf = handler.getTransformer();
			// 4.ͨ��transformer�����xml�ļ���������
			tf.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			// 5.����Result����
			Result result = new StreamResult(new FileOutputStream(new File("bookSAX.xml")));
			// 6.����result����handler����
			handler.setResult(result);
			// 7.����handler����xml�ļ��ı�д
			handler.startDocument();
			AttributesImpl atts = new AttributesImpl();
			handler.startElement("", "", "bookstore", atts);
			for (Book book : bookList) {
				atts.clear();
				// ��������
				atts.addAttribute("", "", "id", "", book.getId());
				handler.startElement("", "", "book", atts);
				// ���ýڵ�
				if (book.getName() != null && !book.getName().trim().equals("")) {
					atts.clear();
					handler.startElement("", "", "name", atts);
					handler.characters(book.getName().toCharArray(), 0, book.getName().length());
					handler.endElement("", "", "name");
				}
				handler.endElement("", "", "book");
			}
			handler.endElement("", "", "bookstore");
			handler.endDocument();
		} catch (TransformerConfigurationException | FileNotFoundException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
