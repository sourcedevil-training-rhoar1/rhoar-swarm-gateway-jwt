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

import com.sourcedevil.ref.rhoar.swarm.gateway.jwt.proxy.RhoarRaffleCreateEntryProxy;
import com.sourcedevil.ref.rhoar.swarm.gateway.jwt.proxy.RhoarRaffleWinnerProxy;





@Path("/")
public class APIGatewayResource {
	
	@Inject
	@ConfigProperty(name = "rhoarRaffleWinnerUrl")
	private String rhoarRaffleWinnerUrl;
	
	@Inject
	@ConfigProperty(name = "rhoarRaffleCreateEntryUrl")
	private String rhoarRaffleCreateEntryUrl;

	
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
	

	private RhoarRaffleWinnerProxy buildRhoarRaffleWinnerUrlProxy() {
		Client client = ClientBuilder.newClient();
		System.out.println("recurring url " + rhoarRaffleWinnerUrl);
		WebTarget target = client.target(rhoarRaffleWinnerUrl);
		ResteasyWebTarget restEasyTarget = (ResteasyWebTarget) target;
		return restEasyTarget.proxy(RhoarRaffleWinnerProxy.class);
	}
	
	private RhoarRaffleCreateEntryProxy buildRhoarRaffleCreateEntryUrl() {
		Client client = ClientBuilder.newClient();
		System.out.println("hw url: " + rhoarRaffleCreateEntryUrl);
		WebTarget target = client.target(rhoarRaffleCreateEntryUrl);
		ResteasyWebTarget restEasyTarget = (ResteasyWebTarget) target;
		return restEasyTarget.proxy(RhoarRaffleCreateEntryProxy.class);
	}

	@GET
    @Path("/raffle-winner")
    @Produces("application/json")
    public String apiRaffleWinner() {		
        RhoarRaffleWinnerProxy proxy = buildRhoarRaffleWinnerUrlProxy();        
        String response = proxy.getWinner();
        return response;
    }

    @GET
    @Path("/secured/create-entry")
    @Produces(MediaType.TEXT_PLAIN)
    //TODO agregar parametros faltantes
    //TODO crear metodo sin seguridad
    public String apiSecuredCreateEntry() {
    	RhoarRaffleCreateEntryProxy proxy = buildRhoarRaffleCreateEntryUrl();
        String response = proxy.createRaffleEntry();
        return response;
    }
    
      
    
    @GET
    @Path("/secured/local-hw-test")
    @Produces("text/plain")    
    public String holaHwTest() {       
        return "Ud esta dentro de un recurso protegido: ";
    }
    
    @GET
    @Path("/secured/raffle-winner")
    @Produces("application/json")    
    public String apiSecuredRaffleWinner() {       
    	RhoarRaffleWinnerProxy proxy = buildRhoarRaffleWinnerUrlProxy();        
        String response = proxy.getWinner();
        return response;
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
