import RPi.GPIO as GPIO
import time
from firebase import firebase
firebase = firebase.FirebaseApplication('https://parkingpi-910f3.firebaseio.com/', None)

globalDistance = 8 #Distancia minima para determinar se a vaga esta livre

TRIGS = [23,16,20,12,18,27]
ECHOS = [24,19,26,6,17,22]
# TRIG1 = 23
# ECHO1 = 24
# TRIG2 = 16
# ECHO2 = 19
# TRIG3 = 20
# ECHO3 = 26

# TRIG4 = 12
# ECHO4 = 6
# TRIG5 = 18
# ECHO5 = 17
# TRIG6 = 27
# ECHO6 = 22

def callSensor(TRIG, ECHO, sensor):
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(TRIG,GPIO.OUT)
	GPIO.setup(ECHO,GPIO.IN)

	GPIO.output(TRIG,False)
	print "Aguardando o Sensor Estabilizar - Sensor: " + str(sensor)
	time.sleep(1)

	GPIO.output(TRIG, True)
	time.sleep(0.00001)
	GPIO.output(TRIG, False)

	while GPIO.input(ECHO)==0:
		 pulse_start = time.time()

	while GPIO.input(ECHO)==1:
		pulse_end = time.time()

	pulse_duration = pulse_end - pulse_start

	distance = pulse_duration * 17150

	distance = round(distance, 2)
	
	if (distance < globalDistance) : n = 1
	else : n = 0
	#result = firebase.get('/Vagas',None)
	path = '/Vagas/' + str(sensor)
	result = firebase.put(path, 'status', n)
	print result

	
print "Mensurando a Distancia"

while True:
	for i in range(len(TRIGS)):
		callSensor(TRIGS[i],ECHOS[i],i+1)