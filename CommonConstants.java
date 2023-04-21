import java.awt.*;

public class CommonConstants {
//    Color Configuration
    public static final Color BG_COLOR = Color.decode("#C4C3D0");
    public static final Color X_COLOR = Color.decode("#990F4B");
    public static final Color O_COLOR = Color.decode("#EFDE75");
    public static final Color BAR_COLOR = Color.decode("#8A7362");
    public static final Color BOARD_COLOR = Color.decode("#C4C3D0");
    public static final Color TEXT_COLOR = Color.decode("#303030");

//    Size Configuration
    public static final Dimension FRAME_SIZE = new Dimension(540, 760);
    public static final Dimension BOARD_SIZE = new Dimension((int)(FRAME_SIZE.width * 0.96), (int)(FRAME_SIZE.height * 0.60));
    public static final Dimension BTN_SIZE = new Dimension(100, 100);
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width/3), (int)(FRAME_SIZE.height/6));


//    Value Configuration
    public static final String X_LBL = "X";
    public static final String O_LBL = "O";
    public static final String SCORE_LBL = "X:0 | O:0";

}
