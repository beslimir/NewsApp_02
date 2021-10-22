This app is built on a MVVM architecture (with domain layer).
The domain layer is very useful in bigger projects, but for demonstration purposes, I used it in my NewsApp.
The NewsApp_02 is a bit different than the first one. I used also dagger hilt and the domain layer.

It uses:

- Retrofit API call (News API) to get news from the internet
- Room database for saved articles
- Bottom navigation menu
- nav_graph
- Live data
- Coroutines
- Dagger Hilt
- Use cases

The goal of this application is to get news from the internet and to save them localy to the Room database. The purpose is to connect all features of the app together in the recommended way (clean architecture).
