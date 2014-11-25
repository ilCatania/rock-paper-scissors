Rock, paper, scissors!
===================

Battle your friends or the computer with challenging Rock, Paper, Scissors sessions

# Features

- Four different game modes out of the box, and a custom game mode with infinte customization possibilities!
- Play against your friends (all hands on the same keyboard), or against AIs of varying intelligence!
- Experience the thrill of picking a hand sign (actually a keystroke) before the timeout expires!
- Three symbols are not enough for you? Customize your games to also support Lizard and Spock, and unlimited others!

# Known issues

- The `jacoco` plugin (included in the build) is not supported by the gradle version (1.4) in the ubuntu repos. You may have to manually install an updated gradle version (I tested with 2.2 and it works)
- When running the command line executable within the build (`gradle run`) every character prompt will also echo `Building 90% > :rps-cmdline:run`. This is a known issue on gradle's side: https://issues.gradle.org/browse/GRADLE-1147
- The character poller is stuck waiting for one more character even after all listeners have been deregistered and the polling timeout expired. This has been worked around by actually asking the user for an additional keypress at the end of each hand
