public class ChatMessage {

    private Integer id;
    private String message;

    public ChatMessage(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public ChatMessage() {
        this.id = 0;
        this.message = " ";
    }


    public Integer getId() {
        return id;
    }

    public void seId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
