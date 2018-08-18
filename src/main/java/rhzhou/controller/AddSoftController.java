package rhzhou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;
import rhzhou.bean.SoftJsonBean;
import rhzhou.util.CreateFileUtil;
import rhzhou.util.MyUtils;

import java.io.IOException;

public class AddSoftController {

    @FXML
    private TextField softName;

    @FXML
    private TextField version;

    @FXML
    private TextField icon;

    @FXML
    protected void handleAddSoftConfirmAction(ActionEvent event) throws IOException {
        // TODO validate the input

        String softPath = "json/softs.json";
        JSONArray softs = new JSONArray(MyUtils.readClasspathFileAsString(softPath));

        SoftJsonBean sjb = new SoftJsonBean();
        sjb.setId(softs.length() + 1);
        sjb.setName(softName.getText());

        // TODO get the Icon's path,if not inputed,use the default one.
        //sjb.setIcon("TwoIcon");

        sjb.setVersion(version.getText());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(sjb);
        softs.put(new JSONObject(json));
        //hotkeys.add(JSONObject.fromObject(sjb));

        System.out.println(softs.toString());
        //CreateFileUtil.createJsonFile(softs.toString(),
        //        "D:\\myproject\\IntelliJ\\HotkeyTraining\\src\\main\\resources\\json",
        //        "softs");
    }
}
