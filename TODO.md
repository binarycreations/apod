# APOD - Todo List and project house keeping

## Product Backlog

### Version 1.0
- As a developer I want crash reporting analytics so that I can fix problems easily
- As a user I want to swipe to refresh the list of APODs so that I can and view the latest picks.
- As a user I want to be able to view APODs whilst offline
- As a user I want to view the APOD picture fullscreen
- As a user I want to play a video associated with an APOD
- As a user I want to favourite an APOD so that I can view it at a later date
- As a user I want to view all of my favourite APOD
- As a user I want to set an APOD picture as my wallpaper

### Future release cycle
- As a user I want to sync APODs at a time of my choosing on a regular basis so that I always have the latest picks available offline.
- As a user I want to view the entire archive till recent APODs offline, from first installation.
- As a developer I want to run static analysis checks so that I can find problems with code quickly
- As a user I want to be able to search for a pick by date
- As a user I want to be able to search for concepts related to picks
- As a user I want to search the for words relating to picks so that I can find a previous pick from a list of relevant results
- As a user I want a live wallpaper that scrolls through the archive of APODs as my wallpaper
- As a user I want all APODs to be downloaded to a folder I can view on my phone
- As a user I want to choose which storage area to download APODs too

## TODOs
- Move dependency injection to use Dagger.
- Move away from Tasks API to RxJava for completing background work.
- Use Joda-time or the three-ten backport for Android. The Calendar and Date API is messy.
- Use genymotion to start emulators prior to automated tests running
- Start automated testing with Espresso, using [mockwebserver](https://github.com/square/okhttp/tree/master/mockwebserver) to isolate.
- Investigate using Gwen with Espresso to have some form of BDD.
- Use git tags to generate the version information within the Android manifest
- Create a Jenkins server to run a CI job