import socket
import random
import time

path = {
        0: "00001",
        1: "00002",
        2: "00053",
        3: "00054",
        4: "00017",
        5: "00018",
        6: "00073",
        7: "00074",
        8: "00033",
        9: "00034",
        10: "00093",
        11: "00094",
        12: "-1"
    }

def rfid(rport=5601):
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(('localhost', rport))
    sock.listen(1)
    connection, address = sock.accept()
    currentStep = 0
    

    with connection:
        while True:
            start_time = time.time()
            time.sleep(1)
            msg = randRFID()
            # print("keyValue pair: " + msg)
            connection.send(bytes(msg + "\r\n", 'UTF-8'))
            time.sleep(1)
            emergCar = vehicleStep(currentStep)
            print(emergCar)
            if (emergCar != "-1"):
                connection.send(bytes(emergCar + "\r\n", 'UTF-8'))
                currentStep += 1
            
            



def randRFID():
    typ = random.randint(1, 6)
    t = typ
    t *= 10000
    v_id = random.randint(2, 6)
    t += v_id
    t *= 100000    
    s_id = random.randint(1, 96)
    t += s_id
    t *= 10
    active = 0
    t += active
    t *= 1000
    if (typ == 4 or typ == 6):
        t += random.randint(1, 1000)       
    t_str = str(t)   
    return t_str

def vehicleStep(currentStep):
    if(currentStep < 12):
        str = "10001" + path.get(currentStep) + "1000"
        
    else:
        return "-1"    
    return str

if __name__ == '__main__':
    rfid()
