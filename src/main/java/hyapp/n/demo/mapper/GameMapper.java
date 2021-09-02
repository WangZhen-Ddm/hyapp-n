package hyapp.n.demo.mapper;

import hyapp.n.demo.entity.GameResultSingle;
import hyapp.n.demo.entity.GameResultWithTime;
import hyapp.n.demo.entity.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Wang Zhen
 * @date 2020/7/23 3:18 下午
 */
@Mapper
@Repository
public interface GameMapper {

    @Insert("insert into room (creatorUnionId) values (#{creatorUnionId})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void insertRoom(Room room);

    @Insert("insert into game_result (unionId, winnerIndex, playerList, equal) values (#{unionId}, #{winnerIndex}, #{playerList}, #{equal})")
    void insertGameResult(GameResultWithTime gameResultWithTime);

    @Insert("insert into game_result_single (unionId, score, nickName, picUrl) values (#{unionId}, #{score}, #{nickName}, #{picUrl})")
    void insertSingleGameResult(String unionId, int score, String nickName, String picUrl);

    @Select("select * from game_result_single order by score desc limit 10")
    List<GameResultSingle> getTopTenGameResult();
}
