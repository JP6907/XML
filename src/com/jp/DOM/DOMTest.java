package com.jp.DOM;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMTest {
	public static void main(String[] args){
		parseByDOM();
		//createXML();
	}
	public static void parseByDOM(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("books.xml");
			//��ȡ���� book �ڵ�
			NodeList bookList = document.getElementsByTagName("book");
			System.out.println("һ����" + bookList.getLength() + "����");
			for(int i = 0; i < bookList.getLength(); i++){
				System.out.println("------���濪ʼ������" + (i+1) + "����------");
				//��ȡ��i����
				/**
				 * ��ȡ���Է���1
				 * ǰ�᣺��֪���ж������ԣ���֪��������
				 * ������������
				 */
//				Node book = bookList.item(i);
//				/*******************/
//				//��ȡbook�ڵ���������Լ���
//				NamedNodeMap attrs = book.getAttributes();
//				System.out.println("��" + (i+1) + "������" + attrs.getLength() + "������");;
//				for(int j = 0; j<attrs.getLength(); j++){
//					Node attr = attrs.item(j);
//					//��ȡ������
//					System.out.print("������:" +	 ttr.getNodeName());
//					//��ȡ����ֵ
//					System.out.println("--����ֵ:" + attr.getNodeValue());
//				}
				
				/**
				 * ��ȡ���Է���2
				 * ǰ�᣺�Ѿ�֪��book�ڵ�����ֻ����һ��id����
				 * ��ȡָ������
				 */
				Element book = (Element)bookList.item(i);
				String attrValue = book.getAttribute("id");
				System.out.println("id���Ե�ֵΪ" + attrValue); 
				
				/****************************/
				/**
				 * ��ȡbook���ӽڵ�
				 */
				NodeList childNodes = book.getChildNodes();
				System.out.println("��" + (i+1) + "���鹲��" 
								+ childNodes.getLength() + "���ӽڵ�");
				for(int k = 0;k<childNodes.getLength(); k++){
					//���� text�ڵ� �� element �ڵ�
					if(childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
						//��ȡ Element �����ӽڵ�
						System.out.print("��" + (k+1) + "���ֽڵ���:" 
									+ childNodes.item(k).getNodeName());
						System.out.print("---�ڵ�ֵ:" 
									+ childNodes.item(k).getFirstChild().getNodeValue());
						// getTextContent ���ȡ �ڵ��м��ֵ �����ֽڵ��ֵ
						System.out.println("---�ڵ�ֵ:" 
								+ childNodes.item(k).getTextContent());
					}
				}
				System.out.println("------����������" + (i+1) + "����------"); 
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createXML(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.newDocument();
			document.setXmlStandalone(true);
			//���ɸ��ڵ�
			Element bookstore = document.createElement("bookstore");
			//����book�ڵ�
			Element book = document.createElement("book");
			Element name = document.createElement("name");
			name.setTextContent("С����");
			//����book�ڵ�����
			book.setAttribute("id", "1");
			book.appendChild(name);
			//���book�ڵ㵽���ڵ�
			bookstore.appendChild(book);
			//��Ӹ��ڵ㵽 document 
			document.appendChild(bookstore);
			//��ȡTransformerFactory ����
			TransformerFactory tff = TransformerFactory.newInstance();
			//��ȡTransform����
			Transformer tf = tff.newTransformer();
			//�����Զ�����
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(new DOMSource(document), new StreamResult(new File("bookDOM.xml")));
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
