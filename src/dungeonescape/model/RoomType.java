package dungeonescape.model;

/**
 * The different kinds of rooms in the dungeon.
 * An enum is used so we can't accidentally mistype a room type.
 */
public enum RoomType {
    ENTRANCE,
    NORMAL,
    MONSTER,
    TREASURE,
    TRAP,
    HEALING,
    PUZZLE,
    BOSS,
    EXIT
}