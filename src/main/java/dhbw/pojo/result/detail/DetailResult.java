package dhbw.pojo.result.detail;



public class DetailResult {
    private String title;
    private String info;

    public DetailResult(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public DetailResult() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

