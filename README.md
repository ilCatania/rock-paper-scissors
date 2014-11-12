rock-paper-scissors
===================

Battle your friends or the computer with challenging Rock, Paper, Scissors games!

# features

- four different game modes, and infinte customization possibilities!
- play against friends (all hands on the same keyboard), or against AIs of varying intelligence
- experience the thrill of picking a hand sign (actually a keystroke) before the timeout expires!

# known issues

- the `jacoco` plugin (included in the build) is not supported by the gradle version in the ubuntu repos. You will have to update it manually
- when running the command line executable within the build (`gradle run`) every character prompt will also echo `BuildinBuilding x% > :rps-cmdline:run`. This is a known issue on gradle's side: https://issues.gradle.org/browse/GRADLE-1147
- the character poller is stuck waiting for one more character even after all listeners have been deregistered and the polling timeout expired. This has been worked around by actually asking the user for an additional keypress at the end of each hand
