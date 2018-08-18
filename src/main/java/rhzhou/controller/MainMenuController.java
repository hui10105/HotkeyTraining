package rhzhou.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import rhzhou.bean.SoftJsonBean;
import rhzhou.util.CreateFileUtil;
import rhzhou.util.MyUtils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenuController {
    @FXML
    private Text pressedKeys;
    @FXML
    private Text result;

    @FXML
    private Text hotkeyID;

    @FXML
    private Text descrip;

    @FXML
    private Text name;

    @FXML
    private ImageView resultIcon;

    @FXML
    private BorderPane mainPane;

    private int keyNum;

    private int idNow;

    private JSONArray hotkeys;

    private JSONObject hotkey;

    private String usingKey = "";

    private int pressingKeyCount = 0;

    private Parent addSoftRoot;

    @FXML
    protected void handleKeyPressedAction(KeyEvent event) {
        if (usingKey.isEmpty()) {
            pressingKeyCount++;
            usingKey = event.getCode().toString();
        } else {
            String org = event.getCode().toString();
            String reg = "^" + org + "\\W+|\\W+" + org + "$|\\W+" + org + "\\W+|^" + org + "$";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(usingKey);
            if (!m.find()) {
                pressingKeyCount++;
                usingKey = usingKey + " + " + event.getCode();
            }
            //if (!usingKey.matches("(^" + event.getCode() + "\\W)|")) {
            //    usingKey = usingKey + " + " + event.getCode();
            //}
        }
        pressedKeys.setText("Pressing: " + usingKey);
    }

    @FXML
    protected void handleKeyReleasedAction(KeyEvent event) {
        //TODO 当只松开部分按键时的处理

        System.out.println("pressingKeyCount: " + pressingKeyCount);
        if(pressingKeyCount <= 0){
            return;
        }
        pressingKeyCount--;
        //System.out.println("pressingKeyCount:"+ pressingKeyCount);
        //System.out.println("usingKey:"+ usingKey);
        //System.out.println("hotkey.get(\"hotkey\"):"+ hotkey.get("hotkey"));
        if(pressingKeyCount == 0){
            resultIcon.setVisible(true);
            if (usingKey.equalsIgnoreCase((hotkey.get("hotkey")).toString())) {
                result.setText("正确");
                setResultIcon(true);
                new Thread(()->{
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    resultIcon.setVisible(false);
                    nextHotkey();
                }).start();
            } else {
                result.setText("错误");
                setResultIcon(false);
            }
            usingKey = "";

        }
        //下面这个改进之后再做，现象：按下多个按键后，松开一部分按键不会动态地正确显示仍在按的键。
        //else {
        //    String org = event.getCode().toString();
        //    String reg = "^" + org + "\\W+|\\W+" + org + "$|\\W+" + org + "\\W+|^" + org + "$";
        //    usingKey = usingKey.replaceAll(reg, "");
        //}
    }

    @FXML
    protected void handleNextButtonAction(ActionEvent event) {
        resultIcon.setVisible(false);
        nextHotkey();
    }

    @FXML
    protected void handleSelectSoftAction(ActionEvent event) {

    }

    //@FXML
    protected void handleAddSoftAction(ActionEvent event) throws IOException {
        if(addSoftRoot == null){
            addSoftRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AddSoft.fxml"));
        }
        Scene scene = new Scene(addSoftRoot, 500, 300);
        Stage addSoftStage = new Stage();
        addSoftStage.setTitle("添加软件");
        addSoftStage.setScene(scene);
        addSoftStage.show();

    }

    /*
    当按下Alt+Tab键切换出去时，会出现显示错误，这个bug我还没想到合适的解决办法。
    不过对于使用影响不大。
    private void clearUsingKey(){
        usingKey = "";
        pressingKeyCount = 0;
    }*/

    private void nextHotkey(){
        idNow = generateRandomId();
        hotkeyID.setText("快捷键ID：" + (idNow + 1));
        hotkey = (JSONObject) hotkeys.get(idNow);
        name.setText("名字：" + hotkey.get("name"));
        descrip.setText("描述：" + hotkey.get("description"));
        pressedKeys.setText("Pressing: ");
    }

    public void init() {
        // 调试用，有了resultIcon的图片后已不再使用
        result.setVisible(false);
        readInformation();
        nextHotkey();
    }

    private int generateRandomId() {
        double d = Math.random();
        //System.out.println("random number:" + d);
        return (int) (d * keyNum);
    }


    private void readInformation() {
        try {
            String filePath = "json/intelliJ.json";
            hotkeys = new JSONArray(MyUtils.readClasspathFileAsString(filePath));
            keyNum = hotkeys.length();
            handleAddSoftAction(new ActionEvent());
            //System.out.println("arraySize:" + jsonArray.length());

            //将读取的数据转换为JSONObject
            //JSONObject jsonObject =  (JSONObject)jsonArray.get(2);
            //System.out.println(jsonObject.get("id"));
            //System.out.println(jsonObject.get("name"));
            //System.out.println(jsonObject.get("description"));
            //System.out.println(jsonObject.get("hotkey"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像裁剪。
     */
    private void setResultIcon(boolean correct) {
        int x, y, width, height;
        y = 475;
        height = 305;
        if(correct){
            x = 590;
            width = 300;
        } else {
            x = 210;
            width = 295;
        }

        Rectangle2D rec = new Rectangle2D(x, y, width, height);
        Image srcImage = new Image(getClass().getClassLoader().getResource("pic/resultIcon.png").toString());
        resultIcon.setImage(srcImage);
        resultIcon.setViewport(rec);
        //return srcImage.getSubimage(x, y, width, height);

    }
}
