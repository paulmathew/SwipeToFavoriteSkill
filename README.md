# SwipeToFavoriteSkill

Android application that utilise 
1. Dagger
2. RxJava3
3. MVVM
4. Recyclerview with swipe to show menu
5. Retrofit
6. Glide

### Di package 
Which contains 
1. Networkmodule : which uses to create and provide Retrofit Components
2. ViewModleModule : which uses to bind the viewmodel class object with ViewModel and when ever there is a request for the Viewmodel this class will provide it.
3. ApplicationComponent : Which manages and distribute all the modules assosisated with this project, here the above two. This will help to do field injection to the Mainactivity used 
  and bind context to any modules.
  
### Model package
It contains all the data classes required in this project

### Retrofit package
This contains the interface class to call the each api using Network module and Retrofit. All the responses are Observed using RxJava

### Ui Package
It contains all the Fragment Class for showing coming soon screen and Skill List Screen.
It also contains the Adapter class for the SkillLIstFragment
>Swipe to show menu item for each of the Recyclerview is implemented in this Adapter class.
>It uses **OnSwipeTouchListener** class inside the Utils package.

### Utils package
It contains all the utility functions and modules requred for this project like
1. DialogUtils *for showing Alert Dialogbox when ever needed*
2. UtiltiyFile.KT this contains 
    >CircularProgressDrawable method to create Circular Progress Drawable
    >
    >loadImage() method to load images into a Imageview it uses Glide lib
    >
    >getDateTime() to convert timestamp value into Hour, Minute for the skill list
    >
    >isNetworkConnected variable to check the network connection when ever we needs like all the api calls
    >
    >FavoriteFlag enum, to handle the different stages of making a skill favorite or remove from favorite
    >
    >afterLayout, for getting the actual width of a view after loading fully
    >
    >Showmenu() and HideMenu() to show and hide swipe emnu while swiping
    >
    >setMargins() this function is to set the margin data to the stacked images in a skill list item

3.OnSwipeTouchListner
  ***This is one of the main class required for this project. It is used to handle the recycelrview item swipe (partial not full swipe). 
  It can detect swipe left and swipe right and according to the each function si triggered for swipeRight() and swipeLeft() changing the value of SWIPE_THRESHOLD inside the companion object
  will help us when to activate swipe left or right***
  
### Viewmodel package
  This contains MainViewmodel which is connected with the SkillListFragment for making 
  1. fetch skill list api
  2. add a skill to the favorite
  3. remove a favorite skill.

  The retofit repository is passed as constructor injection into the viewmodel.
  
  MainViewmodelFactory
  This class will pass the correct viewmodel object to fragment or activity for use. This is usefull when there is multiple Viewmodel Classes are .
  
  
  ### MainActiviy for deploying the app with bottom navigation items. This is calling the SkillListFragment and ComingSoonFragment when ever we click the bottom navigation items
  
  ### SwipeToFacApplication , this application class is used to initialise the ApplicationComponent Interface used with Dagger. it will create the componet in appkication scope,
  


### Dependencies used

    _For Dagger2_
    def dagger_version="2.41"
    implementation "com.google.dagger:dagger:$dagger_version"
    kapt "com.google.dagger:dagger-compiler:$dagger_version"
    
    _For Viewmodel_
    def lifecycle_version = "2.5.0-alpha03"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    
    _For Retrofit_
    def retrofit_version = "2.9.0"
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.3'
    
    _For Coroutines_
    def coroutines_version = "1.6.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    //Glide
    implementation 'com.github.bumptech.glide:glide:4.15.1'

    //RxJava
    implementation 'io.reactivex.rxjava3:rxandroid:3.0.2'
    implementation 'io.reactivex.rxjava3:rxjava:3.1.6'
    implementation 'com.jakewharton.rxbinding4:rxbinding:4.0.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'

    //RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.3.0'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.recyclerview:recyclerview-selection:1.1.0'


    //Animation
    implementation 'at.wirecube:additive_animations:1.9.3'
    
    _For circular iamge _
    implementation 'com.google.android.material:material:1.9.0'
    
    _Use case_
    <com.google.android.material.imageview.ShapeableImageView android:id="@+id/profileImg"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    android:layout_width="@dimen/_45dp"
    android:layout_height="@dimen/_45dp"
    app:strokeWidth="@dimen/_3dp"
    app:strokeColor="@color/white"
    android:background="@color/black"
    app:shapeAppearanceOverlay="@style/circleImageView"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"

    />

    
