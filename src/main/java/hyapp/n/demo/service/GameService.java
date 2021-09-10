package hyapp.n.demo.service;

import com.alibaba.fastjson.JSONArray;
import hyapp.n.demo.common.ResultModel;
import hyapp.n.demo.entity.GameResultSingle;
import hyapp.n.demo.entity.Player;

import java.util.List;

/**
 * @author Wang Zhen
 * @date 2020/7/24 1:50 下午
 */
public interface GameService {

    ResultModel<Integer> createRoom(String unionId, String nickName, String picUrl);

    ResultModel<String> joinGame(Integer roomID, String unionId, String nickName, String picUrl);

    ResultModel<String> leaveGame(Integer roomID, String unionId);

    ResultModel<String> setReady(Integer roomID, String unionId);

    ResultModel<String> setUnready(Integer roomID, String unionId);

    ResultModel<String> finishGame(Integer roomID, String unionId, Integer score);

    ResultModel<String> finishSingleGame(String unionId, String picUrl, String nickName, Integer score);

    ResultModel<List<GameResultSingle>> getGameRank();
}
