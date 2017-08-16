# Android_Remote_Led_Matrix

Control LED Matrix remotely from Android.  Java on Android side, Python on receiving side.  This repository contains just the Android application

### About This Project

I have an ongoing love for using the LED Matrix display for the Raspberry Pi to test out new coding concepts and skills.  As I started to take on Android projects I wanted a project that would test out the capabilities of Android canvas and TCP communication.  This Android project is used in conjunction with an earlier python project.


### To Use

The application is a big drawing screen composed of 32x32 squares which represent led in the LED Matrix.  If the TCP button on the bottom right is set to on, the application will stream coordinates to the designated port and IP address (YOU WILL NEED TO ENTER YOURS!!!!).  If you leave TCP communication off, you can treat it as a normal drawing app.

### To Do

1.  Enhance touch detection on the display.  Currently you have you move your finger slowing in order for the display to register your touch.

2.  Create the option to change port and IP address from the UI

3. Handling for phone rotation.
