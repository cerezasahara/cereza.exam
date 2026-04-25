package model;

public class SharedNote {
    private int noteId;
    private int sharedUserId;
    private String permission;

    public SharedNote(int noteId, int sharedUserId, String permission) {
        this.noteId = noteId;
        this.sharedUserId = sharedUserId;
        this.permission = permission;
    }

    public int getNoteId() { return noteId; }
    public int getSharedUserId() { return sharedUserId; }
    public String getPermission() { return permission; }
}