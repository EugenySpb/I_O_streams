import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class XMLSettingsReader {
    final boolean isLoad;
    final String loadFile;
    final String loadFormat;

    final boolean isSave;
    final String saveFile;
    final String saveFormat;

    final boolean isLog;
    final String logFile;

    public XMLSettingsReader(File xmlFile) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        XPath xPath = XPathFactory.newInstance().newXPath();

        isLoad = Boolean.parseBoolean(xPath.compile("/config/load/enabled")
                .evaluate(doc));
        loadFile = xPath.compile("/config/load/fileName")
                .evaluate(doc);
        loadFormat = xPath.compile("/config/load/format")
                .evaluate(doc);

        isSave = Boolean.parseBoolean(xPath.compile("/config/save/enabled")
                .evaluate(doc));
        saveFile = xPath.compile("/config/save/fileName")
                .evaluate(doc);
        saveFormat = xPath.compile("/config/save/format")
                .evaluate(doc);

        isLog = Boolean.parseBoolean(xPath.compile("/config/log/enabled")
                .evaluate(doc));
        logFile = xPath.compile("/config/log/fileName")
                .evaluate(doc);
    }
}
