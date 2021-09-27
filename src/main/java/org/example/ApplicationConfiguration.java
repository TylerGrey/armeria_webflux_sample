package org.example;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.HttpServiceWithRoutes;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import io.grpc.protobuf.services.ProtoReflectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final HelloServiceImpl helloService;

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
            HttpServiceWithRoutes grpcService =
                    GrpcService.builder()
                            .addService(helloService)
                            // See https://github.com/grpc/grpc-java/blob/master/documentation/server-reflection-tutorial.md
                            .addService(ProtoReflectionService.newInstance())
                            .supportedSerializationFormats(GrpcSerializationFormats.values())
                            .enableUnframedRequests(true)
                            // You can set useBlockingTaskExecutor(true) in order to execute all gRPC
                            // methods in the blockingTaskExecutor thread pool.
                            // .useBlockingTaskExecutor(true)
                            .build();
            builder.service(grpcService);
        };
    }
}
