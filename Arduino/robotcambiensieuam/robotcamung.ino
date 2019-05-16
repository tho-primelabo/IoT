// connect motor controller pins to Arduino digital pins
// motor one
//#include "analogWrite.h"
int trig = 7; //enA
int in1 = 5;
int in2 = 6;
// motor two
int echo = 8; // enB
int in3 = 9;
int in4 = 10;
//int LED =2;
int servoPin = 2;  
int bientro = 3;
#include <Servo.h> 
Servo myservo; 
void setup()
{
  // set all the motor control pins to outputs
  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
  pinMode(in1, OUTPUT);
  pinMode(in2, OUTPUT);
  pinMode(in3, OUTPUT);
  pinMode(in4, OUTPUT);
  //pinMode(LED, OUTPUT);
  myservo.attach(servoPin); 
}
void demoOne()
{
  // this function will run the motors in both directions at a fixed speed
  // turn on motor A
  digitalWrite(in1, HIGH);
  digitalWrite(in2, LOW);
  // set speed to 200 out of possible range 0~255
  //analogWrite(enA, 200);
  // turn on motor B
  digitalWrite(in3, HIGH);
  digitalWrite(in4, LOW);
  // set speed to 200 out of possible range 0~255
  //analogWrite(enB, 200);
  //delay(2000);
  // now change motor directions
  /*
  digitalWrite(in1, LOW);
  digitalWrite(in2, HIGH); 
  digitalWrite(in3, LOW);
  digitalWrite(in4, HIGH);
  delay(2000);*/
  // now turn off motors
  /*
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW); 
  digitalWrite(in3, LOW);
  digitalWrite(in4, LOW);*/
}
void demoTwo()
{
  // this function will run the motors across the range of possible speeds
  // note that maximum speed is determined by the motor itself and the operating voltage
  // the PWM values sent by analogWrite() are fractions of the maximum speed possible
  // by your hardware
  // turn on motors
  digitalWrite(in1, LOW);
  digitalWrite(in2, HIGH); 
  digitalWrite(in3, LOW);
  digitalWrite(in4, HIGH);
  // accelerate from zero to maximum speed
 
/* for (int i = 0; i < 256; i++) {
analogWrite(enA, i);
analogWrite(enB, i);
delay(20);
}*/
// decelerate from maximum speed to zero
/*
for (int i = 255; i > 0; --i)
  {
    analogWrite(enA, i);
    analogWrite(enB, i);
    delay(20);
  }
 */
  
  // now turn off motors
  /*
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW); 
  digitalWrite(in3, LOW);
  digitalWrite(in4, LOW); */
}
void loop()
{
  int value = analogRead(bientro);// Đọc giá trị biến trở
   int servoPos = map(value, 0, 1023, 0, 180);
   myservo.write(servoPos);
  unsigned long duration; // biến đo thời gian
    int distance;           // biến lưu khoảng cách
    
    /* Phát xung từ chân trig */
    digitalWrite(trig,0);   // tắt chân trig
    delayMicroseconds(2);
    digitalWrite(trig,1);   // phát xung từ chân trig
    delayMicroseconds(5);   // xung có độ dài 5 microSeconds
    digitalWrite(trig,0);   // tắt chân trig

    /* Tính toán thời gian */
    // Đo độ rộng xung HIGH ở chân echo. 
    duration = pulseIn(echo,HIGH);  
    // Tính khoảng cách đến vật.
    distance = int(duration/2/29.412);
    
    /* In kết quả ra Serial Monitor */
    Serial.print(distance);
    Serial.println("cm");
   // digitalWrite(LED, HIGH);   // turn the LED on (HIGH is the voltage level
    if (distance  > 10 ) {
      demoOne();//forward
      myservo.write(90);
    }
     else {
      demoTwo(); // back
     //delay(100);
     //demoOne();//forward
       // Cho servo quay một góc là servoPos độ
    myservo.write(0);
     }
    //digitalWrite(LED, LOW);    // turn the LED off by making the voltage LOW
   
    
    Serial.println(servoPos);
    delay(1000);
  }
