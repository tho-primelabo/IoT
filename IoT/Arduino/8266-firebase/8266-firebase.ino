#include <ESP8266WiFi.h>

#include <FirebaseArduino.h>

#define WIFI_SSID "MobileZone"
#define WIFI_PASSWORD "KFC@2018"
#define FIREBASE_HOST "winged-bliss-237302.firebaseio.com"
#define FIREBASE_AUTH "qXwFALB50knO5W4lXOp5jWqIkoNu7lLRufz6HTja"

#define LED 2

#include "DHT.h"
#define DHTTYPE DHT11 
#define DHTPIN 4     // what digital pin we're connected to
DHT dht(DHTPIN, DHTTYPE);

void setup() {

  pinMode(LED,OUTPUT);
  
  digitalWrite(LED,0);
  
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

}

void loop() {

  if(Firebase.getInt("LED_STATUS") == 1)
  
  {
  
  digitalWrite(LED,HIGH);
  
  }
  
  else
  
  {
  
  digitalWrite(LED,LOW);
  
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
