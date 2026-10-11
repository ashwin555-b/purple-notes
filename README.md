# Purple Notes — Android app + home-screen widget

A small native Android notes app with a purple/black theme. Notes are saved automatically in private app preferences, and the home-screen widget displays the latest note.

## Important Android widget limitation
Standard Android home-screen widgets use `RemoteViews`, which does not support a normal editable text field for typing directly inside the widget. This project therefore shows the note on the widget and opens the editor when you tap the note or **EDIT**. After editing, the widget refreshes automatically. That is the closest reliable standard-widget behavior without using a floating overlay or a custom launcher.

## Build in Android Studio
1. Install Android Studio (current stable version) and its Android SDK.
2. Unzip `PurpleNotes.zip`.
3. In Android Studio, choose **Open** and select the unzipped `PurpleNotes` folder (the folder containing `settings.gradle.kts`).
4. Allow Gradle sync. If prompted, install Android SDK Platform 35 and a compatible Android Gradle Plugin/JDK (Android Studio's bundled JDK is fine).
5. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
6. When it finishes, click **Locate**. The APK is normally at `app/build/outputs/apk/debug/app-debug.apk`.
7. Transfer the APK to your Android phone and open it. If prompted, allow installation from that source, then install.

## Add the widget
1. Open Purple Notes once and type a note.
2. Return to your phone's home screen.
3. Long-press an empty area, choose **Widgets**, find **Purple Notes**, and drag the widget onto the home screen.
4. Tap the widget's note area or **EDIT** to update the note. Changes save automatically.

## Project layout
- `app/src/main/java/com/example/purplenotes/MainActivity.kt` — note editor and auto-save.
- `app/src/main/java/com/example/purplenotes/NotesWidgetProvider.kt` — widget refresh and tap actions.
- `app/src/main/res/layout/notes_widget.xml` — widget UI.
- `app/src/main/res/xml/notes_widget_info.xml` — widget sizing/configuration.

## Notes
- This starter version stores one note locally on the phone.
- No account or internet permission is needed.
- If Gradle reports an SDK/license issue, open **Tools > SDK Manager** and install the SDK platform/build tools requested by Android Studio.
