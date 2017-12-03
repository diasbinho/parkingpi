import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)

TRIG = 23
ECHO = 24

# TRIG1 = 23
# ECHO1 = 24
# TRIG2 = 23
# ECHO2 = 24
# TRIG3 = 23
# ECHO3 = 24


# n=1
GPIO.cleanup()
print "Mensurando a Distancia"
while True:


	GPIO.setup(TRIG,GPIO.OUT)
	GPIO.setup(ECHO,GPIO.IN)

	GPIO.output(TRIG,False)
	print "Aguardando o Sensor Estabilizar"
	time.sleep(2)


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

	print "A distancia eh: ",distance, " cm"
	time.sleep(0.5)


