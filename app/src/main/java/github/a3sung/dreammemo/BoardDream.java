package github.a3sung.dreammemo;

public class BoardDream {
    String BoardID;



    String Title;
    String UserID;
    String DreamContent;
    String CommentContent;
    String Time;
    public BoardDream(String boardID ,String userID, String title, String dreamContent, String commentContent, String time) {
        BoardID = boardID;
        Title = title;
        UserID = userID;
        DreamContent = dreamContent;
        CommentContent = commentContent;
        Time = time;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public String getDreamContent() {
        return DreamContent;
    }

    public void setDreamContent(String dreamContent) {
        DreamContent = dreamContent;
    }

    public String getCommentContent() {
        return CommentContent;
    }

    public void setCommentContent(String commentContent) {
        CommentContent = commentContent;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}