package hyapp.n.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Wang Zhen
 * @date 2020/7/24 1:55 下午
 */
@Data
public class GameResult {

    private Player winner;

    private List<Player> playerList;

    private Boolean equal;
}
