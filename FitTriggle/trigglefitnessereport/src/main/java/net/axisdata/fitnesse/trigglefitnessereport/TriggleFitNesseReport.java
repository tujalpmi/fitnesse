/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.axisdata.fitnesse.trigglefitnessereport;

import net.axisdata.fitnesse.trigglefitnessereport.utils.ReportUtils;
import net.axisdata.fitnesse.trigglefitnessereport.EnvironmentValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.ArrayList;
import java.io.InputStream;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.commons.lang.StringUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * @author jose.alvarez
 */

public class TriggleFitNesseReport {

    static ReportTable reportTable;
    static ArrayList<ReportTable> reportTableList = new ArrayList<ReportTable>();
    private static Properties properties;
    private static String mailHost;
    private static String mailUser;
    private static String mailPassWord;
    private static String pathFitNesseResult;
    private static String title;

    TriggleFitNesseReport() {
    }

    public static void main(String[] args) throws Exception {

        String htmlFile;
        InputStream input = TriggleFitNesseReport.class.getClassLoader().getResourceAsStream("store.properties");
        properties = new Properties();
        properties.load(input);
        mailHost = properties.getProperty("mail.host");
        mailUser = properties.getProperty("mail.user");
        mailPassWord = properties.getProperty("mail.password");
        pathFitNesseResult = properties.getProperty("fitnesse.path.result");

        File f = new File("/axisdata/fitnesse/FitNesseRoot/templatesTriggle/set-end-point/message.txt");

        EnvironmentValues env;

        if ("DVLP".equals(args[0]) || "STABLE".equals(args[0])) {

            setEndPoint(args[0]);
            
            
            if ("DVLP".equals(args[0])) {
                env = new EnvironmentValues(args[0], "58003");
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
                out.writeObject(env);
            }

            if ("STABLE".equals(args[0])) {
                env = new EnvironmentValues(args[0], "58002");
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
                out.writeObject(env);
            }

        } else {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
            env = (EnvironmentValues) in.readObject();
            System.out.println("After deserialize environment is: " + env.getEnvironment());
            System.out.println("After deserialize fitnesse port is: " + env.getFitnessePort());
            
            manageFitNesseTestResult();
            htmlFile = createHtmlFitNesseReport(env.getEnvironment(),env.getFitnessePort());
            sendFitNesseReport(htmlFile,args,args.length );

        }

    }

     /**
     *
     * @author jose.alvarez
     */
    
    public static void setEndPoint(String environment) throws FileNotFoundException, IOException {

        String userPort = "30055";
        String providerPort = "30045";
        String systemPort = "30035";
        String stockPort = "30025";
        String graphqlPort = "30065";
        
        StringBuilder sb = new StringBuilder();
        

        try {

            if ("STABLE".equals(environment)) {

                userPort = "30055";
                providerPort = "30045";
                systemPort = "30035";
                stockPort = "30025";
                graphqlPort = "30065";

            } else {

                userPort = "30020";
                providerPort = "30070";
                systemPort = "30050";
                stockPort = "30040";
                graphqlPort = "30060";

            }

            BufferedReader br = new BufferedReader(new FileReader("/axisdata/fitnesse/FitNesseRoot/templatesTriggle/set-end-point/content.txt"));
            String line = new String(br.readLine());

            while (line != null) {

                System.out.println("old line: " + line);

                if (line.contains("USER_PORT")) {
                    line = "!define USER_PORT {" + userPort + "}";
                }
                if (line.contains("PROVIDER_PORT")) {
                    line = "!define PROVIDER_PORT {" + providerPort + "}";
                }
                if (line.contains("SYSTEM_PORT")) {
                    line = "!define SYSTEM_PORT {" + systemPort + "}";
                }
                if (line.contains("STOCK_PORT")) {
                    line = "!define STOCK_PORT {" + stockPort + "}";
                }
                if (line.contains("GRAPHQL_PORT")) {
                    line = "!define GRAPHQL_PORT {" + graphqlPort + "}";
                }

                sb.append(line);
                sb.append("\n");
                System.out.println("new line: " + line);
                line = br.readLine();
            }

            line = sb.toString();

            File file = new File("/axisdata/fitnesse/FitNesseRoot/templatesTriggle/set-end-point/content.txt");
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                writer.write(line);
                writer.flush();
            }

        } catch (IOException ex) {

            System.out.println(ex);

        }

    }
    
    
    /**
     *
     * @author jose.alvarez
     * @return
     * @throws java.text.ParseException
     */
    public static String createHtmlFitNesseReport(String environment, String port) throws java.text.ParseException {

        float successRate = 0, totalSuccessRate = 0;
        float totalTestTimeBySuite = 0, testTimeExec = 0;
        int totalTestByTC = 0, totalTestOKBySuite = 0, totalTestNOKBySuite = 0;
        int totalTestExceptionsBySuite = 0, totalTestIgnoresBySuite = 0, totalTestBySuite = 0;
        String initialtd, finaltd, partialTableHead, testingDomainFormatIn, totalTableHead, totalTableRows, partialTableRows = "";
        String testingDomain = reportTableList.get(1).getPageHistoryLink();
        String[] testingDomainParts = testingDomain.split("\\.");
        String title = "Triggle FitNesse Test Report";
        setTitle(title);
        String testDomain = null;
        String testDomainCompare = "111";
        String testCompare = "111";
        String color = "blue";
        String texto = "xxxx";
        String test = null;
         
        testingDomainFormatIn = "<center><img align=\"middle\" width=140 height=50 src=\"cid:image\"><BR><BR><font size=\"5\" color=\"green\" face=\"Verdana\">" + "Triggle Test Report "  + "</font><font size=\"3\" color=\"green\" face=\"Verdana\">"
                + " - Environment: "+environment+" - "+ ReportUtils.getFotmatDate(reportTableList.get(0).getDate(), "yyyy-MM-dd'T'hh:mm:ss", "yyyy-MM-dd HH:mm:ss")
                + "</font></center><BR>";

        partialTableHead = "<html><head><title>" + title + "</title></head><body>" + testingDomainFormatIn + "\n"
                + "<table border='1' cellpadding='0' cellspacing='0' width='90%'>\n"
                + "  <tr bgcolor='#5C5858'>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Testing Domain</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Test</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Test Case</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">FitLink</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Date</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">TimeInSec</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Right</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Wrong</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Ignores</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">Exceptions</font></b></th>\n"
                + "    <th><b><font size=\"2\" color=\"white\" face=\"Verdana\">SuccessRate %</font></b></th></tr>\n";

        initialtd = "<td bgcolor='#efefef' align=\"center\">";
        finaltd = "</td>";

        //for (int x = 0; x < reportTableList.size(); x++) {
        for (int x = reportTableList.size() - 1; x >= 0; x--) {
            totalTestByTC = Integer.parseInt(reportTableList.get(x).getExceptions()) + Integer.parseInt(reportTableList.get(x).getWrong()) + Integer.parseInt(reportTableList.get(x).getRight());
            successRate = ((float) (Integer.parseInt(reportTableList.get(x).getRight())) / totalTestByTC) * 100;
            testTimeExec = (float) (Integer.parseInt(reportTableList.get(x).getRunTimeInMillis())) / 1000;

            totalTestOKBySuite = totalTestOKBySuite + Integer.parseInt(reportTableList.get(x).getRight());
            totalTestNOKBySuite = totalTestNOKBySuite + Integer.parseInt(reportTableList.get(x).getWrong());
            totalTestExceptionsBySuite = totalTestExceptionsBySuite + Integer.parseInt(reportTableList.get(x).getExceptions());
            totalTestIgnoresBySuite = totalTestIgnoresBySuite + Integer.parseInt(reportTableList.get(x).getIgnores());
            totalTestTimeBySuite = totalTestTimeBySuite + testTimeExec;

            testDomain= reportTableList.get(x).getPageHistoryLink();
            String[] splitString = testDomain.split("\\.");
            
            if (testDomainCompare.equals(splitString[2]))  {
                
                color= "#efefef";
                texto ="";
            }else {
                
                color = "##D1D0CE";
                texto =  splitString[2];
            }
            
            test = StringUtils.substringBefore(splitString[3],"?");
            
    
            
            if (testCompare.equals(test)) {

                //color = "#efefef";
                test = "";
            } else {

                //color = "#B6B6B4";
                test = StringUtils.substringBefore(splitString[3],"?");
            }
            
                        
            partialTableRows = partialTableRows + "<tr>" + "<td bgcolor='"  +color+  "' align=\"center\">"+texto + "</td>"
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + test + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + reportTableList.get(x).getRelativePageName() + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + "<a href='http://srvaxitrgjen249:"+port+"/" + reportTableList.get(x).getPageHistoryLink() + "'>+</a>" + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + ReportUtils.getFotmatDate(reportTableList.get(x).getDate(), "yyyy-MM-dd'T'hh:mm:ss", "yyyy-MM-dd HH:mm:ss") + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + testTimeExec + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + "<b><font size=\"3\" color=\"green\" face=\"Verdana\">" + reportTableList.get(x).getRight() + "</font></b>" + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + "<b><font size=\"3\" color=\"red\" face=\"Verdana\">" + reportTableList.get(x).getWrong() + "</font></b>" + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + "<b><font size=\"3\" color=\"blue\" face=\"Verdana\">" + reportTableList.get(x).getIgnores() + "</font></b>" + finaltd
                    + "<td bgcolor='"  +color+  "' align=\"center\">" + "<b><font size=\"3\" color=\"orange\" face=\"Verdana\">" + reportTableList.get(x).getExceptions() + "</font></b>" + finaltd;

            if (successRate < 100) {

                partialTableRows = partialTableRows + "<td bgcolor='red' align=\"center\">" + "<b><font size=\"3\" color=\"white\" face=\"Verdana\">" + String.format("%.1f", successRate) + finaltd + "</tr>";

            } else {

                partialTableRows = partialTableRows + "<td bgcolor='green' align=\"center\">" + "<b><font size=\"3\" color=\"white\" face=\"Verdana\">" + String.format("%.1f", successRate) + finaltd + "</tr>";
            }
            
               testDomainCompare = splitString[2];
               testCompare = StringUtils.substringBefore(splitString[3],"?");;

        }

        //totalTestBySuite = totalTestOKBySuite + totalTestNOKBySuite + totalTestIgnoresBySuite + totalTestExceptionsBySuite;
        totalTestBySuite = totalTestOKBySuite + totalTestNOKBySuite  + totalTestExceptionsBySuite;
        totalSuccessRate = ((float) (totalTestOKBySuite) / totalTestBySuite) * 100;

        totalTableHead = "<table align=\"center\" border='1' cellpadding='0' cellspacing='0' width='50%'>\n"
                + "  <tr>\n"
                + "    <th>TotalTimeInSec</th>\n"
                + "    <th>TotalRight</th>\n"
                + "    <th>TotalWrong</th>\n"
                + "    <th>TotalIgnores</th>\n"
                + "    <th>TotalExceptions</th>\n"
                + "    <th>TotalSuccessRate %</th></tr>\n";

        totalTableRows = "<tr>" + initialtd + totalTestTimeBySuite + finaltd
                + initialtd + "<b><font size=\"3\" color=\"green\" face=\"Verdana\">" + totalTestOKBySuite + "</font></b>" + finaltd
                + initialtd + "<b><font size=\"3\" color=\"red\" face=\"Verdana\">" + totalTestNOKBySuite + "</font></b>" + finaltd
                + initialtd + "<b><font size=\"3\" color=\"blue\" face=\"Verdana\">" + totalTestIgnoresBySuite + "</font></b>" + finaltd
                + initialtd + "<b><font size=\"3\" color=\"orange\" face=\"Verdana\">" + totalTestExceptionsBySuite + "</font></b>" + finaltd;

        if (totalSuccessRate < 100) {

            totalTableRows = totalTableRows + "<td bgcolor='red' align=\"center\">" + "<b><font size=\"3\" color=\"white\" face=\"Verdana\">" + String.format("%.1f", totalSuccessRate) + finaltd + "</tr>";

        } else {

            totalTableRows = totalTableRows + "<td bgcolor='green' align=\"center\">" + "<b><font size=\"3\" color=\"white\" face=\"Verdana\">" + String.format("%.1f", totalSuccessRate) + finaltd + "</tr>";
        }

        String finalHtml = partialTableHead + partialTableRows + "</table><BR><BR>" + totalTableHead + totalTableRows + "</table></body></html>";

        return finalHtml;

    }

    /**
     *
     * @author jose.alvarez
     * @param htmlFile
     * @throws javax.mail.NoSuchProviderException
     */
    public static void sendFitNesseReport(String htmlFile, String[] arg, int leng) throws NoSuchProviderException, MessagingException {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        //props.setProperty("mail.host", "smtp.gmail.com");
        props.setProperty("mail.host", mailHost);
        props.setProperty("mail.user", mailUser);
        props.setProperty("mail.password", mailPassWord);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.auth", "true");

        Session mailSession = Session.getInstance(props,
                new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUser, mailPassWord);
            }
        });

        mailSession.setDebug(true);

        Transport transport = mailSession.getTransport();
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(geTitle());
        message.setFrom(new InternetAddress("triggle.pmi.test@axisdata.net"));
        
       // message.setContent(htmlFile, "text/html");
        
        
         // first part (the html)
         // This mail has 2 part, the BODY and the embedded image
        MimeMultipart multipart = new MimeMultipart("related");

         // first part (the html)
         BodyPart messageBodyPart = new MimeBodyPart();

         messageBodyPart.setContent(htmlFile, "text/html");
         // add it
         multipart.addBodyPart(messageBodyPart);

         // second part (the image)
         messageBodyPart = new MimeBodyPart();
         DataSource fds = new FileDataSource("/axisdata/fitnesse/TriggleLogo.jpg");
         	//Get file from resources folder
	//ClassLoader classLoader = TriggleFitNesseReport.class.getClassLoader();
	//File file = new File(classLoader.getResource("/axisdata/fitnesse/TriggleLogo.jpg").getFile());
        
         //DataSource fds = new FileDataSource(classLoader.getResource("logoTriggle.jpg").getFile());

         messageBodyPart.setDataHandler(new DataHandler(fds));
         messageBodyPart.setHeader("Content-ID", "<image>");

         // add image to the multipart
         multipart.addBodyPart(messageBodyPart);

         // put everything together
         message.setContent(multipart);
  
        for (int i = 0; i < leng; i++) {

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(arg[i]));

        }
  
        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();

    }

    /**
     *
     * @author jose.alvarez
     * @throws javax.xml.xpath.XPathExpressionException
     * @throws javax.mail.NoSuchProviderException
     */
    public static void manageFitNesseTestResult() throws XPathExpressionException, NoSuchProviderException, MessagingException {

        Document doc = null;
        Document inputAsDocument;
        NodeList subList;
        Node subNode;
        Node paxNode;
        Element paxElement;
        NodeList paxList;

        String date = null;
        String runTimeInMillis = null;
        String relativePageName = null;
        String pageHistoryLink = null;

        String right = null;
        String wrong = null;
        String ignores = null;
        String exceptions = null;

        try {

            BufferedReader br = new BufferedReader(new FileReader(pathFitNesseResult));

            StringBuilder sb = new StringBuilder();
            String line;
            String lineout;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                sb.append("\n");
            }

            lineout = sb.toString();
            System.out.println("line-out: \n" + lineout);
            inputAsDocument = ReportUtils.getDocumentFromXMLString(lineout);

            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();

            XPathExpression xpathCompilada = xpath.compile("/testResults/result/counts/right");

            XPathExpression xpathCompilada2 = xpath.compile("/testResults/result");
            NodeList nodes2 = (NodeList) xpathCompilada2.evaluate(inputAsDocument, XPathConstants.NODESET);

            for (int i = 0; i < nodes2.getLength(); i++) {

                NodeList nodes3 = nodes2.item(i).getChildNodes();

                int v = 0;

                for (int j = 0; j < nodes3.getLength() / 2; j++) {

                    v = j * 2 + 1;

                    if ("date".equals(nodes3.item(v).getNodeName())) {

                        date = nodes3.item(v).getTextContent();
                    }
                    if ("runTimeInMillis".equals(nodes3.item(v).getNodeName())) {

                        runTimeInMillis = nodes3.item(v).getTextContent();

                    }
                    if ("relativePageName".equals(nodes3.item(v).getNodeName())) {

                        relativePageName = nodes3.item(v).getTextContent();
                    }
                    if ("pageHistoryLink".equals(nodes3.item(v).getNodeName())) {

                        pageHistoryLink = nodes3.item(v).getTextContent();
                    }

                    if ("counts".equals(nodes3.item(j * 2 + 1).getNodeName())) {

                        int w = 0;
                        NodeList nodes4 = nodes3.item(j * 2 + 1).getChildNodes();

                        for (int k = 0; k < nodes4.getLength() / 2; k++) {
                            w = k * 2 + 1;

                            if ("right".equals(nodes4.item(w).getNodeName())) {
                                right = nodes4.item(w).getTextContent();
                            }
                            if ("wrong".equals(nodes4.item(w).getNodeName())) {
                                wrong = nodes4.item(w).getTextContent();
                            }
                            if ("ignores".equals(nodes4.item(w).getNodeName())) {
                                ignores = nodes4.item(w).getTextContent();
                            }
                            if ("exceptions".equals(nodes4.item(w).getNodeName())) {
                                exceptions = nodes4.item(w).getTextContent();
                            }

                        }

                    }

                }

                System.out.println("date:  " + date);
                System.out.println("runTimeInMillis:  " + runTimeInMillis);
                System.out.println("relativePageName:  " + relativePageName);
                System.out.println("pageHistoryLink:  " + pageHistoryLink);
                System.out.println("right:  " + right);
                System.out.println("wrong:  " + wrong);
                System.out.println("ignores:  " + ignores);
                System.out.println("exceptions:  " + exceptions);

                reportTable = new ReportTable(right, wrong, ignores, exceptions, date, runTimeInMillis, relativePageName, pageHistoryLink);
                reportTableList.add(reportTable);

            }

        } catch (IOException ex) {

            System.out.println(ex);

        }

    }

    public static String geTitle() {

        return title;

    }

    public static void setTitle(String title) {

        TriggleFitNesseReport.title = title;
    }

}
