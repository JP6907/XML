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
			// System.out.println("共获得" + bookList.size() + "本书");
			// for(int i = 0;i<bookList.size();i++){
			// System.out.println("第" + i + "本书:");
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
		// 1.获取SAXTransformerFactory对象
		SAXTransformerFactory stff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		try {
			// 2.获取TransformerHandler对象
			TransformerHandler handler = stff.newTransformerHandler();
			// 3.通过handler对象获取transformer对象
			Transformer tf = handler.getTransformer();
			// 4.通过transformer对象对xml文件进行配置
			tf.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			// 5.创建Result对象
			Result result = new StreamResult(new FileOutputStream(new File("bookSAX.xml")));
			// 6.关联result对象到handler对象
			handler.setResult(result);
			// 7.利用handler进行xml文件的编写
			handler.startDocument();
			AttributesImpl atts = new AttributesImpl();
			handler.startElement("", "", "bookstore", atts);
			for (Book book : bookList) {
				atts.clear();
				// 设置属性
				atts.addAttribute("", "", "id", "", book.getId());
				handler.startElement("", "", "book", atts);
				// 设置节点
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
