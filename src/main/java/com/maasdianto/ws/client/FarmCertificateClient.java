package com.maasdianto.ws.client;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.maasdianto.certification.FarmCertification;
import com.maasdianto.certification.GetFarmCertificationByStatusRequest;
import com.maasdianto.certification.GetFarmCertificationByStatusResponse;

public class FarmCertificateClient {
	
	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:client-ws-ctx.xml");
		WebServiceTemplate template = (WebServiceTemplate) context.getBean("webserviceTemplate");
		
		GetFarmCertificationByStatusRequest request = new GetFarmCertificationByStatusRequest();
		request.setStatus("invalid");
		
		GetFarmCertificationByStatusResponse response = (GetFarmCertificationByStatusResponse) template.marshalSendAndReceive(request);
		List<FarmCertification> list = response.getFarmCertification();
		for (FarmCertification farm : list) {
			System.out.println("ID : " + farm.getId() + " , Start Date: " + farm.getStartDate() + ", End Date : " + farm.getEndDate());
		}
		
	}

}
