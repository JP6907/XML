package com.jp.JDOM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JDOMTest {
	public static void main(String[] args) {
		//parseByJDOM();
		createXML();
	}
	public static void parseByJDOM(){
		// 1.创建 SAXBuilder 对象
				SAXBuilder builder = new SAXBuilder();
				InputStream in = null;
				try {
					// 2.创建输入流对象
					in = new FileInputStream("books.xml");
					// 3.通过builder的build的方法将输入流加载到SAXBuilder对象中
					Document document = builder.build(in);
					//Document document = builder.build("books.xml");
					// 4.通过document对象获取xml文件的根节点
					Element rootElement = document.getRootElement();
					// 5.获取根节点下的字节点集合
					List<Element> bookList = rootElement.getChildren();
					for (Element book : bookList) {
						System.out.println("------开始解析第" + (bookList.indexOf(book) + 1) + "本书------");
						// System.out.println("属性id的值为：" +
						// book.getAttributeValue("id"));
						//
						// 获取节点属性
						List<Attribute> attrs = book.getAttributes();
						for (Attribute attr : attrs) {
							String attrName = attr.getName();
							String attrValue = attr.getValue();
							System.out.println("属性名：" + attrName + "---属性值：" + attrValue);
						}
						//
						// 历遍子节点
						List<Element> bookChilds = book.getChildren();
						for (Element child : bookChilds) {
							String name = child.getName();
							String value = child.getValue();
							System.out.println("节点名：" + name + "---节点值：" + value);
						}
						System.out.println("------结束解析第" + (bookList.indexOf(book) + 1) + "本书------");
					}

				} catch (JDOMException | IOException e) {
					e.printStackTrace();
				}
	}
	
	public static void createXML(){
		//创建根节点,设置属性
		Element rss = new Element("rss");
		rss.setAttribute("version", "2.0");
		//添加子节点
		Element channel = new Element("channel");
		rss.addContent(channel);
		Element title = new Element("title");
		title.setText("你好");
		channel.addContent(title);
		//生成Document对象
		Document document = new Document(rss);
		//设置xml格式
		Format format = Format.getCompactFormat();
		format.setIndent("");
		format.setEncoding("utf-8");
		//创建输出对象XMLOutputter
		XMLOutputter outputer = new XMLOutputter(format);
		try {
		//输出xml文件
			outputer.output(document, new FileOutputStream(new File("rssJDOM.xml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
