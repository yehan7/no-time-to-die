package com.yh.service;

import com.yh.business.utils.RestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by idea China
 * Author: YH007
 * Time: 18:24 2020/2/10
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlTest {

    @Test
    public void test1() {
        String xpath = "C:\\Users\\YH\\OneDrive\\桌面\\test.xml";
        String elementName = "name";
        try {
            xpathForCountByEN(xpath, elementName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

    public int xpathForCountByEN(String xpath, String elementName) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        File file = new File(xpath);

        InputStream is = new FileInputStream(file);
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(is);
        int count = 0;
        while (streamReader.hasNext()) {
            streamReader.next();
            if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                System.out.println(streamReader.getLocalName());
                if (streamReader.getLocalName().equals(elementName)) {
                    count++;
                }
            }
        }
        System.out.printf("---> %s count: %d \n", elementName, count);
        return count;
    }


}
