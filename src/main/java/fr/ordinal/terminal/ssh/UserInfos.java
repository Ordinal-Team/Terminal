package fr.ordinal.terminal.ssh;

import com.jcraft.jsch.UserInfo;

public class UserInfos implements UserInfo {
    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean promptPassword(String message) {
        return false;
    }

    @Override
    public boolean promptPassphrase(String message) {
        return false;
    }

    @Override
    public boolean promptYesNo(String message) {
        return false;
    }

    @Override
    public void showMessage(String message) {

    }
}
