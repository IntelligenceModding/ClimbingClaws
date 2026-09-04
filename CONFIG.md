# Climbing Claws Config

Climbing Claws has a server config for gameplay behavior and a client config for local rendering.

The server config is registered as a NeoForge server config so multiplayer clients receive the server's gameplay values when they connect.

```text
serverconfig/climbingclaws-server.toml
```

On a dedicated server this is under the world's `serverconfig` directory, normally:

```text
world/serverconfig/climbingclaws-server.toml
```

For an integrated singleplayer world, it is under that world's save folder:

```text
saves/<world_name>/serverconfig/climbingclaws-server.toml
```

Older `config/climbingclaws-common.toml` files are no longer read. Copy any customized values into the world's `climbingclaws-server.toml`.

The client config is normally written as:

```text
config/climbingclaws-client.toml
```

## Server Config Sections

| Section | Keys | What it controls |
| --- | --- | --- |
| `general` | `enableClimbing`, `allowMainHandUse`, `allowOffHandUse`, `enableWallClimbing`, `enableCeilingClimbing`, `enableHanging`, `enableControlledDescent`, `enableCanopyGripEffect` | Master traversal behavior, allowed hands, wall/ceiling movement, hanging, controlled descent, and Canopy Grip behavior. |
| `movement` | `sideClimbSpeed`, `ceilingClimbSpeed`, `ceilingHoldSpeed`, `efficiencySpeedBonus`, `horizontalVelocityLimit`, `fallSpeedLimitWhileAttached` | Base movement speeds, Efficiency scaling, horizontal movement limiting, and maximum downward speed while attached. |
| `wallSpring` | `enableWallSpring`, `allowWhileSneaking`, `levelOneBoost`, `levelTwoBoost`, `cooldownTicks` | Wall Spring availability, sneaking behavior, launch strength, and cooldown length. |
| `durability` | `enableDamage`, `climbingDamageAmount`, `activeClimbDamageIntervalTicks`, `clingDamageIntervalTicks`, `wallSpringDamageAmount` | Durability damage from climbing, clinging, and Wall Spring. |

## Important Defaults

| Key | Default |
| --- | --- |
| `general.enableClimbing` | `true` |
| `general.allowMainHandUse` | `true` |
| `general.allowOffHandUse` | `true` |
| `general.enableWallClimbing` | `true` |
| `general.enableCeilingClimbing` | `true` |
| `general.enableHanging` | `true` |
| `general.enableControlledDescent` | `true` |
| `general.enableCanopyGripEffect` | `true` |
| `movement.sideClimbSpeed` | `0.065` |
| `movement.ceilingClimbSpeed` | `0.03` |
| `movement.ceilingHoldSpeed` | `0.01` |
| `movement.efficiencySpeedBonus` | `0.0125` |
| `movement.horizontalVelocityLimit` | `0.15` |
| `movement.fallSpeedLimitWhileAttached` | `0.15` |
| `wallSpring.enableWallSpring` | `true` |
| `wallSpring.allowWhileSneaking` | `false` |
| `wallSpring.levelOneBoost` | `0.75` |
| `wallSpring.levelTwoBoost` | `1.05` |
| `wallSpring.cooldownTicks` | `200` |
| `durability.enableDamage` | `true` |
| `durability.climbingDamageAmount` | `1` |
| `durability.activeClimbDamageIntervalTicks` | `10` |
| `durability.clingDamageIntervalTicks` | `20` |
| `durability.wallSpringDamageAmount` | `1` |

## Traversal Interactions

`general.enableClimbing = false` disables Climbing Claws traversal. The item can still exist and behave as a light weapon, but using it will not start the climbing stance.

`general.allowMainHandUse` and `general.allowOffHandUse` decide whether holding right-click with the claws in that hand can activate traversal.

Curios hands-slot traversal still requires Curios to be installed and an equipped Climbing Claws item.

`general.enableWallClimbing = false` disables latching onto vertical block faces.

`general.enableCeilingClimbing = false` disables latching onto block undersides.

`general.enableHanging = false` prevents holding position while attached and not pressing movement.

`general.enableControlledDescent = false` prevents sneak-descending while attached.

`general.enableCanopyGripEffect = false` disables Canopy Grip's partial-surface behavior even if the claws have the enchantment.

## Movement Tuning

`movement.sideClimbSpeed` is the base upward velocity while climbing a vertical wall.

`movement.ceilingClimbSpeed` is the upward/hold velocity while moving against a ceiling.

`movement.ceilingHoldSpeed` is the upward/hold velocity while clinging to a ceiling without movement input.

`movement.efficiencySpeedBonus` is added once per Efficiency level. For example, Efficiency III adds three times this value to the relevant base climb speed.

`movement.horizontalVelocityLimit` clamps retained horizontal velocity while attached to a surface.

`movement.fallSpeedLimitWhileAttached` clamps retained downward velocity before claw movement is applied.

## Wall Spring

`wallSpring.enableWallSpring = false` disables Wall Spring activation and hides the client cooldown overlay.

Wall Spring requires the Wall Spring enchantment and a valid wall or ceiling contact.

`wallSpring.allowWhileSneaking = false` blocks Wall Spring while the player is sneaking. Set it to `true` if you want Wall Spring to work during controlled descent.

`wallSpring.levelOneBoost` and `wallSpring.levelTwoBoost` control the upward velocity added by Wall Spring I and Wall Spring II.

`wallSpring.cooldownTicks` controls the server-side cooldown and the client item overlay. `20` ticks is one second. A value of `200` is ten seconds.

## Durability

`durability.enableDamage = false` disables traversal durability damage entirely.

`durability.climbingDamageAmount` is applied at the active or cling interval while attached.

`durability.activeClimbDamageIntervalTicks` is used while actively climbing or descending.

`durability.clingDamageIntervalTicks` is used while attached but not actively climbing.

`durability.wallSpringDamageAmount` is applied immediately when Wall Spring activates.

Unbreaking and Mending still use vanilla item durability behavior.

## Client Config

| Key | Default | What it controls |
| --- | --- | --- |
| `showWallSpringCooldownOverlay` | `true` | Shows the Wall Spring cooldown overlay on Climbing Claws item stacks. |
