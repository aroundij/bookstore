package io.aroundij.fitler;

import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;

import java.io.IOException;
import java.nio.charset.Charset;

public class LoggingFilter {
    @ServerRequestFilter
    public void request(ContainerRequestContext requestContext) throws IOException {
        Log.infov("Request Url To: [{0}]:[{1}]", requestContext.getMethod(), requestContext.getUriInfo().getAbsolutePath());
        Log.infov("Headers Request: [{0}]", requestContext.getHeaders());
        if (requestContext.hasEntity() && requestContext.getMediaType().isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
            String requestBody =
                    IOUtils.toString(requestContext.getEntityStream(), Charset.defaultCharset());
            Log.infov("Request Body: [{0}]", requestBody);
            requestContext.setEntityStream(IOUtils.toInputStream(requestBody, Charset.defaultCharset()));
        }
    }

    @ServerResponseFilter
    public void response(ContainerResponseContext responseContext) {
        Log.infov("Response Status: [{0}]", responseContext.getStatusInfo().getReasonPhrase());
        Log.infov("Headers Response: [{0}]", responseContext.getHeaders());
        if(responseContext.hasEntity()) {
            Log.infov("Response Body: [{0}]", responseContext.getEntity().toString());
        }
    }
}
