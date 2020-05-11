# Bankable
<img src="https://raw.githubusercontent.com/aliumujib/MnSonsApp/master/app_icon.png" width="250" />

### Introduction
Android banking app uses Hover's USSD sdk to power fully offline banking and transaction analytics. The app aggregates banking operations of different app into one
concise good looking application.

### Goals
- Significantly improve the UX of USSD in terms of accessibility and aesthetics.
- Achieve very lean APK size, currently at 6.3 MB with 2 bank integrations, can't go up much more than that even if we added more banks because we'd
be adding only new contracts for the bank, and an SVG logo image for each bank. Size can be further optimized with code splitting and obfuscation.
- Make intelligent use of Hover's SDK (Cut some corners though, for speed 🤣)
- Aggregate USSD banking services in one place so the user doesn't have to remember them all.

### Used libraries
**Hover SDK** - USSD calls</br>
**Dagger2** - Dagger2 was used for dependency injection.</br>
**Kotlin Flow** - Kotlin Flow was used for threading and data stream management.</br>
**AndroidKtx** - For cool extensions to Android classes.</br>
**Architecture Components** - For Lifecycle management etc.</br>


### Possible Improvements
We had a lot of fun building this. There are some improvements we intend to make.

- Write tests. </br>
- Further explore the `Activity Result API` as a means of further abstracting Hover SDK logic from activities and fragments for a cleaner dev experience</br>
- Improve success/error parsing logic with parsers</br>
- Add more banks</br>


### Team (MnSons)
- [Abdul-Mujeeb Aliu](https://github.com/aliumujib).
- [Quadri Anifowose](https://github.com/Quadriyanney).
- [Olusesan Peter](https://sesan.design).

### Build Instructions
- Clone repository.</br>
- Run with Android Studio 4.1 canary 8 and above. </br>
