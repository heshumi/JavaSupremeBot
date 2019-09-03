package user;

import java.util.*;

public class UserPreferences {
    private String url;
    private String codeWord;
    private Queue<String> colors;
    private boolean reload;
    private int reloadTime;
    private Queue<String> sizes;
    private boolean anyColor;
    private boolean anySize;
    private String userProfileName;
    private String chromeDriverPath;
    private String checkoutDelay;
    private boolean tickAndPress;
    private boolean fillInfo;

    public boolean isFillInfo() {
        return fillInfo;
    }

    public void setFillInfo(boolean fillInfo) {
        this.fillInfo = fillInfo;
    }

    public boolean isTickAndPress() {
        return tickAndPress;
    }

    public void setTickAndPress(boolean tickAndPress) {
        this.tickAndPress = tickAndPress;
    }

    public String getCheckoutDelay() {
        return checkoutDelay;
    }

    public void setCheckoutDelay(String checkoutDelay) {
        this.checkoutDelay = checkoutDelay;
    }

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public void setChromeDriverPath(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;
    }

    public String getUserProfileName() {
        return userProfileName;
    }

    public void setUserProfileName(String userProfileName) {
        this.userProfileName = userProfileName;
    }

    public String getUrl() {
        return url;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCodeWord() {
        return codeWord;
    }

    public void setCodeWord(String codeWord) {
        this.codeWord = codeWord;
    }

   public void setColors(String st){
        colors=new LinkedList<String>(Arrays.asList(st.split(",")));
        colors.removeAll(Collections.singleton(""));
   }

    public boolean getReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public Queue<String> getColors(){
        return colors;
   }

    public Queue<String> getSizes() {
        return sizes;
    }

    public void setSizes(String st) {
        sizes=new LinkedList<>(Arrays.asList(st.split(" ")));
        sizes.removeAll(Collections.singleton(""));
    }

    public boolean isAnyColor() {
        return anyColor;
    }

    public void setAnyColor(boolean anyColor) {
        this.anyColor = anyColor;
    }

    public boolean isAnySize() {
        return anySize;
    }

    public void setAnySize(boolean anySize) {
        this.anySize = anySize;
    }
}
