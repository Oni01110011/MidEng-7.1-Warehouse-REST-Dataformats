# MidEng 7.2 gRPC Framework [GK]

---

Autor: *Bernhard Aichinger-Ganas*

Date: *3.12.2024*

# Introduction

This exercise is intended to demonstrate the functionality and implementation of Remote Procedure Call (RPC) technology using the Open Source High Performance gRPC Framework *gRPC Frameworks* ([https://grpc.io](https://grpc.io/)). It shows that this framework can be used to develop a middleware system for connecting several services developed with different programming languages.

Document all individual implementation steps and any problems that arise in a log (Markdown). Create a GITHUB repository for this project and add the link to it in the comments.

# Screen shots - toutorial

![image.png](..%2F..%2F..%2F..%2FDANILO%7E1%2FAppData%2FLocal%2FTemp%2FRar%24DRa21160.49103%2Fimage.png)

# Dependencies

First, it is crucial to implement all the necessary dependencies. Without them, the program will not execute properly.

jsx
dependencies {
implementation 'io.grpc:grpc-netty:1.68.1'
implementation 'io.grpc:grpc-protobuf:1.68.1'
implementation 'io.grpc:grpc-stub:1.68.1'
implementation 'io.grpc:grpc-api:1.68.1'
compileOnly 'javax.annotation:javax.annotation-api:1.3.2'

    implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.1'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.18.1'
    implementation 'com.fasterxml.jackson.core:jackson-core:2.18.1'
    implementation 'com.fasterxml.jackson.core:jackson-annotations:2.18.1'
}


After we defined those dependencies in our build.gradle File we should run them so they get embeddet. Then we should bild the gradle.

# Proto Election

Afterward, I created the .proto file where I defined the structure and communication protocol for the gRPC service. Here’s what I implemented:

- *Service Definition*: I created the ElectionService with an RPC method called transferElectionData. This method takes an ElectionDataRequest as input and returns an ElectionDataResponse.
- *Message Structures*: I designed the following:
    - ElectionDataRequest to wrap the election data being sent.
    - ElectionDataResponse to contain a response message from the server.
    - ElectionData as the main structure to store regional information (e.g., region ID, name, address, and timestamp) along with a list of parties and their vote counts.
    - Party to represent individual party data with fields for partyID and amountVotes.

This file acts as the contract for the client-server communication, ensuring consistent data exchange.

# ElectionClient

Afterward, I created the ElectionClient class, which acts as the client in the gRPC communication. Here's what I implemented:

- *Connecting to the Server*:

  I initialized a ManagedChannel to establish a connection to the gRPC server running on a specific host and port.

- *Stub Creation*:

  I created a blocking stub using ElectionServiceGrpc.newBlockingStub(channel), which allows the client to call the transferElectionData RPC method synchronously.

- *Data Preparation*:

  I wrote a method, createElectionData, to construct an instance of ElectionData. This includes setting the region details (e.g., ID, name, address) and adding a list of parties with their vote counts.

- *Sending Data*:

  I implemented the sendElectionData method, which:

    - Builds an ElectionDataRequest containing the prepared ElectionData.
    - Sends the request to the server via the stub.
    - Prints the server's response to confirm successful communication.

Then we need to define the Service in the ElectionServiceImpl Class.

And as the last step we should implement the service in our MyServer Class as follows

jsx
public void start() throws IOException {
server = ServerBuilder.forPort(PORT)
.addService(new HelloWorldServiceImpl())     // Register HelloWorld service
.addService(new ElectionServiceImpl())       // Register Election service
.build()
.start();

        System.out.println("Server started, listening on port " + PORT);
    }


# Questions & Answers

- What is gRPC and why does it work accross languages and platforms?
    - gRPC is a Remote Procedure Call framework developed by Google that enables communication between different applications and platforms in various programming languages. It works because it has a unified interface based on the open standard HTTP/2
- Describe the RPC life cycle starting with the RPC client?
    - The RPC client sends a request to the server's stub, the stub serializes the request, sends it to the server, the server processes the request, sends a response back, and the client stub deserializes the response.
- Describe the workflow of Protocol Buffers?
    - Define the message schema in a .proto file, compile it using protoc to generate code, use the generated code to serialize/deserialize data and transmit it.
- What are the benefits of using protocol buffers?
    - Efficient serialization (compact and fast).
    - Cross-platform compatibility.
    - Strongly typed schema for structured data.
- When is the use of protocol not recommended?
- List 3 different data types that can be used with protocol buffers?
    - int32 (integer).
    - string (text).
    - bool (boolean).