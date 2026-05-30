package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.Marshaller;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {


        Student s1 = new Student(1, "Ion", 10, 9.5);
        JAXBContext context = JAXBContext.newInstance(Student.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(s1, new File("student.xml"));

        System.out.println("XML created successfully!");






        Unmarshaller unmarshaller =
                context.createUnmarshaller();

        Student student = (Student) unmarshaller.unmarshal(
                new File("student.xml"));

        System.out.println(student.getNume());
    }
}