package hyapp.n.demo.controller;

import hyapp.n.demo.common.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wang Zhen
 * @date 2020-05-15 13:53
 */
@Api(tags = {"测试"})
@RestController
@RequestMapping("/test")
public class HelloWorldController {

    @ApiOperation("HelloWorld测试")
    @GetMapping("/hello")
    public ResultModel<String> helloWorld() {
        ResultModel<String> result = new ResultModel<>();
        return result.sendSuccessResult("hello world");
    }
}