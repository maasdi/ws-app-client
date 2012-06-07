package com.maasdianto.ws.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.bind.JAXBElement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.maasdianto.attachment.ObjectFactory;
import com.maasdianto.attachment.Pdf;

public class AttachmentClient {
	
	ObjectFactory factory = new ObjectFactory();
	
	ApplicationContext context;
	WebServiceTemplate template;
	
	public AttachmentClient() {
		context = new ClassPathXmlApplicationContext("classpath:client-ws-ctx.xml");
		template = (WebServiceTemplate) context.getBean("webserviceTemplateMtom");
	}

	public static void main(String[] args) {
		AttachmentClient client = new AttachmentClient();
		// load attachment file in server by name
		String fileName = "HudsonArch-Security.pdf";
		client.loadAttachment(fileName);
		
		// send attachment file to server
		File file = new File("D:/prototype-160-api.pdf");
		client.sendAttachment(file);
	}
	
	public void loadAttachment(String fileName){
		JAXBElement<String> request = factory.createLoadPdfRequest(fileName);

		@SuppressWarnings("unchecked")
		JAXBElement<Pdf> response = (JAXBElement<Pdf>) template.marshalSendAndReceive(request);

		try {
			Pdf pdf = response.getValue();
			DataHandler handler = pdf.getFile();
			System.out.println(pdf.getName());
			
			FileOutputStream outputStream = new FileOutputStream(new File("D:/" + pdf.getName()));
			handler.writeTo(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendAttachment(File file){
		DataSource dataSource = new FileDataSource(file);
		
		Pdf pdf = new Pdf();
		pdf.setName(file.getName());
		pdf.setFile(new DataHandler(dataSource));
		
		JAXBElement<Pdf> request = factory.createSendPdfRequest(pdf);
		@SuppressWarnings("unchecked")
		JAXBElement<String> response = (JAXBElement<String>) template.marshalSendAndReceive(request);
		String status = response.getValue();
		System.out.println("Status : " + status);
	}

}
