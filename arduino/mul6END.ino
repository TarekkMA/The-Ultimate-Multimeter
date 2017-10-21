#include "LiquidCrystal.h"



LiquidCrystal lcd(10, 9,8, 7, 6, 5);

//v2

 float input_voltage2 = 0.0;
float temp2=0.0;
float r12=98300.0;
float r22=9700.0;

//v1

 float input_voltage = 0.0;
float temp=0.0;
float r1=98300.0;
float r2=9700.0;

//capacitor

#define analogPin      A3         
#define chargePin      4         
#define dischargePin   3       
#define resistorValue  9700.0F  

#define mode      11
unsigned long startTime;
unsigned long elapsedTime;
float microFarads;                
float nanoFarads;

//resistor

const int sensorPin = A2;  // Analog input pin that senses Vout
int sensorValue = 0;       // sensorPin default value
float Vin = 5;             // Input voltage
float Vout = 0;            // Vout default value
float Rref = 10000;          // Reference resistor's value in ohms (you can give this value in kiloohms or megaohms - the resistance of the tested resistor will be given in the same units)
float R = 0;               // Tested resistors default value


void setup()
{
   Serial.begin(9600);     //  opens serial port, sets data rate to 9600 bps
   lcd.begin(16, 2);       //// set up the LCD's number of columns and rows: 
 lcd.setCursor(0, 0);
 lcd.print("***WELCOM TO***");
 lcd.setCursor(0, 1);
  lcd.print("FEE PHYSICS LAB");
 delay(2000);
 lcd.clear();
  lcd.print("DIGITAL MULTITER");
lcd.setCursor(0, 1);
  lcd.print("***PREB YEAY***");
  delay(4000);
  lcd.clear();
pinMode(mode, INPUT);  
   pinMode(chargePin, OUTPUT);     
  digitalWrite(chargePin, LOW); 
  
  

}
void loop()
{
  int Mode = digitalRead(mode);
if(Mode==LOW){
  //capacitor

 
     digitalWrite(chargePin, HIGH);  
  startTime = millis();
  while(analogRead(analogPin) < 648){       
  }

  elapsedTime= millis() - startTime;
  microFarads = ((float)elapsedTime / resistorValue) * 1000;   


 

  

  digitalWrite(chargePin, LOW);            
  pinMode(dischargePin, OUTPUT);            
  digitalWrite(dischargePin, LOW);          
  while(analogRead(analogPin) > 0){         
 }

  pinMode(dischargePin, INPUT);

if (microFarads > 1){
  lcd.clear();
      lcd.setCursor(0, 1);
     lcd.print("C=");
     lcd.print(microFarads);       
    lcd.print(" uF"); 
    
  }

  else{
    
    lcd.clear();
     lcd.setCursor(0, 0);
    lcd.print("CAPACITOR METER");
    nanoFarads = microFarads * 1000.0;      
   lcd.setCursor(0, 1);
     lcd.print("C=");
     lcd.print(nanoFarads);       
    lcd.print("nF"); 
  }  
    //serial      
 Serial.print("vdc:");
      Serial.print(0);
       Serial.print(",vac:");
      Serial.print(0);
       Serial.print(",i:");
      Serial.print(0);
       Serial.print(",c:");
      Serial.print(microFarads);
 Serial.print(",r:");
      Serial.print(0);
Serial.println();

 }
else {

//voltmeter

   float analog_value = analogRead(A0);
    temp = (analog_value * 5.0) / 1024.0; 
   input_voltage = temp / (r2/(r1+r2));
   input_voltage+=.25;
   if (input_voltage < 0.26) 
   {
     input_voltage=0.0;
   } 

   
   //ammeter
   
   float analog_value2 = analogRead(A1);
    temp2 = (analog_value2 * 5.0) / 1024.0; 
   input_voltage2 = temp2 / (r22/(r12+r22));
   input_voltage2+=.25;
   if (input_voltage2 < 0.26) 
   {
     input_voltage2=0.0;
   } 

   float amp = ((input_voltage-input_voltage2)/1);


//resistor

    sensorValue = analogRead(sensorPin);  // Read Vout on analog input pin A1(Arduino can sense from 0-1023, 1023 is 5V)
  Vout = (Vin * sensorValue) / 1023;    // Convert Vout to volts
  R = Rref * (1 / ((Vin / Vout) - 1));  // Formula to calculate tested resistor's value
 



 //lcd

  


   lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("V=");
    lcd.print(input_voltage);
    //  lcd.print(" v");


lcd.setCursor(7, 0);
   // lcd.print("V=");
   if(R>=1000)
   {
   lcd.print(" R=");
    lcd.print(R/1000);
    lcd.print("K  ");
   }
   else if(R<1000&&R>0){
   lcd.print(" R=");
    lcd.print(R);
   }  
else if(R==0)
 lcd.print(" R=      ");
 
    lcd.setCursor(0, 1);

    if(amp>=1000)
   {
   lcd.print("I=");
    lcd.print(amp);
    lcd.print("A ");
   }
   else if(amp<1000 && amp>0){
   lcd.print("I=");
    lcd.print(amp*1000);
    lcd.print("mA");
   }  
else if(amp==0)
 lcd.print("I=0,00");

 
 
    
    //lcd.setCursor(8, 1);
    
   // lcd.print("v2=");
     // lcd.print(input_voltage2);

//serial

    Serial.print("vdc:");
      Serial.print(input_voltage);
       Serial.print(",vac:");
      Serial.print(input_voltage);
       Serial.print(",i:");
      Serial.print(amp);
       Serial.print(",c:");
      Serial.print(microFarads);
 Serial.print(",r:");
      Serial.print(R);
Serial.println();
   
    
    
 delay(1000);
}
   
   }

