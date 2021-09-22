package hyapp.n.demo.controller;

import com.alibaba.fastjson.JSONArray;
import hyapp.n.demo.common.ResultModel;
import hyapp.n.demo.entity.GameResultSingle;
import hyapp.n.demo.entity.Player;
import hyapp.n.demo.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Wang Zhen
 * @date 2020/7/24 2:01 下午
 */
@Api(tags = "游戏接口")
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @ApiOperation(value = "创建房间")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultModel<Integer> createRoom(@RequestParam(value = "unionId") String unionId,
                                           @RequestParam(value = "nickName") String nickName,
                                           @RequestParam(value = "picUrl") String picUrl) {
        return gameService.createRoom(unionId, nickName, picUrl);
    }

    @ApiOperation(value = "加入游戏")
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResultModel<String> joinGame(@RequestParam(value = "roomID") Integer roomID,
                                        @RequestParam(value = "unionId") String unionId,
                                        @RequestParam(value = "nickName") String nickName,
                                        @RequestParam(value = "picUrl") String picUrl) {
        return gameService.joinGame(roomID, unionId, nickName, picUrl);
    }

    @ApiOperation(value = "离开房间")
    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public ResultModel<String> leaveGame(@RequestParam(value = "roomID") Integer roomID,
                                         @RequestParam(value = "unionId") String unionId) {
        return gameService.leaveGame(roomID, unionId);
    }

    @ApiOperation(value = "玩家准备")
    @RequestMapping(value = "/status/ready", method = RequestMethod.POST)
    public ResultModel<String> setReady(@RequestParam(value = "roomID") Integer roomID,
                         @RequestParam(value = "unionId") String unionId) {
        return gameService.setReady(roomID, unionId);
    }

    @ApiOperation(value = "玩家取消准备")
    @RequestMapping(value = "/status/unready", method = RequestMethod.POST)
    public ResultModel<String> setUnready(@RequestParam(value = "roomID") Integer roomID,
                           @RequestParam(value = "unionId") String unionId) {
        return gameService.setUnready(roomID, unionId);
    }

    @ApiOperation(value = "玩家游戏结束")
    @RequestMapping(value = "/status/finish", method = RequestMethod.POST)
    public ResultModel<String> finishGame(@RequestParam(value = "roomID") Integer roomID,
                                          @RequestParam(value = "unionId") String unionId,
                                          @RequestParam(value = "score") int score) {
        return gameService.finishGame(roomID, unionId, score);
    }

    @ApiOperation(value = "玩家单人游戏结束")
    @RequestMapping(value = "/status/finish/single", method = RequestMethod.POST)
    public ResultModel<String> finishSingleGame(@RequestParam(value = "unionId") String unionId,
                                                @RequestParam(value = "picUrl") String picUrl,
                                                @RequestParam(value = "nickName") String nickName,
                                                @RequestParam(value = "score") int score) {
        return gameService.finishSingleGame(unionId, picUrl, nickName, score);
    }

    @ApiOperation(value = "获取排行榜")
    @RequestMapping(value = "/rank/get", method = RequestMethod.POST)
    public ResultModel<List<GameResultSingle>> getGameRank() {
        return gameService.getGameRank();
    }

    @ApiOperation(value = "获取房间人员信息")
    @RequestMapping(value = "/player/list/get/by/room", method = RequestMethod.POST)
    public ResultModel<List<Player>> getPlayerListByRoom(@RequestParam(value = "roomID") Integer roomID) {
        return gameService.getPlayerListByRoom(roomID);
    }

    @ApiOperation(value = "提交弹幕信息")
    @RequestMapping(value = "/barrage/upload", method = RequestMethod.POST)
    public ResultModel<String> uploadBarrage(@RequestParam(value = "unionId") String unionId,
                                             @RequestParam(value = "barrage") String barrage) {
        return gameService.uploadBarrage(unionId, barrage);
    }
}
