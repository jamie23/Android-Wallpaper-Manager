# Android-Wallpaper-Manager
A simple to use Android wallpaper browser and automated wallpaper changer.

## Description
Two main functionalities of this wallpaper manager.
- **Wallpaper browser**
- **Automatic wallpaper switcher**

The wallpaper browser functionality allows a user to search for a specific theme e.g. animals, the application will return pictures 
based on this theme and display them in a recycler view. The user can then view them full screen and select one as a system wallpaper.

The second functionality is for the application to automatically switch the wallpaper based on a theme the user enters every user specified
number of days.

The images are found via [Bing's API](https://datamarket.azure.com/dataset/bing/search "Bing's API").

## Requirements
- Android Device or AVD (Lollipop / 21+).
- A Bing API key, these can be obtained from the link above.

## Set up
Once you have cloned the project, simply get your API key from Bing (link above to API), enter it as the string value for API_KEY under APIKey class file.
The project can then be compiled and ran.

