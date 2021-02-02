# Protocols & packets
A protocol is a set of rules that allow devices to communicate with each other.

# TCP/IP
Is a suite of communication protocols used to interconnect devices on the Internet.

## IP (Internet Protocol)
Defines how to address and route a packet to make sure it arrives to the right destination.

## TCP (Transmission Control Protocol)
Manages how data is assembled/disassembled into a packet. TCP makes sure that every packet arrives at the destination with the right order.

### TCP/IP Layers or Communication/protocol stack
- Application: Application defined protocol, its data (HTTP/S, FTP, POP3)
- Transport: Maintain end to end communications (TCP, UDP)
- Network: Deals with packets and connects networks (IP, ICMP)
- Physical: Interconnects nodes or hosts in the network (Ethernet, ARP)

# ARP
Used for discovering the link layer address (MAC Address) associated with an internet layer address (IPv4/6).

# Sockets
Software structure that is used to send/receive data accross a network. On a server application, a socket would be bound to a port number and it would wait for a client to make a connection.

# Rendezvous socket
A socket is assigned a port to listen connections on. When a client connects, that connection gets a new port number assigned allowing the previous port to listen for new connections.

# Binding
The process of allocating a port number to a socket is called binding.

# NAT (Network Address Translation)
Is a method of remapping an IP into another using a table.
- Static: Provides a permanent mapping between the internal and the public IP address.
- Dynamic: Is used when you have a “pool” of public IP addresses that you want to assign to your internal hosts.

# Routing rules
Networks are interconnected with a device called router. This device is responsible of receiving and sending packets to the right destination (routing packets). The destination of a packet may be decided by a set of routing rules that a network admin may have defined.

# Switch
Is a networking hardware that connects devices on a computer network by forwading data between source and destination.
