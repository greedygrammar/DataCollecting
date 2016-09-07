DataCollecting

The purpose of this programme is to demonstrate how to collect time, acceleration and gyroscope from the touchscreen based on Android. User has to register first with his username, password, sex, and age. After that, user can choose to get into the train mode or the test mode to store his biometric pattern.

Programme Design

The most important class in this programme is the DataCollecting class extends Activity and implements SensorEventListener.

the DataCollecting class initializes the SensorManager Objects with the appropriate OnResume, OnPause and OnSensorChanged method. Activity also has the contenview with buttons witch contains numbers from 0 to 9.

When users click the button, we record the corresponding number with the user's clicking time in millisecond, acceleration and gyroscope.