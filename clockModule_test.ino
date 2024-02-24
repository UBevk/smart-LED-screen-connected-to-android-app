#include <Adafruit_NeoPixel.h>

#ifdef __AVR__
 #include <avr/power.h> // Required for 16 MHz Adafruit Trinket
#endif

// Declare our NeoPixel strip object:
#define LED_PIN 14
#define LED_COUNT 256
Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);


#include <ThreeWire.h>
#include <RtcDS1302.h>
               //   ,scl pin,
ThreeWire myWire(13,22,16); // IO, SCLK, CE
RtcDS1302<ThreeWire> Rtc(myWire);


//////deklaracija/inicializacija ure//////////////////////
int stevila[10][5][3] = {
  
    // nič
    {
      {0, 1, 2},
      {16, 3, 18},
      {32, 3, 34},
      {48, 3, 50},
      {64, 65, 66}
    },
    
    // ena
    {
      {3, 3, 2},
      {3, 17, 18},
      {32, 3, 34},
      {3, 3, 50},
      {3, 3, 66}
    },

    // dva
    {
      {0, 1, 2},
      {3, 3, 18},
      {32,33, 34},
      {48, 3, 3},
      {64, 65, 66}
    },

    // tri
    {
      {0,1, 2},
      {3, 3, 18},
      {32,33, 34},
      {3, 3, 50},
      {64, 65, 66}
    },

    // štiri
    {
      {0, 3, 2},
      {16, 3, 18},
      {32,33, 34},
      {3, 3, 50},
      {3, 3, 66}
    },

    // pet
    {
      {0,1, 2},
      {16, 3, 3},
      {32,33, 34},
      {3, 3, 50},
      {64, 65, 66}
    },

    // šest
    {
      {0, 1, 2},
      {16, 3, 3},
      {32,33, 34},
      {48, 3, 50},
      {64, 65, 66}
    },

    // sedem
    {
      {0,1, 2},
      {3, 3, 18},
      {3,3, 34},
      {3, 3, 50},
      {3, 3, 66}
    },

    // osem
    {
      {0,1, 2},
      {16, 3, 18},
      {32,33, 34},
      {48, 3, 50},
      {64, 65, 66}
    },

    // devet
    {
      {0,1, 2},
      {16, 3, 18},
      {32,33, 34},
      {3, 3, 50},
      {64, 65, 66}
    },
};


int st;
int piksl;
int startPosX;
///////////konec deklaracije/inicializacije ure /////////////////

void setup () 
{
  #if defined(__AVR_ATtiny85__) && (F_CPU == 16000000)
    clock_prescale_set(clock_div_1);
  #endif

  //////// strip ////////
  strip.begin();           // inicializacija neopixel objecta
  strip.show();            // izklop vseh pikslov
  strip.setBrightness(100); // svetlost (max = 255)
  //////// end-strip ////////
  
  Serial.begin(9600);

  Serial.print("compiled: ");
  Serial.print(__DATE__);
  Serial.println(__TIME__);

    Rtc.Begin();

    RtcDateTime compiled = RtcDateTime(__DATE__, __TIME__);
    printDateTime(compiled);
    Serial.println();

    if (!Rtc.IsDateTimeValid()) 
    {
        // Common Causes:
        //    1) first time you ran and the device wasn't running yet
        //    2) the battery on the device is low or even missing

        Serial.println("RTC lost confidence in the DateTime!");
        Rtc.SetDateTime(compiled);
    }

    if (Rtc.GetIsWriteProtected())
    {
        Serial.println("RTC was write protected, enabling writing now");
        Rtc.SetIsWriteProtected(false);
    }

    if (!Rtc.GetIsRunning())
    {
        Serial.println("RTC was not actively running, starting now");
        Rtc.SetIsRunning(true);
    }

    RtcDateTime now = Rtc.GetDateTime();
    if (now < compiled) 
    {
        Serial.println("RTC is older than compile time!  (Updating DateTime)");
        Rtc.SetDateTime(compiled);
    }
    else if (now > compiled) 
    {
        Serial.println("RTC is newer than compile time. (this is expected)");
    }
    else if (now == compiled) 
    {
        Serial.println("RTC is the same as compile time! (not expected but all is fine)");
    }  
}

int prvaStevka;
int drugaStevka;
int tretjaStevka;
int cetrtaStevka;

bool sekunda = 0;

uint32_t barva = strip.Color(0, 0, 255);

void ura(int ure, int minute){
  
   // pobriši vse
  strip.clear();

  // sekundne pike
  if(sekunda == 0){
      barva = strip.Color(0, 0, 255);
      strip.setPixelColor(104, barva);
      strip.setPixelColor(136, barva);
      sekunda = 1;
  }else{
      barva = strip.Color(0, 0, 0);
      strip.setPixelColor(104, barva);
      strip.setPixelColor(136, barva);
      sekunda = 0;
  } 

  prvaStevka = ure/10;
  drugaStevka = ure%10; 
  tretjaStevka = minute/10; 
  cetrtaStevka = minute%10; 

  Serial.println(prvaStevka);
  Serial.println(drugaStevka);
  Serial.println(tretjaStevka);
  Serial.println(cetrtaStevka);
  
  st = prvaStevka;
  startPosX = 80;

  barva = strip.Color(0, 255, 0);
  for(int y = 0; y < 5; y++){
      for(int x = 0; x < 3; x++){
        if(stevila[st][y][x]!=3){
         strip.setPixelColor(startPosX+stevila[st][y][x], barva);
        }
      }
    }

    st = drugaStevka;
    startPosX = 84;

    for(int y = 0; y < 5; y++){
      for(int x = 0; x < 3; x++){
        if(stevila[st][y][x]!=3){
         strip.setPixelColor(startPosX+stevila[st][y][x], barva);
        }
      }
    }

    barva = strip.Color(255, 0, 0);
    
    st = tretjaStevka;
    startPosX = 89;

    for(int y = 0; y < 5; y++){
      for(int x = 0; x < 3; x++){
        if(stevila[st][y][x]!=3){
         strip.setPixelColor(startPosX+stevila[st][y][x], barva);
        }
      }
    }

    st = cetrtaStevka;
    startPosX = 93;

    for(int y = 0; y < 5; y++){
      for(int x = 0; x < 3; x++){
        if(stevila[st][y][x]!=3){
         strip.setPixelColor(startPosX+stevila[st][y][x], barva);
        }
      }
    }

     barva = strip.Color(255, 165, 0);

     for(int i = 48; i <= 63; i++){
      strip.setPixelColor(i, barva);
    }

    for(int i = 176; i <= 191; i++){
      strip.setPixelColor(i, barva);
    }


    strip.show();
}

int x = 0;
int y = 0;

void loop () 
{
    RtcDateTime now = Rtc.GetDateTime();

    //printDateTime(now);
    Serial.println();

    if (!now.IsValid())
    {
        // Common Causes:
        //    1) the battery on the device is low or even missing and the power line was disconnected
        Serial.println("RTC lost confidence in the DateTime!");
    }

    int ure = now.Hour();
    int minute = now.Minute() - 3;

    ura(ure, minute);

    /*ura(x, y);
    x++;
    y++;

    if(y==99){
      y=0;
      x=0;
    } */
      

    delay(1000); //na 60 sekund
}

#define countof(a) (sizeof(a) / sizeof(a[0]))

void printDateTime(const RtcDateTime& dt)
{ 
    char datestring[20];

    snprintf_P(datestring, 
            countof(datestring),
            PSTR("%02u/%02u/%04u %02u:%02u:%02u"),
            dt.Month(),
            dt.Day(),
            dt.Year(),
            dt.Hour(),
            dt.Minute(),
            dt.Second() );
    Serial.print(datestring);
}
