/*
 * TM1637.cpp
 * A library for the 4 digit display
 *
 * Copyright (c) 2012 seeed technology inc.
 * Website    : www.seeed.cc
 * Author     : Frankie.Chu
 * Create Time: 9 April,2012
 * Change Log :
 *
 * The MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
#include "TM1637.h"
#include <RTClib.h>
#define CLK 2//pins definitions for TM1637 and can be changed to other ports
#define DIO 3
TM1637 tm1637(CLK,DIO);
RTC_DS3231 rtc;
//char t[32];
int sensorPin = A0;
int sensorValue = 0;
void setup()
{
   Serial.begin(9600);
   //pinMode(sensorPin, INPUT); 
  tm1637.init();
  tm1637.set(BRIGHT_TYPICAL);//BRIGHT_TYPICAL = 2,BRIGHT_DARKEST = 0,BRIGHTEST = 7;
  if (! rtc.begin()) 
  {
    Serial.print("Couldn't find RTC");
    while (1);
  }
  //rtc.adjust(DateTime(F(__DATE__),F(__TIME__)));
}
void show_time(DateTime &t){
  tm1637.display(0,t.hour()/10);
  tm1637.display(1,t.hour()%10);
  tm1637.display(2,t.minute()/10);
  tm1637.display(3,t.minute()%10);
}
void loop()
{
   DateTime now = rtc.now();

  tm1637.point(POINT_ON);
  show_time(now);  // changes take effect here
  delay(500);
  tm1637.point(POINT_OFF);
  show_time(now);

  sensorValue = analogRead(sensorPin);
  Serial.println(sensorValue);
  delay(500);
}
