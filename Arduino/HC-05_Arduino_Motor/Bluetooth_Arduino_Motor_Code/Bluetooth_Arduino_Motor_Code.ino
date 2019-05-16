int Motor_A_Enable = 5;
int Motor_A_Reverse = 3;
int Motor_A_Forward = 4;

int Motor_B_Enable = 10;
int Motor_B_Reverse = 9;
int Motor_B_Forward = 8;

//Front Light pins   
int front_light1 = A0;
int front_light2 = A1;
//Back light pins
int back_light1 = A2;
int back_light2 = A3;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  
  pinMode(Motor_A_Enable, OUTPUT);
  pinMode(Motor_A_Forward, OUTPUT);
  pinMode(Motor_A_Reverse, OUTPUT);

  pinMode(Motor_B_Enable, OUTPUT);
  pinMode(Motor_B_Forward, OUTPUT);
  pinMode(Motor_B_Reverse, OUTPUT);
  
  pinMode(front_light1, OUTPUT);  
  pinMode(back_light1, OUTPUT);
  pinMode(front_light2, OUTPUT);  
  pinMode(back_light2, OUTPUT);
}

void loop() {

  if(Serial.available() > 0)
  {
    char data;
    data = Serial.read();
    Serial.write(Serial.read());

    switch (data)
    {
      case '1': //FORWARD
        analogWrite(Motor_B_Enable, 100);
        analogWrite(Motor_A_Enable, 100);
        digitalWrite(Motor_A_Reverse, HIGH);
        digitalWrite(Motor_B_Reverse, HIGH);
        digitalWrite(Motor_A_Forward, LOW);
        digitalWrite(Motor_B_Forward, LOW);
        
        digitalWrite(front_light1, HIGH);
        digitalWrite(front_light2, HIGH);
        digitalWrite(back_light1, LOW);
        digitalWrite(back_light2, LOW);
        break;
      case '2': //REVERSE
        analogWrite(Motor_B_Enable, 100);
        analogWrite(Motor_A_Enable, 100);
        digitalWrite(Motor_A_Reverse, LOW);
        digitalWrite(Motor_B_Reverse, LOW);
        digitalWrite(Motor_A_Forward, HIGH);
        digitalWrite(Motor_B_Forward, HIGH);

        digitalWrite(back_light1, HIGH);
        digitalWrite(back_light2, HIGH);
        //delay(500);
        digitalWrite(front_light1, LOW);
        digitalWrite(front_light2, LOW);
        break;
      case '3': //FORWARD LEFT
        analogWrite(Motor_A_Enable, 100);
        analogWrite(Motor_B_Enable, 0);
        digitalWrite(Motor_A_Reverse, LOW);
        digitalWrite(Motor_A_Forward, HIGH);
        digitalWrite(Motor_B_Reverse, LOW);
        digitalWrite(Motor_B_Forward, LOW);
        break;
      case '4': //FORWARD RIGHT
        analogWrite(Motor_B_Enable, 100);
        analogWrite(Motor_A_Enable, 0);
        digitalWrite(Motor_B_Reverse, LOW);
        digitalWrite(Motor_B_Forward, HIGH);
         digitalWrite(Motor_A_Forward, LOW);
        digitalWrite(Motor_A_Reverse, LOW);
        break;
      case '5': //REVERSE LEFT
        analogWrite(Motor_A_Enable, 100);
        analogWrite(Motor_B_Enable, 0);
        digitalWrite(Motor_A_Reverse, HIGH);
        digitalWrite(Motor_A_Forward, LOW);
        break;
      case '6': //REVERSE RIGHT
        analogWrite(Motor_B_Enable, 100);
        analogWrite(Motor_A_Enable, 0);
        digitalWrite(Motor_B_Reverse, HIGH);
        digitalWrite(Motor_B_Forward, LOW);
        break;
      default: //If bluetooth module receives any value not listed above, both motors turn off
        analogWrite(Motor_A_Enable, 0);
        analogWrite(Motor_B_Enable, 0);
         digitalWrite(Motor_A_Forward, LOW);
        digitalWrite(Motor_B_Forward, LOW);
        digitalWrite(Motor_A_Reverse, LOW);
        digitalWrite(Motor_B_Reverse, LOW);
    }
  }
}
