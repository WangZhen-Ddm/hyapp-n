package hyapp.n.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wang Zhen
 * @date 2021/9/2 16:27
 */
@Data
public class GameResultSingle {
    public int id;

    public String unionId;

    public String nickName;

    public String picUrl;

    public int score;

    public Date createTime;
}
