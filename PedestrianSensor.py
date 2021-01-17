import socket
import random


def server(rport=5603):

    # initialize the buffer that simulates the pedestrians sensors and 
    # prepopulate them with random numbers.
    buffer = {}
    random.seed(128)
    for y in range(1, 96):
        buffer[y] = (random.randint(0, 37), random.randint(0, 37))

    # wait for a socket connection.    
    while True:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind(('localhost', rport))
        sock.listen(1)
        connection, address = sock.accept()
        print('connection from' + str(address))

        # when a connection is astablished read the sensor_id form the payload
        # and return the sensor data [for testing consistencythe reading is increase by one each time it is called.]
        with connection:        
            while True:
                data = connection.recv(1024)

                # end connection if the client has quit.
                if not data:
                    print("connection ended")
                    break
                buffer_RAW = data.decode(encoding='UTF-8')                
                print("returning pedestrian buffer")                
                    
                bufferData = buffer[int(buffer_RAW[1:])]
                first = bufferData[0]
                second = bufferData[1]
                print("first is: " + str(first))
                print("second is: " + str(second))
                #first = first + randint(-3, + 3)
                #second = second + randint(-3, + 3)

                # for unit testing
                first = first + 1
                second = second + 1
                if first < 0:
                    first = 0
                if first > 999:
                    first = 999
                if second < 0:
                    second = 0
                if second > 999:
                    second = 999
                buffer[int(buffer_RAW[1:])] = (first, second)                
                firstString = str(first).zfill(3)
                secondString = str(second).zfill(3)                
                print("firstSecond is: " + str(firstString))
                print("secondSecond is: " + str(secondString))
                connection.send(bytes(firstString + secondString + "\r\n", 'UTF-8'))
  

if __name__ == '__main__':
    server()