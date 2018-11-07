package net.axisdata.fitnesse.trigglefitnessereport.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author jose.alvarez
 */

public class ReportUtils {

    public ReportUtils() {
    }

    public static String getFotmatDate(String inputDate, String inputFormat, String outFormat) throws java.text.ParseException {

        Date date;
        String dateFormat = null;
        SimpleDateFormat sdf;

        sdf = new SimpleDateFormat(inputFormat);
        date = sdf.parse(inputDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date nuevaFecha = calendar.getTime();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(outFormat);
        dateFormat = DATE_FORMAT.format(nuevaFecha);

        return dateFormat;
    }

    public static Document getDocumentFromXMLString(String xml) {
        Document result = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            result = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            //Logger.getLogger(NormalizeEmailXML.class.getName()).error(ex.getLocalizedMessage() + "\nUnable to get document from: " + xml, ex);
        }
        return result;
    }

}
