## Space Invaders Game ##
-A Java Swing game where you shoot alien enemies to win before they destroy or reach your ship.

## Features
-5x8 enemy grid with three difficulty levels (speed and health vary).
-Player movement (left/right) and shooting with cooldown.
-3 lives; frequent enemy shots increase challenge.
-Scoring system: 30/20/10 points for defeating level 3/2/1 enemies.
-Win by defeating all enemies; lose if lives run out or enemies reach bottom.
-Uses ArrayList for dynamic management of enemies and bullets.

## Controls
-Left/Right Arrows: Move ship horizontally.
-Spacebar: Shoot (hold for rapid fire, tap for precision).

## Setup
-Install Java 21 and Maven.

## Files
-SpaceInvadersGame.java: Main class; manages game loop, input, updates, and rendering.
-Player.java: Defines player properties (position, lives, score) and collision box.
-Enemy.java: Defines enemy properties (position, level, health) and collision box.
-Bullet.java: Defines bullet properties (position, type) and collision box.

## Notes
-Final project for CIT-215 (Java)
-Use of <ArrayList> for Player, Enemy, and Bullet handling.
