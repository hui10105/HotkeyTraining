package rhzhou.bean;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

public class SoftJsonBean {

    /**
     * id : 1
     * name : IntelliJ IDEA
     * version : 2018.1.6
     * icon : intelliJ_Icon.png
     */

    //@com.google.gson.annotations.SerializedName("id")
    private int id;
    //@com.google.gson.annotations.SerializedName("name")
    private String name;
    //@com.google.gson.annotations.SerializedName("version")
    private String version;
    //@com.google.gson.annotations.SerializedName("icon")
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /*@Override
    public String toString() {
        try {
            return this.toString(0);
        } catch (Exception var2) {
            return null;
        }
    }

    public String toString(int indentFactor) throws JSONException {
        StringWriter w = new StringWriter();
        synchronized (w.getBuffer()) {
            return this.write(w, indentFactor, 0).toString();
        }
    }

    public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
        try {
            boolean commanate = false;
            int length = this.length();
            writer.write(123);
            if (length == 1) {
                Map.Entry<String, ?> entry = (Map.Entry)this.entrySet().iterator().next();
                String key = (String)entry.getKey();
                writer.write(quote(key));
                writer.write(58);
                if (indentFactor > 0) {
                    writer.write(32);
                }

                try {
                    writeValue(writer, entry.getValue(), indentFactor, indent);
                } catch (Exception var12) {
                    throw new JSONException("Unable to write JSONObject value for key: " + key, var12);
                }
            } else if (length != 0) {
                int newindent = indent + indentFactor;

                for(Iterator var15 = this.entrySet().iterator(); var15.hasNext(); commanate = true) {
                    Map.Entry<String, ?> entry = (Map.Entry)var15.next();
                    if (commanate) {
                        writer.write(44);
                    }

                    if (indentFactor > 0) {
                        writer.write(10);
                    }

                    indent(writer, newindent);
                    String key = (String)entry.getKey();
                    writer.write(quote(key));
                    writer.write(58);
                    if (indentFactor > 0) {
                        writer.write(32);
                    }

                    try {
                        writeValue(writer, entry.getValue(), indentFactor, newindent);
                    } catch (Exception var11) {
                        throw new JSONException("Unable to write JSONObject value for key: " + key, var11);
                    }
                }

                if (indentFactor > 0) {
                    writer.write(10);
                }

                indent(writer, indent);
            }

            writer.write(125);
            return writer;
        } catch (IOException var13) {
            throw new JSONException(var13);
        }
    }

    static final void indent(Writer writer, int indent) throws IOException {
        for(int i = 0; i < indent; ++i) {
            writer.write(32);
        }

    }*/
}
