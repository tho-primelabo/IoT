/*
https://somtips.com
https://youtube.com/somtips
*/
#include <RTClib.h>
#include <Wire.h>
#include <TM1637Display.h>
#define CLK 2 //pins definitions for TM1637 and can be changed to other ports
#define DIO 3
TM1637Display tm1637(CLK,DIO);
RTC_DS3231 rtc;
 
char t[32];
 
void setup() 
{
  Serial.begin(9600);
  Wire.begin();
  tm1637.setBrightness(0x0a); //set the diplay to maximum brightness
  tm1637.setColon(true);
  if (! rtc.begin()) 
  {
    Serial.print("Couldn't find RTC");
    while (1);
  }
  rtc.adjust(DateTime(F(__DATE__),F(__TIME__)));
  //rtc.adjust(DateTime(2014, 1, 21, 3, 0, 0));
  
 
}
 
void loop()
{
  DateTime now = rtc.now();
  int ti = now.hour() * 100 + now.minute();
  tm1637.showNumberDec(ti, true);
 
  sprintf(t, "%02d:%02d:%02d %02d/%02d/%02d",  now.hour(), now.minute(), now.second(), now.day(), now.month(), now.year());  
  
  Serial.print(F("Date/Time: "));
  Serial.println(t);
 
  delay(1000);
}
