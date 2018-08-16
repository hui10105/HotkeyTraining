package rhzhou.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
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

    private int keyNum;

    private int idNow;

    private JSONArray hotkeys;

    private JSONObject hotkey;

    private String usingKey = "";

    private int pressingKeyCount = 0;

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
        pressedKeys.setText("code:" + usingKey);
    }

    @FXML
    protected void handleKeyReleasedAction(KeyEvent event) {
        pressingKeyCount--;
        //System.out.println("pressingKeyCount:"+ pressingKeyCount);
        //System.out.println("usingKey:"+ usingKey);
        //System.out.println("hotkey.get(\"hotkey\"):"+ hotkey.get("hotkey"));
        if(pressingKeyCount == 0){
            if (usingKey.equalsIgnoreCase((hotkey.get("hotkey")).toString())) {
                result.setText("正确");
            } else {
                result.setText("错误");
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

    @FXML protected void handleNextButtonAction(ActionEvent event) {
        nextHotkey();
    }

    private void nextHotkey(){
        idNow = generateRandomId();
        hotkeyID.setText("快捷键ID：" + (idNow + 1));
        hotkey = (JSONObject) hotkeys.get(idNow);
        name.setText("名字：" + hotkey.get("name"));
        descrip.setText("描述：" + hotkey.get("description"));
    }

    public void init() {
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
            ClassPathResource resource = new ClassPathResource("json/intelliJ.json");
            File filePath = resource.getFile();
            JSONArray btnArray = null;

            //读取文件
            String input = FileUtils.readFileToString(filePath, "UTF-8");

            hotkeys = new JSONArray(input);
            keyNum = hotkeys.length();
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
}
