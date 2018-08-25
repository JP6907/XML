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
			//获取所有 book 节点
			NodeList bookList = document.getElementsByTagName("book");
			System.out.println("一共有" + bookList.getLength() + "本书");
			for(int i = 0; i < bookList.getLength(); i++){
				System.out.println("------下面开始遍历第" + (i+1) + "本书------");
				//获取第i本书
				/**
				 * 获取属性方法1
				 * 前提：不知道有多少属性，不知道属性名
				 * 历遍所有属性
				 */
//				Node book = bookList.item(i);
//				/*******************/
//				//获取book节点的所有属性集合
//				NamedNodeMap attrs = book.getAttributes();
//				System.out.println("第" + (i+1) + "本书有" + attrs.getLength() + "个属性");;
//				for(int j = 0; j<attrs.getLength(); j++){
//					Node attr = attrs.item(j);
//					//获取属性名
//					System.out.print("属性名:" +	 ttr.getNodeName());
//					//获取属性值
//					System.out.println("--属性值:" + attr.getNodeValue());
//				}
				
				/**
				 * 获取属性方法2
				 * 前提：已经知道book节点有且只能有一个id属性
				 * 获取指定属性
				 */
				Element book = (Element)bookList.item(i);
				String attrValue = book.getAttribute("id");
				System.out.println("id属性的值为" + attrValue); 
				
				/****************************/
				/**
				 * 获取book的子节点
				 */
				NodeList childNodes = book.getChildNodes();
				System.out.println("第" + (i+1) + "本书共有" 
								+ childNodes.getLength() + "个子节点");
				for(int k = 0;k<childNodes.getLength(); k++){
					//区分 text节点 和 element 节点
					if(childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
						//获取 Element 类型子节点
						System.out.print("第" + (k+1) + "个字节点名:" 
									+ childNodes.item(k).getNodeName());
						System.out.print("---节点值:" 
									+ childNodes.item(k).getFirstChild().getNodeValue());
						// getTextContent 会获取 节点中间的值 和其字节点的值
						System.out.println("---节点值:" 
								+ childNodes.item(k).getTextContent());
					}
				}
				System.out.println("------结束遍历第" + (i+1) + "本书------"); 
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
			//生成根节点
			Element bookstore = document.createElement("bookstore");
			//生成book节点
			Element book = document.createElement("book");
			Element name = document.createElement("name");
			name.setTextContent("小王子");
			//设置book节点属性
			book.setAttribute("id", "1");
			book.appendChild(name);
			//添加book节点到根节点
			bookstore.appendChild(book);
			//添加根节点到 document 
			document.appendChild(bookstore);
			//获取TransformerFactory 对象
			TransformerFactory tff = TransformerFactory.newInstance();
			//获取Transform对象
			Transformer tf = tff.newTransformer();
			//设置自动换行
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(new DOMSource(document), new StreamResult(new File("bookDOM.xml")));
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
