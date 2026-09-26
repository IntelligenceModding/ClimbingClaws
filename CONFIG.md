# Climbing Claws Config

Climbing Claws writes simple JSON config files in the Fabric config directory.

```text
config/climbingclaws.json
config/climbingclaws-client.json
```

`climbingclaws.json` controls gameplay behavior. `climbingclaws-client.json` controls local rendering only.

When Mod Menu is installed, these settings can also be edited from the Climbing Claws config button in the mod list.

The client settings are always local to the player. The gameplay settings are server-side settings: changing them from a client only affects a singleplayer or locally hosted integrated server. On a dedicated multiplayer server, the server's own `climbingclaws.json` is authoritative.

## Gameplay Config

| Key | Default | What it controls |
| --- | --- | --- |
| `enableClimbing` | `true` | Master traversal switch. |
| `allowMainHandUse` | `true` | Allows right-click traversal from the main hand. |
| `allowOffHandUse` | `true` | Allows right-click traversal from the off hand. |
| `enableWallClimbing` | `true` | Allows latching onto vertical block faces. |
| `enableCeilingClimbing` | `true` | Allows latching onto block undersides. |
| `enableHanging` | `true` | Allows holding position while attached and not pressing movement. |
| `enableControlledDescent` | `true` | Allows sneak-descending while attached. |
| `enableCanopyGripEffect` | `true` | Allows Canopy Grip to latch onto partial collision surfaces. |
| `sideClimbSpeed` | `0.065` | Base upward speed while climbing a wall. |
| `ceilingClimbSpeed` | `0.03` | Upward/hold speed while moving against a ceiling. |
| `ceilingHoldSpeed` | `0.01` | Upward/hold speed while clinging to a ceiling without movement input. |
| `efficiencySpeedBonus` | `0.0125` | Extra climb speed per Efficiency level. |
| `horizontalVelocityLimit` | `0.15` | Horizontal velocity retained while attached. |
| `fallSpeedLimitWhileAttached` | `0.15` | Maximum downward velocity before claw movement is applied. |
| `enableWallSpring` | `true` | Allows Wall Spring activation. |
| `allowWallSpringWhileSneaking` | `false` | Allows Wall Spring while sneaking. |
| `wallSpringLevelOneBoost` | `0.75` | Upward velocity added by Wall Spring I. |
| `wallSpringLevelTwoBoost` | `1.05` | Upward velocity added by Wall Spring II. |
| `wallSpringCooldownTicks` | `200` | Wall Spring cooldown in ticks. |
| `enableDurabilityDamage` | `true` | Enables traversal durability damage. |
| `climbingDamageAmount` | `1` | Durability damage during normal climbing or clinging intervals. |
| `activeClimbDamageIntervalTicks` | `10` | Damage interval while actively climbing. |
| `clingDamageIntervalTicks` | `20` | Damage interval while attached but not actively climbing. |
| `wallSpringDamageAmount` | `1` | Durability damage applied when Wall Spring activates. |

## Client Config

| Key | Default | What it controls |
| --- | --- | --- |
| `showWallSpringCooldownOverlay` | `true` | Shows the Wall Spring cooldown overlay on Climbing Claws item stacks. |
