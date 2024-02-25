#include "esp_bt_main.h"
#include "esp_bt_device.h"
#include <Adafruit_NeoPixel.h>

#ifdef __AVR__
#include <avr/power.h> // Required for 16 MHz Adafruit Trinket
#endif
// Spremenljvika za hranjenje vhodne vrednosti
#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

// data pin
#define LED_PIN 12

// Stevilo vklopljenih ledic
#define LED_COUNT 256

// Declare our NeoPixel strip object:
Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);

BluetoothSerial SerialBT;

void printDeviceAddress() {

  const uint8_t* point = esp_bt_dev_get_address();

  for (int i = 0; i < 6; i++) {

    char str[3];

    sprintf(str, "%02X", (int)point[i]);
    Serial.print(str);

    if (i < 5) {
      Serial.print(":");
    }

  }
}

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
    {32, 33, 34},
    {48, 3, 3},
    {64, 65, 66}
  },

  // tri
  {
    {0, 1, 2},
    {3, 3, 18},
    {32, 33, 34},
    {3, 3, 50},
    {64, 65, 66}
  },

  // štiri
  {
    {0, 3, 2},
    {16, 3, 18},
    {32, 33, 34},
    {3, 3, 50},
    {3, 3, 66}
  },

  // pet
  {
    {0, 1, 2},
    {0, 3, 3},
    {32, 33, 34},
    {3, 3, 50},
    {64, 65, 66}
  },

  // šest
  {
    {0, 1, 2},
    {16, 3, 18},
    {32, 33, 34},
    {48, 3, 50},
    {64, 65, 66}
  },

  // sedem
  {
    {0, 1, 2},
    {3, 3, 18},
    {3, 3, 34},
    {3, 3, 50},
    {3, 3, 66}
  },

  // osem
  {
    {0, 1, 2},
    {16, 3, 18},
    {32, 33, 34},
    {48, 3, 50},
    {64, 65, 66}
  },

  // devet
  {
    {0, 1, 2},
    {16, 3, 18},
    {32, 33, 34},
    {3, 3, 50},
    {64, 65, 66}
  },
};


uint32_t barva;
int st;
int piksl;
int startPosX;

void setup() {

  // These lines are specifically to support the Adafruit Trinket 5V 16 MHz.
  // Any other board, you can remove this part (but no harm leaving it):
#if defined(__AVR_ATtiny85__) && (F_CPU == 16000000)
  clock_prescale_set(clock_div_1);
#endif

  strip.begin();           // inicializacija neopixel objecta
  strip.show();            // izklop vseh pikslov
  strip.setBrightness(100); // svetlost (max = 255)
  Serial.begin(9600);

  uint32_t barva;
  barva = strip.Color(0, 0, 255);

  st = 1;
  startPosX = 0;

  for (int y = 0; y < 5; y++) {
    for (int x = 0; x < 3; x++) {
      if (stevila[st][y][x] != 3) {
        strip.setPixelColor(startPosX + stevila[st][y][x], barva);
      }
    }
  }

  st = 2;
  startPosX = 4;

  for (int y = 0; y < 5; y++) {
    for (int x = 0; x < 3; x++) {
      if (stevila[st][y][x] != 3) {
        strip.setPixelColor(startPosX + stevila[st][y][x], barva);
      }
    }
  }

  st = 3;
  startPosX = 9;

  for (int y = 0; y < 5; y++) {
    for (int x = 0; x < 3; x++) {
      if (stevila[st][y][x] != 3) {
        strip.setPixelColor(startPosX + stevila[st][y][x], barva);
      }
    }
  }

  st = 4;
  startPosX = 13;

  for (int y = 0; y < 5; y++) {
    for (int x = 0; x < 3; x++) {
      if (stevila[st][y][x] != 3) {
        strip.setPixelColor(startPosX + stevila[st][y][x], barva);
      }
    }
  }

  strip.show();
}

void loop() {


}
