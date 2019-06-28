/*ANDROID ARDUINO BLUETOOTH RC CAR  */ 
//this is arduino code//
/*-----------------------code start here -------------------------------------*/
int led = 8;        //led 
int led1 = A1;
int led2 = A2;
int led3 = A3;
int Motor_A_Enable = 3;
int outPin1 = 5;     //motor1 pwm
int outPin2 = 4;    //motor1 

int outPin4 = 7;   //motor2 
int outPin3 = 6;   //motor2 pwm
int Motor_B_Enable = 9;

char bt = ' ';       //BT  
int speedCar = 100;
/*------------------------------------------------------------------------------*/ 
void setup() 
{ 
Serial.begin(9600); 
Serial1.begin(9600); 
pinMode(outPin1,OUTPUT); 
pinMode(outPin2,OUTPUT); 
pinMode(outPin3,OUTPUT); 
pinMode(outPin4,OUTPUT); 
pinMode(led,OUTPUT); 
pinMode(led1,OUTPUT); 
pinMode(led2,OUTPUT); 
pinMode(led3,OUTPUT); 
} 
void loop() 
{ 
if (Serial1.available() > 0) 
{ 
 bt = Serial1.read(); 
 Serial.println(bt);
 digitalWrite(led, 1); 
 digitalWrite(led1, 1);
 digitalWrite(led2, 1);
 digitalWrite(led3, 1);
 /*________________________________________________________________________*/ 
  switch (bt)
  {
   case 'F':       //move forwards 
   { 
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,HIGH); 
     digitalWrite(outPin4,LOW); 
     break;
   } 
   case 'B':       //move backwards 
   { 
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,HIGH); 
     break;
   } 
   case  'S':     //stop!! 
   {    
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,LOW); 
     break;
   } 
   case 'R':   //right 
   { 
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,HIGH); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,LOW); 
     break;
   } 
   case 'L':     //left 
   { 
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin2,HIGH); 
     digitalWrite(outPin4,LOW); 
     break;
   } 
   case 'I':   //forward right 
   { 
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,HIGH); 
     digitalWrite(outPin2,LOW); 
     digitalWrite(outPin3,HIGH); 
     digitalWrite(outPin4,LOW); 
     break;
   } 
   case 'G':   //forward left 
   { 
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,HIGH); 
     digitalWrite(outPin3,HIGH);
     digitalWrite(outPin4,LOW); 
     break;
   }
   case 'H': {  // back left
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
     digitalWrite(outPin1,LOW); 
     digitalWrite(outPin2,HIGH); 
     digitalWrite(outPin3,LOW); 
     digitalWrite(outPin4,HIGH); 
     break;
    }
    case 'J': {  // back right
     analogWrite(Motor_A_Enable, speedCar);
     analogWrite(Motor_B_Enable, speedCar);
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
