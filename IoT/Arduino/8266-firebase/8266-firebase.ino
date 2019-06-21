#include <ESP8266WiFi.h>

#include <FirebaseArduino.h>

#define WIFI_SSID "MobileZone"
#define WIFI_PASSWORD "KFC@2018"
#define FIREBASE_HOST "winged-bliss-237302.firebaseio.com"
#define FIREBASE_AUTH "qXwFALB50knO5W4lXOp5jWqIkoNu7lLRufz6HTja"

#define LED5 D5
#define LED1 D1
#define LED3 D3
#define LED4 D4
#define LED6 D6
#define LED7 D7
#define LED8 D8
#define LED9 D9
#define LED10 D10

#include "DHT.h"
#define DHTTYPE DHT11 
#define DHTPIN D2     // D2
DHT dht(DHTPIN, DHTTYPE);

void setup() {

  pinMode(LED1,OUTPUT);  
  pinMode(LED3,OUTPUT);  
  pinMode(LED4,OUTPUT);
  pinMode(LED5,OUTPUT);
  pinMode(LED6,OUTPUT);  
  pinMode(LED7,OUTPUT);
  pinMode(LED8,OUTPUT);  
  pinMode(LED9,OUTPUT); 
   pinMode(LED10,OUTPUT);  
  Serial.begin(9600);
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  
  Serial.print("connecting");
  
  while (WiFi.status() != WL_CONNECTED) {
  
  Serial.print(".");
  
  delay(500);
  dht.begin();
  
  }
  
  Serial.println();
  
  Serial.print("connected: ");
  
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  
  Firebase.setInt("LED_STATUS",0);
  Firebase.setInt("LED_STATUS3",0);
  Firebase.setInt("LED_STATUS4",0);
  Firebase.setInt("LED_STATUS5",0);
  Firebase.setInt("LED_STATUS6",0);
  Firebase.setInt("LED_STATUS7",0);
  Firebase.setInt("LED_STATUS8",0);
  Firebase.setInt("LED_STATUS9",0);
  Firebase.setInt("LED_STATUS10",0);

}

void loop() {


  if(Firebase.getInt("LED_STATUS") == 1)  
  {  
    digitalWrite(LED1,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED1,LOW);   
  }
  if(Firebase.getInt("LED_STATUS3") == 1)  
  {  
    digitalWrite(LED3,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED3,LOW);   
  } if(Firebase.getInt("LED_STATUS4") == 1)  
  {  
    digitalWrite(LED4,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED4,LOW);   
  }

   if(Firebase.getInt("LED_STATUS5") == 1)  
  {  
    digitalWrite(LED5,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED5,LOW);   
  }

   if(Firebase.getInt("LED_STATUS6") == 1)  
  {  
    digitalWrite(LED6,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED6,LOW);   
  }
   if(Firebase.getInt("LED_STATUS7") == 1)  
  {  
    digitalWrite(LED7,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED7,LOW);   
  }
   if(Firebase.getInt("LED_STATUS8") == 1)  
  {  
    digitalWrite(LED8,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED8,LOW);   
  }
   if(Firebase.getInt("LED_STATUS9") == 1)  
  {  
    digitalWrite(LED9,HIGH);  
  }  
  else  
  {  
   digitalWrite(LED9,LOW);   
  }
   if(Firebase.getInt("LED_STATUS10") == 1)  
  {  
    digitalWrite(LED10,HIGH);     
  }  
  else  
  {  
   digitalWrite(LED10,LOW);   
  }
  if (Firebase.failed()) { // Check for errors 
  
     Serial.print("setting /number failed:");
  
     Serial.println(Firebase.error());
  
  return;
  
  }
   Firebase.setFloat("led", 0);
   // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float h = dht.readHumidity();
  // Read temperature as Celsius (the default)
  float t = dht.readTemperature();
  // Check if any reads failed and exit early (to try again).
  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
   Firebase.setFloat("Humidity", h);
   Firebase.setFloat("Temperature", t);
  
  
  delay(1000);

}
