package org.example;

import example.armeria.grpc.Hello;
import example.armeria.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(Hello.HelloRequest request, StreamObserver<Hello.HelloReply> responseObserver) {
        Token token = ApplicationContextProvider.getApplicationContext().getBean(Token.class);
        System.out.println("token = " + token.getToken());
        Hello.HelloReply value = Hello.HelloReply.getDefaultInstance();
        responseObserver.onNext(value);
        responseObserver.onCompleted();
    }
}
