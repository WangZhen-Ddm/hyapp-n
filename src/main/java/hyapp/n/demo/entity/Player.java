package hyapp.n.demo.entity;

import lombok.Data;

/**
 * @author Wang Zhen
 * @date 2020/7/23 11:13 上午
 */
@Data
public class Player {

    private String unionId;

    private String nickName;

    private String picUrl;

    private Integer gameResult;
}
