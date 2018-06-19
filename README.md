### Overview
The MainActivity uses the RecylerView and a CardView to display weather forecast data as a list. Selecting an item from the list begins a new activity showing the additional data fields 

### Project directory structure

```
└── com
    └── thompson
        └── darkskydemo
            ├── DarkSkyApp.java
            ├── UIHelper.java
            ├── activities
            │   ├── MainActivity.java
            │   └── WeatherDetailActivity.java
            ├── adapters
            │   └── DailyDataAdapter.java
            ├── json_models
            │   ├── DarkSkyCurrentWx.java
            │   ├── DarkSkyDailyDataBlock.java
            │   ├── DarkSkyDailyWx.java
            │   └── DarkSkyForecast.java
            └── networking
                ├── ConnectivityException.java
                └── DarkSkyClient.java
 ```


### 3rd Party libs/tools
- Retrofit2 for REST API interaction
- Gson for JSON de-serialization
- http://www.jsonschema2pojo.org/ to create POJO's easily from JSON.

### Unit Tests
Basic smoke tests for web requests to DarkSky API. See DarkSkySmokeTest.java. 

### Caveats
The DarkSky API has some undocumented inconsistencies. Numeric fields in the JSON can either be int or decimal(double) types. E.g. the field

uvVisibility can come over the wire as:

"uvVisibility": 3

OR

"uvVisibility": 8.9

This is not noted in the DarkSky docs. The issue is, when using a tool like jsonschema2pojo (mentioned above), you could create the field as an int, but when a float value comes down, your Gson parser will barf at inexplicable times because the behaviors appears to be random. Remedy: treat all non-time fields as decimal(double) types in JSON model POJO's. 

### Versions
- 0.0.1 