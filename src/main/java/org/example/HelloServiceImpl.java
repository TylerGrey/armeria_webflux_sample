package org.example;

import example.armeria.grpc.Hello;
import example.armeria.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private final Token token;

    @Override
    public void hello(Hello.HelloRequest request, StreamObserver<Hello.HelloReply> responseObserver) {
        Hello.HelloReply value = Hello.HelloReply.getDefaultInstance();
        responseObserver.onNext(value);
        responseObserver.onCompleted();
    }
}
