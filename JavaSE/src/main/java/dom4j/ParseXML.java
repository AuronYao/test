package dom4j;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.SynchronousQueue;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXML {
	public static void main(String[] args) throws DocumentException, FileNotFoundException {
		FileInputStream in = new FileInputStream("result.xml"); 
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		Element node1 = root.element("Body");
		Element node2 =	node1.element("payResponse");
		Element node3 = node2.element("payReturn");
		String info = node3.getText();
		System.out.println(info);
	}
}
