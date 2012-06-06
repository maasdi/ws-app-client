package com.maasdianto.ws.client;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.maasdianto.certificate.FarmCertification;
import com.maasdianto.certificate.GetFarmCertificationByStatusRequest;
import com.maasdianto.certificate.GetFarmCertificationByStatusResponse;

public class FarmCertificateClient {
	
	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:client-ws-ctx.xml");
		WebServiceTemplate template = context.getBean(WebServiceTemplate.class);
		
		GetFarmCertificationByStatusRequest request = new GetFarmCertificationByStatusRequest();
		request.setStatus("invalid");
		
		GetFarmCertificationByStatusResponse response = (GetFarmCertificationByStatusResponse) template.marshalSendAndReceive(request);
		List<FarmCertification> list = response.getFarmCertification();
		for (FarmCertification farmCertification : list) {
			System.out.println(farmCertification.getId());
			System.out.println(farmCertification.getStatus());
		}
		
	}

}
