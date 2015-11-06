# NASA Astronomy Pick of the Day

This is a small project that uses the NASA APOD API to gather astronomy pictures and explanations. The API is
documented [here] (https://api.nasa.gov/api.html#apod).

The application on launch immediately tries to fetch the last seven days of astronomy picks. On scrolling to the 
bottom of the list, it will then attempt to fetch the next seven days. You can view the explanation and share some 
social text containing the URL to the astronomy pick. 

The application follows the model-view-presenter architecture pattern, a custom application class and factories are used for 
dependency injection throughout the application. Background processing occurs through using AsyncTasks, which is hidden 
behind very light weight Task API. This is all in the attempt to keep everything as clean and simple as possible, whist
adhering to SOLID principles.

There are some sample unit tests for the Archive presenter, however more unit/functional tests are required so they
should not be considered exhaustive.
