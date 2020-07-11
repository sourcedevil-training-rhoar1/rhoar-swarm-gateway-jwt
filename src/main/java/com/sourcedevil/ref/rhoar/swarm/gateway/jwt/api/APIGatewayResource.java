package com.sourcedevil.ref.rhoar.swarm.gateway.jwt.api;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.sourcedevil.ref.rhoar.swarm.gateway.jwt.proxy.HwTestProxy;
import com.sourcedevil.ref.rhoar.swarm.gateway.jwt.proxy.RecurringpaymentsProxy;





@Path("/")
public class APIGatewayResource {
	
	@Inject
	@ConfigProperty(name = "rhoarRecurringpaymentsUrl")
	private String rhoarRecurringpaymentstUrl;
	
	@Inject
	@ConfigProperty(name = "rhoarHwUrl")
	private String rhoarHwUrl;

	
    @Context
    private SecurityContext securityContext;

    @Context
    private HttpServletRequest servletRequest;

	
	
	@GET
	@Path("/hw")
	@Produces(MediaType.TEXT_PLAIN)
	public String hw() {
		return "HW";
	}
	

	private RecurringpaymentsProxy buildRecurringpaymentsProxy() {
		Client client = ClientBuilder.newClient();
		System.out.println("recurring url " + rhoarRecurringpaymentstUrl);
		WebTarget target = client.target(rhoarRecurringpaymentstUrl);
		ResteasyWebTarget restEasyTarget = (ResteasyWebTarget) target;
		return restEasyTarget.proxy(RecurringpaymentsProxy.class);
	}
	
	private HwTestProxy buildRhoarHwUrlProxy() {
		Client client = ClientBuilder.newClient();
		System.out.println("hw url: " + rhoarHwUrl);
		WebTarget target = client.target(rhoarHwUrl);
		ResteasyWebTarget restEasyTarget = (ResteasyWebTarget) target;
		return restEasyTarget.proxy(HwTestProxy.class);
	}

	@GET
    @Path("/recurringpayments/{phoneNumber}")
    @Produces("application/json")
    public String getRecurringpayments(@PathParam("phoneNumber") Long phone) {		
        RecurringpaymentsProxy proxy = buildRecurringpaymentsProxy();
        System.out.println("telefono a utilizar: " + phone);
        String response = proxy.getRecurringpayments(phone);
        return response;
    }

    @GET
    @Path("/secured/rhoar-hw-test")
    @Produces(MediaType.TEXT_PLAIN)
    public String apihw() {
    	HwTestProxy proxy = buildRhoarHwUrlProxy();
        String response = proxy.hw();
        return response;
    }
    
      
    
    @GET
    @Path("/secured/local-hw-test")
    @Produces("text/plain")    
    public String holaHwTest() {       
        return "Ud esta dentro de un recurso protegido: ";
    }    
    
    
    @GET
    @Path("/logout")
    @Produces("text/plain")
    public String logout() throws ServletException {
        servletRequest.logout();
        return "Logged out";
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    public String health() {
        return "I'm ok";
    }
    
    
    
}
