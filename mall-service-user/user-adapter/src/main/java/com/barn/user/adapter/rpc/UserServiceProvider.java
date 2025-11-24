package com.barn.user.adapter.rpc;

import com.barn.api.user.dto.AddressDTO;
import com.barn.api.user.dto.UserDTO;
import com.barn.api.user.service.UserServiceApi;
import com.barn.core.domain.R;
import com.barn.user.domain.entity.Address;
import com.barn.user.domain.entity.User;
import com.barn.user.domain.repo.UserRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * packageName com.barn.user.adapter.rpc
 *
 * @author mj
 * @className UserServiceProvider
 * @date 2025/11/24
 * @description TODO
 */
@DubboService
public class UserServiceProvider implements UserServiceApi {

    @Autowired
    private UserRepository userRepository;

    @Override
    public R<UserDTO> getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return R.fail("用户不存在");
        }

        // Entity -> DTO (简单属性拷贝，实际项目可用 MapStruct)
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setMobile(user.getMobile());
        dto.setAvatar(user.getAvatar());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        dto.setMemberLevelId(user.getMemberLevelId());

        return R.ok(dto);
    }

    @Override
    public R<AddressDTO> getAddressById(Long addressId) {
        Address address = userRepository.findAddressById(addressId);
        if (address == null) {
            return R.fail("地址不存在");
        }

        // Entity -> DTO
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setUserId(address.getUserId());
        dto.setName(address.getName());
        dto.setPhoneNumber(address.getPhoneNumber());
        dto.setDefaultStatus(address.getDefaultStatus());
        dto.setProvince(address.getProvince());
        dto.setCity(address.getCity());
        dto.setRegion(address.getRegion());
        dto.setDetailAddress(address.getDetailAddress());

        return R.ok(dto);
    }
}