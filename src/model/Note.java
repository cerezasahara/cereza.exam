package model;

public class Note {
    private int id;
    private int ownerId;
    private String content;
    private String updatedAt;

    public Note(int id, int ownerId, String content, String updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public int getOwnerId() { return ownerId; }
    public String getContent() { return content; }
    public String getUpdatedAt() { return updatedAt; }
}
