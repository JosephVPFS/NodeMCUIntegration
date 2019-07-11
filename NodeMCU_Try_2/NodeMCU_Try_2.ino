#include <ESP8266WiFi.h>

#define NAME "**********"
#define PASS "**********"

const char* host = "***.***.***.***";
String readStr;
WiFiClient client;

void setup() {
  Serial.begin(9600);
  Serial.println();

  /* Set Client up as station */
  WiFi.mode(WIFI_STA);

  WiFi.begin(NAME, PASS);

  /* Connect to the network */
  Serial.print("Connecting");
  while(WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println();

  Serial.print("Connected, IP address: ");
  Serial.println(WiFi.localIP());
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {
  
  if (client.connect(host, 9090))
  {
    Serial.print("Connected to: ");
    Serial.println(host);
	client.println ("ACTUATOR");
    /* Wait for data for 5 seconds at most before timing out */
    unsigned long timeout = millis();
    while(client.available() == 0)
    {
      if(millis() - timeout > 5000)
      {
        Serial.println("Timeout to server!");
        break;
      }
    }

    /* Read in the data in the stream */
    while(client.available() > 0)
    {
      readStr = client.readStringUntil('\n');
      if(readStr == "HIGH") {
        digitalWrite(LED_BUILTIN, HIGH);
      }else {
         digitalWrite(LED_BUILTIN, LOW);
      }
      Serial.println();
    }
    client.stop();
  }
  else
  {
    client.stop();
  }
  delay(5000);
}
