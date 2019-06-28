#include <SoftwareSerial.h>

SoftwareSerial mySerial(11,13);
int led = 13;        //led 
int outPin2 = 4;        //led 4
int outPin1 = 5;     //motor1 pwm R
int Motor_A_Enable = 3 ;
int led4 = 12;    //motor1 L
int outPin4 = 9;   //motor2 pwm B
int outPin3 = 7;   //motor2 F
int Motor_B_Enable = 10 ;
char data = ' ';
int speedCar = 100;
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
    data = mySerial.read();
    Serial.println(data);
      
    /*________________________________________________________________________*/ 
    switch (data)
    {
     case 'F':        //move forwards 
     { 
       //digitalWrite(outPin3,HIGH); 
       analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
       digitalWrite(outPin1,LOW); 
       digitalWrite(outPin2,HIGH); 
        digitalWrite(outPin3,LOW); 
       digitalWrite(led4,HIGH); 
       digitalWrite(outPin4,LOW); 
       break;
     } 
     case 'B':       //move backwards 
     { 
      analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
       digitalWrite(outPin1,HIGH); 
       digitalWrite(outPin2,LOW); 
       digitalWrite(outPin3,LOW); 
       digitalWrite(outPin4,LOW); 
       break;
        
     } 
     case 'S':     //stop!! 
     {    
      analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
       digitalWrite(outPin1,LOW); 
       digitalWrite(outPin2,LOW); 
       digitalWrite(outPin3,LOW); 
       digitalWrite(outPin4,LOW); 
       //digitalWrite(led,LOW); 
        digitalWrite(led4,LOW);
        break;
     } 
     case 'R':   //right 
     { 
      analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
       digitalWrite(outPin4,HIGH); 
       digitalWrite(outPin2,LOW); 
       digitalWrite(outPin3,LOW); 
       digitalWrite(outPin1,LOW); 
       break;
     } 
     case 'L':    //left 
     { 
      analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
       digitalWrite(outPin1,LOW); 
       digitalWrite(outPin3,HIGH); 
       digitalWrite(outPin2,LOW); 
       digitalWrite(outPin4,LOW); 
       break;
     } 
     case 'I':    //forward right 
     { 
       analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
       digitalWrite(outPin1,HIGH); 
       digitalWrite(outPin2,LOW); 
       digitalWrite(outPin3,HIGH); 
       digitalWrite(outPin4,LOW); 
       break;
     } 
     case 'G':   //forward left 
     { 
      analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
       digitalWrite(outPin1,LOW); 
       digitalWrite(outPin2,HIGH); 
       digitalWrite(outPin3,HIGH);
       digitalWrite(outPin4,LOW); 
       break;
     }
     case 'H': {  // back left
        analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
        digitalWrite(outPin1,LOW); 
       digitalWrite(outPin2,HIGH); 
       digitalWrite(outPin3,HIGH); 
       digitalWrite(outPin4,LOW); 
       break;
      }
      case 'J': {  // back right
        analogWrite(Motor_B_Enable, speedCar);
        analogWrite(Motor_A_Enable, speedCar);
        digitalWrite(outPin1,HIGH); 
       digitalWrite(outPin2,LOW); 
       digitalWrite(outPin3,LOW); 
       digitalWrite(outPin4,HIGH); 
       break;
      }
       case '1':speedCar = 115;break;
        case '2':speedCar = 130;break;
        case '3':speedCar = 145;break;
        case '4':speedCar = 160;break;
        case '5':speedCar = 175;break;
        case '6':speedCar = 190;break;
        case '7':speedCar = 205;break;
        case '8':speedCar = 220;break;
        case '9':speedCar = 235;break;
        case 'q':speedCar = 255;break;
      default: //If bluetooth module receives any value not listed above, both motors turn off
        analogWrite(Motor_A_Enable, 0);
        analogWrite(Motor_B_Enable, 0);
        
     }
     
  }
  
}
