package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.client.model.domain.Users;
import generator.service.UsersService;
import generator.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author 29769
* @description 针对表【users】的数据库操作Service实现
* @createDate 2022-07-31 14:24:42
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




