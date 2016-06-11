package library.module.dialog;

public class ListDialogItemModel {
    public int title;
    public int summary;
    public int image;
    public Runnable runnable;

    public ListDialogItemModel(int title, int summary) {
        this(title, summary, 0);
    }

    public ListDialogItemModel(int title, int summary, int image) {
        this(title, summary, image, (Runnable)null);
    }

    public ListDialogItemModel(int title, int summary, int image, Runnable runnable) {
        this.title = title;
        this.summary = summary;
        this.image = image;
        this.runnable = runnable;
    }
}
