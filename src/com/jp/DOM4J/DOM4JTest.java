package com.jp.DOM4J;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DOM4JTest {

	public static void main(String[] args) {
		//parseByDOM4J();
		createXML();
	}
	public static void parseByDOM4J(){
		//1.获取SAXReader对象
				SAXReader saxReader = new SAXReader();
				try {
				//2.通过SAXReader对象的read方法加载xml文件，获取Document对象
					Document document = saxReader.read(new File("books.xml"));
				//3.获取根节点
					Element rootElement = document.getRootElement();
				//4.通过 elementIterator（） 方法获取根节点的迭代器
					Iterator iter = rootElement.elementIterator();
				//5.历遍迭代器
					while(iter.hasNext()){
						System.out.println("------下面开始历遍某一本书------");
						Element book = (Element)iter.next();
				//6.获取属性信息
						List<Attribute> attrs = book.attributes();
						for(int i = 0;i<attrs.size();i++){
							String attrName = attrs.get(i).getName();
							String attrValue = attrs.get(i).getValue();
							System.out.println("属性名：" + attrName + "---属性值：" + attrValue);
						}
				//7.获取子节点
						Iterator childIter = book.elementIterator();
						while(childIter.hasNext()){
							Element child = (Element)childIter.next();
							String name = child.getName();
							String value = child.getStringValue();
							System.out.println("节点名：" + name + "---节点值：" + value);
						}
						System.out.println("------结束历遍某一本书------");
					}
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	public static void createXML(){
		//创建Document对象
		Document document = DocumentHelper.createDocument();
		//创建 rss 根节点,设置属性
		Element rss = document.addElement("rss");
		rss.addAttribute("version", "2.0");
		//创建子节点
		Element channel = rss.addElement("channel");
		Element title= channel.addElement("title");
		title.setText("国内新闻");
		//转义字符  <   >
//		Element name = channel.addElement("name");
//		name.setText("<![鹏]>");
		//创建输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		try {
		//创建XMLWriter对象,并设置输出格式
			XMLWriter write = new XMLWriter(new FileOutputStream(new File("rssDOM4J.xml")),format);
		//处理转义字符,默认是true，处理
			write.setEscapeText(false);
		//生成xml文件
			write.write(document);
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
