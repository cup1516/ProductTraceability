package com.pt.ptmanor.service.user;

import com.pt.ptmanor.mapper.user.UserRepository;
import com.pt.ptmanor.pojo.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<SysUser> getCheckInvoiceUserList() {

        List<SysUser> all = userRepository.findByDelFlag("0");

//        for (int i=0;i<all.size();i++){
//            String[] roleIds = all.get(i).getRoleIds();
//            int x=0;
//
//            System.out.println("____________________"+roleIds);
//
//            if (roleIds!= null){
//                for(int j=0;j<roleIds.length;j++){
//                    if ((roleIds[j]=="35ba326c05594c978dd3172627d0e21a")){
//                        System.out.println("================"+roleIds[j]);
//                        x++;
//                    }
//                }
//            }
//
//            System.out.println("xxxxxxxxxxxxxxx"+x);
//            if (x==0){
//                all.remove(i);
//                i--;
//            }
//
//        }
        return all;

    }
}
