import socket

# prepopulate all traffic lights with a state of 1.
pattern = {}
i = 1
for y in range(1, 96):
    pattern[i] = "1"
    i += 1

def server(rport=5602):
    
    # wait for a socket connection. 
    while True:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind(('localhost', rport))
        sock.listen(1)
        connection, address = sock.accept()
        print('connection from' + str(address))

        with connection:        
            while True:
                data = connection.recv(1024)

                # end connection if the client has quit.
                if not data:
                    print("connection ended")
                    break
                trafficLight_RAW = data.decode(encoding='UTF-8')

                # if the first character is a '0', the client wants to to read a state.
                if (trafficLight_RAW[0:1] == "0"):
                    print("returning switch pattern")                    
                    trafficPattern = pattern[int(trafficLight_RAW[1:])]
                    connection.send(bytes(str(trafficPattern) + "\r\n", 'UTF-8'))
                
                # if the first character is not a '0', the client wants to change a state.
                else:
                    payLoad = trafficLight_RAW[1:]
                    print("payload is: " + payLoad)
                    s_id = payLoad[:5]
                    trafficPattern = payLoad[5:]                    
                    print("s_id is " + s_id + " | current pattern is: " + trafficPattern)
                    pattern[int(s_id)] = trafficPattern                  
                    connection.send(bytes("OK" +"\r\n", 'UTF-8'))
              


if __name__ == '__main__':
    server()