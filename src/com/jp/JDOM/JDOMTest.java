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
		// 1.���� SAXBuilder ����
				SAXBuilder builder = new SAXBuilder();
				InputStream in = null;
				try {
					// 2.��������������
					in = new FileInputStream("books.xml");
					// 3.ͨ��builder��build�ķ��������������ص�SAXBuilder������
					Document document = builder.build(in);
					//Document document = builder.build("books.xml");
					// 4.ͨ��document�����ȡxml�ļ��ĸ��ڵ�
					Element rootElement = document.getRootElement();
					// 5.��ȡ���ڵ��µ��ֽڵ㼯��
					List<Element> bookList = rootElement.getChildren();
					for (Element book : bookList) {
						System.out.println("------��ʼ������" + (bookList.indexOf(book) + 1) + "����------");
						// System.out.println("����id��ֵΪ��" +
						// book.getAttributeValue("id"));
						//
						// ��ȡ�ڵ�����
						List<Attribute> attrs = book.getAttributes();
						for (Attribute attr : attrs) {
							String attrName = attr.getName();
							String attrValue = attr.getValue();
							System.out.println("��������" + attrName + "---����ֵ��" + attrValue);
						}
						//
						// �����ӽڵ�
						List<Element> bookChilds = book.getChildren();
						for (Element child : bookChilds) {
							String name = child.getName();
							String value = child.getValue();
							System.out.println("�ڵ�����" + name + "---�ڵ�ֵ��" + value);
						}
						System.out.println("------����������" + (bookList.indexOf(book) + 1) + "����------");
					}

				} catch (JDOMException | IOException e) {
					e.printStackTrace();
				}
	}
	
	public static void createXML(){
		//�������ڵ�,��������
		Element rss = new Element("rss");
		rss.setAttribute("version", "2.0");
		//����ӽڵ�
		Element channel = new Element("channel");
		rss.addContent(channel);
		Element title = new Element("title");
		title.setText("���");
		channel.addContent(title);
		//����Document����
		Document document = new Document(rss);
		//����xml��ʽ
		Format format = Format.getCompactFormat();
		format.setIndent("");
		format.setEncoding("utf-8");
		//�����������XMLOutputter
		XMLOutputter outputer = new XMLOutputter(format);
		try {
		//���xml�ļ�
			outputer.output(document, new FileOutputStream(new File("rssJDOM.xml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
