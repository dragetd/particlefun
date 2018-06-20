# Particle Fun

Simple demo applicaton for JavaFX and drawing a particle-system.

Currently the gameloop is a simple variable-tick gameloop with a limit of 100ms max simulation step. The example-particlesystem spawns
explosion-like particles that can rest ontop of each other.

Plans for future improvement:
  - fixed-timestep simulation ticks with varible framerate
  - snow-like dynamic interaction
  - game stage drawing
  - more fun particle effects

## Building
This project uses Gradle version 4 or greater to build. If a local gradle is not installed, use the included wrapper `./gradlew` (for Linux) or `gradlew.bat` (for Windows)


Build all classes:
`gradle build`

Create a distributable jar packaged in a zip
`gradle distZip`

Output will be placed inside the `build` directory.
