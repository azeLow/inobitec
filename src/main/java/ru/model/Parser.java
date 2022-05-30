package ru.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Parser {

    public static void parse(String path, String fieldName) {

        if(path!=null || path.length()==0) {
            System.out.println("Параметр запроса пуст");
            return;
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(path));

            doc.getDocumentElement().normalize();
            var patientsList = doc.getElementsByTagName("patient");

            List<Patient> patientList = new ArrayList<>();
            for (int i = 0; i < patientsList.getLength(); i++) {

                Node node = patientsList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String firstName = element.getElementsByTagName("first_name").item(0).getTextContent();
                    String middleName = element.getElementsByTagName("middle_name").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("last_name").item(0).getTextContent();
                    LocalDate birthday = LocalDate.parse(element.getElementsByTagName("birthday").item(0).getTextContent());
                    LocalDate currentDate = LocalDate.now();
                    int age = Period.between(birthday, currentDate).getYears();
                    String gender = element.getElementsByTagName("gender").item(0).getTextContent();
                    String phone = element.getElementsByTagName("phone").item(0).getTextContent();

                    Patient patient = new Patient(firstName, middleName, lastName, age, gender, phone);
                    patientList.add(patient);

                }

            }

            if (fieldName.trim().equals("name")) {
                Comparator<Patient> patientComparator = Comparator.comparing(Patient::getFirstName);
                Collections.sort(patientList, patientComparator);
            }
            else if (fieldName.trim().equals("age")) {
                Comparator<Patient> patientComparator = Comparator.comparing(Patient::getAge);
                Collections.sort(patientList, patientComparator);
            }

            for (int i = 0; i < patientList.size(); i++) {
                System.out.println(patientList.get(i));
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

}