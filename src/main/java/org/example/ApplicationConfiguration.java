package org.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    /**
     * A user can configure a {@link Server} by providing an {@link ArmeriaServerConfigurator} bean.
     */
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        // Customize the server using the given ServerBuilder. For example:
        return builder -> {
            // Add DocService that enables you to send Thrift and gRPC requests from web browser.
            DocService docService = DocService.builder()
                    .exampleRequests(HelloController.class,
                            "hello")
                    .build();
            builder.serviceUnder("/docs", docService);

            // Log every message which the server receives and responds.
            builder.decorator(LoggingService.newDecorator());

            // Write access log after completing a request.
            builder.accessLogWriter(AccessLogWriter.combined(), false);

            // You can also bind annotated HTTP services and asynchronous RPC services such as Thrift and gRPC:
            // builder.annotatedService("/rest", service);
            // builder.service("/thrift", THttpService.of(...));
            // builder.service(GrpcService.builder()...build());
        };
    }
}
