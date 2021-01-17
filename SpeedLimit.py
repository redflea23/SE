import socket

# initialize a list of all the speed limit signs an prepopulate it 
# with "50". This is also done for safety, if the sensor servers a restarted, 
# they are initialized with a sensible value.
limits = {}
i = 1
for y in range(1, 96):
    limits[i] = "50"
    i += 1

def server(rport=5600):
    
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
                speedLimit_RAW = data.decode(encoding='UTF-8')

                # if the payload start with character '0', the client wants to read a speed limit.
                if (speedLimit_RAW[0:1] == "0"):
                    print("returning Speed limit")
                    print(speedLimit_RAW[1:])
                    
                    speedLimit = limits[int(speedLimit_RAW[1:])]
                    connection.send(bytes(str(speedLimit) + "\r\n", 'UTF-8'))

                # if the payload does not start with character '0', the client wants to set a new speed limit.
                else:
                    payLoad = speedLimit_RAW[1:]
                    s_id = payLoad[:5]
                    limit = payLoad[5:]
                    print("s_id is " + s_id + " | limit is: " + limit)
                    limits[int(s_id)] = limit                  
                    connection.send(bytes("OK" +"\r\n", 'UTF-8'))
                
                


if __name__ == '__main__':
    server()
