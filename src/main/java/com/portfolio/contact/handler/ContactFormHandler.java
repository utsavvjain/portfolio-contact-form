package com.portfolio.contact.handler;

import com.google.gson.Gson;
import com.portfolio.contact.beans.ContactForm;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;

import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class ContactFormHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
        private Gson gson = new Gson();

        static final String FROM = System.getenv("SENDER_EMAIL");
        static final String TO = System.getenv("RECEIVER_EMAIL");
        static final String SUBJECT = "PORTFOLIO CONTACT FORM";

        public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                        final Context context) {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("X-Custom-Header", "application/json");
                LambdaLogger logger = context.getLogger();
                ContactForm contactForm = gson.fromJson(input.getBody(), ContactForm.class);
                logger.log(contactForm.toString());
                AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                                .withRegion(Regions.AP_SOUTH_1).build();
                SendEmailRequest emailRequest = new SendEmailRequest()
                                .withDestination(
                                                new Destination().withToAddresses(TO))
                                .withMessage(new Message()
                                                .withBody(new Body()
                                                                .withText(new Content()
                                                                                .withCharset("UTF-8")
                                                                                .withData(contactForm.toString())))
                                                .withSubject(new Content()
                                                                .withCharset("UTF-8").withData(SUBJECT)))
                                .withSource(FROM);
                client.sendEmail(emailRequest);
                APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
                return response
                                .withStatusCode(200).withHeaders(headers);
        }

}
