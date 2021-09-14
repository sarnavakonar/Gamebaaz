## Gamebaaz 🎮
![GitHub license](https://img.shields.io/apm/l/link) [![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/) [![GitHub stars](https://img.shields.io/github/stars/sarnavakonar/Gamebaaz?style=social)](https://github.com/sarnavakonar/Gamebaaz/stargazers)


A *full-stack application* showing the power 💪 of **KOTLIN**. Entire android app + backend Apis written in **Kotlin** :fire:

Android             |  Backend
:-------------------------:|:-------------------------:
<img src="https://developer.android.google.cn/codelabs/jetpack-compose-animation/img/ea1442f28b3c3b39.png?hl=pt-br" width="100" height="75">       Jetpack Compose |  <img src="https://avatars.githubusercontent.com/u/28214161?s=200&v=4" width="75" height="75"> Ktor framework
<img src="https://i.pcmag.com/imagery/reviews/06fdvkyOgcsc4kzcSqhLmxB-12.1601663115.fit_scale.size_760x427.png" width="100" height="75"> MVVM,Hilt,Coroutines...|  <img src="https://pbs.twimg.com/profile_images/1255113654049128448/J5Yt92WW.png" width="100" height="100"> MySQL DB
<img src="https://theoriginalprintingcompany.com/wp-content/uploads/2016/08/Z_Blog-entry-Pictures_03.png" width="200" height="120"> | <img src="https://uploads.sitepoint.com/wp-content/uploads/2016/04/1461122387heroku-logo.jpg" width="100" height="100"> Deploy to Heroku 

## The Gamebaaz app itself 🪄

<img src="art/Gamebaaz_app.gif" width="300" height="600"/>

## 💡 About the Project

*This project has 2 components as frontend(mobile) and backend. Details mentioned below -*

## 📱 [Gamebaaz Android App](/android)

- App UI built 100% with [**Jetpack Compose**](https://developer.android.com/jetpack/compose)
- *MVVM architecture* with *Livedata, Coroutines, Retrofit, [Accompanist for compose](https://github.com/google/accompanist)*
- *Navigation Component* for fragment navigation
- *Hilt* for DI
- Bottom navigation view with multiple backstacks 
- Light/Dark mode support (comes automatically with compose 😍)

## 💻 [Gamebaaz Server Side](/server-side)

- *REST APIs* are written in **Kotlin** using [**KTOR**](https://ktor.io/) framework
- Basic AUTH is used for authentication
- MySQL is used as the database
- Apis are deplyed in the cloud via [**Heroku**](https://www.heroku.com/) 

## Contributing :heart_eyes:
Pull requests and bug 🐛 fixes are more than welcome 🙏 

## Finally 😌
If you found this to be helpful, dont forget to give it a ⭐️  

## License

```
MIT License

Copyright (c) 2021 Sarnava Konar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
