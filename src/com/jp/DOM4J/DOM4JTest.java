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
		//1.��ȡSAXReader����
				SAXReader saxReader = new SAXReader();
				try {
				//2.ͨ��SAXReader�����read��������xml�ļ�����ȡDocument����
					Document document = saxReader.read(new File("books.xml"));
				//3.��ȡ���ڵ�
					Element rootElement = document.getRootElement();
				//4.ͨ�� elementIterator���� ������ȡ���ڵ�ĵ�����
					Iterator iter = rootElement.elementIterator();
				//5.���������
					while(iter.hasNext()){
						System.out.println("------���濪ʼ����ĳһ����------");
						Element book = (Element)iter.next();
				//6.��ȡ������Ϣ
						List<Attribute> attrs = book.attributes();
						for(int i = 0;i<attrs.size();i++){
							String attrName = attrs.get(i).getName();
							String attrValue = attrs.get(i).getValue();
							System.out.println("��������" + attrName + "---����ֵ��" + attrValue);
						}
				//7.��ȡ�ӽڵ�
						Iterator childIter = book.elementIterator();
						while(childIter.hasNext()){
							Element child = (Element)childIter.next();
							String name = child.getName();
							String value = child.getStringValue();
							System.out.println("�ڵ�����" + name + "---�ڵ�ֵ��" + value);
						}
						System.out.println("------��������ĳһ����------");
					}
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	public static void createXML(){
		//����Document����
		Document document = DocumentHelper.createDocument();
		//���� rss ���ڵ�,��������
		Element rss = document.addElement("rss");
		rss.addAttribute("version", "2.0");
		//�����ӽڵ�
		Element channel = rss.addElement("channel");
		Element title= channel.addElement("title");
		title.setText("��������");
		//ת���ַ�  <   >
//		Element name = channel.addElement("name");
//		name.setText("<![��]>");
		//���������ʽ
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		try {
		//����XMLWriter����,�����������ʽ
			XMLWriter write = new XMLWriter(new FileOutputStream(new File("rssDOM4J.xml")),format);
		//����ת���ַ�,Ĭ����true������
			write.setEscapeText(false);
		//����xml�ļ�
			write.write(document);
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
