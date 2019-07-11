#include <ESP8266WiFi.h>

#define NAME "************"
#define PASS "************"

const char* host = "***.***.***.***";
const int analog_ip = A0;
int counter = 0;
int inputVal  = 0; 

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
}

void loop() {
  counter++;
  inputVal = analogRead (analog_ip); // Analog Values 0 to 1023
  
  if (client.connect(host, 9090))
  {
    Serial.print("Connected to: ");
    Serial.println(host);
    //client.println ("analogRead");
    Serial.print("analogRead: ");
    Serial.println(inputVal);
	if(counter > 5)
	{
		inputVal = inputVal - counter;
		counter = 0;
	}
    client.println (inputVal);

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
    client.stop();
  }
  else
  {
    client.stop();
  }
  delay(5000);
}
