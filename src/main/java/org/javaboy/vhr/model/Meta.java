package org.javaboy.vhr.model;
/**
 * @Author lixiang
 * @Date 2020/5/24 11:12
 * @Version 1.0
 */

/**
 * @author     ：lx
 * @date       ：Created in 2020/5/24 11:12
 * @description：
 * @modified By：
 * @version: 1.0
 */
public class Meta {

    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
