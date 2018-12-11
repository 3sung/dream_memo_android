package github.a3sung.dreammemo;

public class Comment {
    String ID;
    String BoardID;
    String UserID;
    String Content;

    public Comment(String ID, String boardID, String userID, String content) {
        this.ID = ID;
        BoardID = boardID;
        UserID = userID;
        Content = content;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBoardID() {
        return BoardID;
    }

    public void setBoardID(String boardID) {
        BoardID = boardID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}