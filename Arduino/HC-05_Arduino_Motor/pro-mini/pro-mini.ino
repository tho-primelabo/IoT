#include <SoftwareSerial.h>

SoftwareSerial mySerial(11,10);
int led = 13;        //led 
int led4 = 4;        //led 4
int outPin1 = 5;     //motor1 pwm R
int outPin2 = 12;    //motor1 L
int outPin4 = 9;   //motor2 pwm B
int outPin3 = 7;   //motor2 F
char bt = ' ';
void setup()
{
  mySerial.begin(9600);
  Serial.begin(9600);
  pinMode(led,OUTPUT); 
   pinMode(led4,OUTPUT);
   pinMode(outPin1,OUTPUT); 
  pinMode(outPin2,OUTPUT); 
  pinMode(outPin3,OUTPUT); 
  pinMode(outPin4,OUTPUT);
}

// Add the main program code into the continuous loop() function
void loop()
{
  digitalWrite(led,HIGH);
  if(mySerial.available()){
    bt = mySerial.read();
    Serial.println(bt);
      
    /*________________________________________________________________________*/ 
   if(bt == 'F')        //move forwards 
   { 
     //digitalWrite(outPin3,HIGH); 
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,HIGH); 
     digitalWrite(led4,HIGH); 
     digitalWrite(outPin4,LOW); 
   } 
   else if (bt == 'B')       //move backwards 
   { 
     digitalWrite(outPin1,HIGH); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,LOW); 
      
   } 
   else if (bt == 'S')     //stop!! 
   {    
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,LOW); 
     //digitalWrite(led,LOW); 
      digitalWrite(led4,LOW);
   } 
   else if (bt == 'R')    //right 
   { 
     digitalWrite(outPin4,HIGH); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin1,LOW); 
   } 
   else if (bt == 'L')     //left 
   { 
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin3,HIGH); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin4,LOW); 
   } 
   else if (bt == 'I')    //forward right 
   { 
     
     digitalWrite(outPin1,HIGH); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,HIGH); 
     digitalWrite(outPin4,LOW); 
   } 
   else if (bt == 'G')    //forward left 
   { 
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,HIGH); 
     digitalWrite(outPin3,HIGH);
     digitalWrite(outPin4,LOW); 
   }
   else if (bt == 'H') {  // back left
      digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,HIGH); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,HIGH); 
    }
    else if (bt == 'J') {  // back right
      
      digitalWrite(outPin1,HIGH); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,HIGH); 
      }
   }
   else {
      
   }
  
}
